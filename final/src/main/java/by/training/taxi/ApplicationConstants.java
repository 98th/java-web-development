package by.training.taxi;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.HashSet;
import java.util.Set;

public class ApplicationConstants {

    //views
    public static final String VIEWNAME_REQ_PARAMETER = "viewName";
    public static final String GET_EDIT_CONTACT_VIEW = "editUserContactView";
    public static final String GET_LOGIN_VIEW = "loginView";
    public static final String GET_USER_REGISTRATION = "registrationView";
    public static final String GET_USER_PROFILE_VIEW = "userProfile";
    public static final String GET_DRIVER_LIST_VIEW = "driverListView";
    public static final String GET_USER_LIST_VIEW = "userListView";
    public static final String GET_SUITABLE_DRIVER_VIEW = "suitableDriverView";
    public static final String GET_WALLET_VIEW = "walletView";
    public static final String ERROR_VIEW = "error";
    public static final String GET_REQUEST_LIST_VIEW = "requestListView";
    public static final String GET_REQUEST_INFO_VIEW = "requestInfoView";
    public static final String GET_INCOMING_REQUESTS_VIEW = "incomingRequestsView";
    //params
    public static final String PARAM_USER = "user";
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_USER_ROLE = "userRole";
    public static final String PARAM_USER_LOGIN = "login";
    public static final String PARAM_USER_PASSWORD = "password";
    public static final String PARAM_USER_PASSWORD_REPEATED = "passwordRepeated";
    public static final String PARAM_USER_AVATAR = "userAvatar";
    public static final String PARAM_USER_F_NAME = "firstName";
    public static final String PARAM_USER_L_NAME = "lastName";
    public static final String PARAM_USER_EMAIL = "email";
    public static final String PARAM_USER_PHONE = "phone";
    public static final String PARAM_DISCOUNT = "discountAmount";
    public static final String PARAM_DRIVER_LICENCE_NUM = "drivingLicence";
    public static final String PARAM_CAR_LICENCE_PLATE_NUM = "licencePlateNumber";
    public static final String PARAM_CAR_COLOR = "carColor";
    public static final String PARAM_CAR_MODEL = "carModel";
    public static final String PARAM_REQUEST = "request";
    public static final String PARAM_REQUEST_ID = "requestId";
    public static final String PARAM_ERROR = "error";
    public static final String PARAM_FREE_CARS = "freeCars";
    public static final String PARAM_REQUIREMENT = "requirementType";
    //commands
    public static final String POST_REQUEST_INFO = "postRequestInfo";
    public static final String POST_ADD_WALLET = "postAddWallet";
    public static final String POST_FILL_WALLET = "postFillWallet";
    public static final String CMD_REQ_PARAMETER = "commandName";
    public static final String POST_CAR_REQUIREMENT = "postCarRequirement";
    public static final String POST_USER_REGISTRATION = "postUserReg";
    public static final String POST_EDIT_USER_INFO = "postEditUserInfo";
    public static final String POST_USER_LOGIN = "postUserLogin";
    public static final String POST_ASSIGN_DISCOUNT = "postAssignDiscount";
    public static final String POST_DELETE_WALLET = "postDeleteWallet";
    public static final String POST_CHOOSE_CAR = "postChooseCar";
    public static final String LOGOUT_CMD = "logoutUser";
    public static final String ABOUT_VIEW = "aboutView";
    public static final String GET_CAR_LIST_VIEW = "carListView";
    public static final String POST_CONFIRM_REQUEST = "postConfirmRide";
    public static final String LOCK_USER_CMD = "lockUser";
    public static final String POST_CANCEL_RIDE = "postCancelRide";
    public static final String POST_DECLINE_REQUEST = "declineRequest";
    public static final String DELETE_REQUEST_CMD = "deleteRequest";
    public static final String POST_UPDATE_AVATAR = "updateAvatar";
    //validators
    public static final String USER_ACCOUNT_VALIDATOR = "userAccountValidator";
    public static final String CONTACT_VALIDATOR = "contactValidator";
    public static final String DISCOUNT_VALIDATOR = "discountValidator";
    public static final String DRIVER_VALIDATOR = "driverValidator";
    public static final String CAR_VALIDATOR = "carValidator";


    public static final Set<String> ALL_COMMANDS = new HashSet<>();

    static {
        ALL_COMMANDS.add(POST_REQUEST_INFO);
        ALL_COMMANDS.add(POST_ADD_WALLET);
        ALL_COMMANDS.add(POST_DELETE_WALLET);
        ALL_COMMANDS.add(POST_CHOOSE_CAR);
        ALL_COMMANDS.add(POST_CANCEL_RIDE);
        ALL_COMMANDS.add(POST_ASSIGN_DISCOUNT);
        ALL_COMMANDS.add(LOCK_USER_CMD);
        ALL_COMMANDS.add(POST_DECLINE_REQUEST);
        ALL_COMMANDS.add(DELETE_REQUEST_CMD);
        ALL_COMMANDS.add(GET_EDIT_CONTACT_VIEW);
        ALL_COMMANDS.add(POST_CONFIRM_REQUEST);
        ALL_COMMANDS.add(GET_SUITABLE_DRIVER_VIEW);
        ALL_COMMANDS.add(GET_CAR_LIST_VIEW);
        ALL_COMMANDS.add(GET_INCOMING_REQUESTS_VIEW);
        ALL_COMMANDS.add(POST_EDIT_USER_INFO);
        ALL_COMMANDS.add(POST_FILL_WALLET);
        ALL_COMMANDS.add(LOGOUT_CMD);
        ALL_COMMANDS.add(GET_USER_LIST_VIEW);
        ALL_COMMANDS.add(GET_LOGIN_VIEW);
        ALL_COMMANDS.add(GET_WALLET_VIEW);
        ALL_COMMANDS.add(GET_DRIVER_LIST_VIEW);
        ALL_COMMANDS.add(POST_USER_LOGIN);
        ALL_COMMANDS.add(GET_USER_REGISTRATION);
        ALL_COMMANDS.add(POST_USER_REGISTRATION);
        ALL_COMMANDS.add(VIEWNAME_REQ_PARAMETER);
        ALL_COMMANDS.add(CMD_REQ_PARAMETER);
        ALL_COMMANDS.add(ABOUT_VIEW);
        ALL_COMMANDS.add(POST_UPDATE_AVATAR);
        ALL_COMMANDS.add(GET_USER_PROFILE_VIEW);
    }
}