package by.training.taxi.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(filterName = "CookieLocaleFilter", urlPatterns = { "/" })
public class CookieLocaleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> output = Optional.empty();
        if (cookies != null) {
           output = Arrays.stream(cookies).filter(i -> "lang".equals(i.getName()))
                   .filter(i -> "en_US".equals(i.getValue()) || "be_BY".equals(i.getValue()))
                   .findFirst();
        }
        String lang;
        if(output.isPresent()) {
            lang = output.get().getValue();
        } else {
            lang = "en_US";
            Cookie langCookie = new Cookie("lang", lang);
            resp.addCookie(langCookie);
        }
        req.setAttribute("lang", lang);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig arg0) throws ServletException {}

}