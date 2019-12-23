package by.training.taxi.util;

import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.taxi.ApplicationConstants.VIEWNAME_REQ_PARAMETER;

@Log4j
public class RequestUtil {
    public static void forward(HttpServletRequest request, HttpServletResponse response, String page) throws RequestUtilException {
        try {
            request.setAttribute(VIEWNAME_REQ_PARAMETER, page);
            request.getRequestDispatcher("/jsp/layout.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            log.error("Failed to forward", e);
            throw new RequestUtilException(e.getMessage());
        }
    }

    public static void sendRedirect(HttpServletRequest request, HttpServletResponse response, String page) throws RequestUtilException {
        try {
            request.setAttribute(VIEWNAME_REQ_PARAMETER, page);
            response.sendRedirect(page);
        } catch(IOException e) {
            log.error("Failed to redirect", e);
            throw new RequestUtilException(e.getMessage());
        }
    }


    public static void sendRedirectToCommand(HttpServletRequest request, HttpServletResponse response, String commandName) throws RequestUtilException{
        try {
            response.sendRedirect(request.getContextPath() + "?commandName=" + commandName);
        } catch (IOException e) {
            log.error("Failed to redirect to command", e);
            throw new RequestUtilException(e);
        }
    }
}
