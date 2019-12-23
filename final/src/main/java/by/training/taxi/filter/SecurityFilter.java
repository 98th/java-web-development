package by.training.taxi.filter;

import by.training.taxi.ApplicationConstants;
import by.training.taxi.SecurityContext;
import by.training.taxi.command.CommandType;
import by.training.taxi.role.Role;
import javafx.beans.property.ObjectProperty;
import lombok.extern.log4j.Log4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static by.training.taxi.ApplicationConstants.*;

@Log4j
@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {
    private final Map<CommandType, List<Role>> permissions = new EnumMap<>(CommandType.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("security.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            Stream.of(CommandType.values()).forEach(commandType -> {
                List<String> roles = Arrays.asList(properties.getProperty("command." + commandType.name()).split(","));
                List<Role> userRole = roles.stream()
                        .map(i -> {
                            Optional<Role> role = Role.getRoleFromText(i);
                            if (!role.isPresent()) {
                                throw new IllegalArgumentException("Exception while reading security properties. Wrong role type");
                            }
                            return role.get();
                        }).collect(Collectors.toList());
                permissions.put(commandType, userRole);
            });
        } catch (IOException e) {
            log.error("Exception while reading security properties", e);
            throw new ServletException("Exception while reading security properties", e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String command = servletRequest.getParameter(CMD_REQ_PARAMETER);
        Optional<CommandType> commandOptional = CommandType.getCommandFromString(command);
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (!commandOptional.isPresent()){
        //    log.info("unknown command"  + command);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            Role currentUserRole = (Role) httpServletRequest.getSession().getAttribute(PARAM_USER_ROLE);
            if(currentUserRole == null) {
                currentUserRole = Role.GUEST;
            }
            List<Role> commandRoles = permissions.get(commandOptional.get());
            if (commandRoles.contains(currentUserRole)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                log.error("not enough rights for " + command);
                servletRequest.setAttribute(VIEWNAME_REQ_PARAMETER, ERROR_VIEW);
                servletRequest.getRequestDispatcher("/jsp/layout.jsp").forward(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
