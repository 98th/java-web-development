package by.training.taxi.listener;

import by.training.taxi.SecurityContext;
import by.training.taxi.user.UserAccountDto;
import lombok.extern.log4j.Log4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import static by.training.taxi.ApplicationConstants.PARAM_USER;

@WebListener
@Log4j
public class SessionListener implements HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        if (httpSessionBindingEvent.getName().equals(PARAM_USER)) {
            UserAccountDto user = (UserAccountDto) httpSessionBindingEvent.getValue();
            SecurityContext.getInstance().addSession(user, httpSessionBindingEvent.getSession());
            log.info("user " + user.getId() + " has started using the site");
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }
}
