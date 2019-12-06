package by.training.taxi;

import java.util.HashSet;
import java.util.Set;

public class ApplicationConstants {

    //views
    public static final String VIEWNAME_REQ_PARAMETER = "viewName";
    public static final String GET_LOGIN_VIEW = "loginView";
    public static final String GET_USER_REGISTRATION = "registrationView";
    public static final String GET_USER_PAGE_VIEW = "userPage";
    //params
    public static final String PARAM_USER_LOGIN = "login";
    public static final String PARAM_USER = "user";
    public static final String PARAM_USER_PASSWORD = "password";
    public static final String PARAM_USER_PASSWORD_REPEATED = "passwordRepeated";
    public static final String PARAM_USER_ROLE = "userRole";
    public static final String PARAM_USER_EMAIL = "email";
    public static final String PARAM_USER_PHONE = "phone";
    public static final String PARAM_DRIVER_LICENCE_NUM = "drivingLicence";
    public static final String PARAM_CAR_LICENCE_PLATE_NUM = "licencePlateNumber";
    public static final String PARAM_USER_F_NAME = "firstName";
    public static final String PARAM_USER_L_NAME = "lastName";
    //commands
    public static final String USER_PAGE_CMD = "getUserPage";
    public static final String GET_REQUEST_HISTORY = "getRequestHistory";
    public static final String CMD_REQ_PARAMETER = "commandName";
    public static final String POST_USER_REGISTRATION = "postUserReg";
    public static final String POST_EDIT_USER_INFO = "postEdinUserInfo";
    public static final String POST_USER_LOGIN = "postUserLogin";
    public static final String VIEW_ALL_USERS_CMD_NAME = "allUsersView";
    public static final String LOGOUT_CMD_NAME = "logoutUser";
    public static final String GET_ALL_USERS = "allUsers";
    public static final String ABOUT_VIEW = "aboutView";



    public static final Set<String> ALL_COMMANDS = new HashSet<>();

    static {
        ALL_COMMANDS.add(USER_PAGE_CMD);
        ALL_COMMANDS.add(POST_EDIT_USER_INFO);
        ALL_COMMANDS.add(LOGOUT_CMD_NAME);
        ALL_COMMANDS.add(GET_ALL_USERS);
        ALL_COMMANDS.add(GET_LOGIN_VIEW);
        ALL_COMMANDS.add(POST_USER_LOGIN);
        ALL_COMMANDS.add(GET_USER_REGISTRATION);
        ALL_COMMANDS.add(POST_USER_REGISTRATION);
        ALL_COMMANDS.add(VIEWNAME_REQ_PARAMETER);
        ALL_COMMANDS.add(CMD_REQ_PARAMETER);
        ALL_COMMANDS.add(VIEW_ALL_USERS_CMD_NAME);
        ALL_COMMANDS.add(ABOUT_VIEW);
        ALL_COMMANDS.add(GET_USER_PAGE_VIEW);
    }
}