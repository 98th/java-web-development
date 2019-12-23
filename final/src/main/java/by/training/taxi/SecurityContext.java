package by.training.taxi;

import by.training.taxi.user.UserAccountDto;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Log4j
public class SecurityContext {
    private ConcurrentHashMap<UserAccountDto, HttpSession> userSessions = new ConcurrentHashMap<>();
    private final static AtomicBoolean INITIALIZED = new AtomicBoolean(false);
    private final static Lock INITIALIZE_LOCK = new ReentrantLock();
    private static SecurityContext INSTANCE;


    private SecurityContext() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Instance was created");
        }
    }

    public static void initialize() {
        INITIALIZE_LOCK.lock();
        try {
            if (INSTANCE != null && INITIALIZED.get()) {
                throw new IllegalStateException("Context was already initialized");
            } else {
                SecurityContext context = new SecurityContext();
                INSTANCE = context;
                INITIALIZED.set(true);
            }
        } finally {
            INITIALIZE_LOCK.unlock();
        }
    }

    public static SecurityContext getInstance() {
        if (INSTANCE == null) {
            try {
                INITIALIZE_LOCK.lock();
                if (INSTANCE == null) {
                    INSTANCE = new SecurityContext();
                }
            } finally {
                INITIALIZE_LOCK.unlock();
            }
        }
        return INSTANCE;
    }

    public void addSession(UserAccountDto user, HttpSession session) {
        userSessions.put(user, session);
        log.info("session has been added");
    }

    public boolean deleteSession(HttpSession session) {
        if (userSessions.containsKey(session)) {
            userSessions.remove(session);
            session.invalidate();
            log.info("session has been deleted");
            return true;
        } else {
            return false;
        }
    }


}
