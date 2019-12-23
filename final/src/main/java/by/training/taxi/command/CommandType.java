package by.training.taxi.command;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public enum CommandType {
    USER_PAGE_CMD("getUserPage"),
    GET_REQUEST_HISTORY("getRequestHistory"),
    POST_USER_REGISTRATION("postUserReg"),
    POST_EDIT_USER_INFO ("postEdinUserInfo"),
    POST_USER_LOGIN ("postUserLogin"),
    LOGOUT_CMD_NAME("logoutUser"),
    GET_USER_LIST_VIEW("userListView"),
    GET_DRIVER_LIST_VIEW("driverListView"),
    ABOUT_VIEW ("aboutView"),
    POST_CAR_REQUIREMENT("postCarRequirement"),
    GET_USER_REGISTRATION("getUserRegistration"),
    GET_SUITABLE_DRIVER_VIEW("suitableDriverView"),
    GET_USER_LOGIN("getUserLogin");

    private String value;

    CommandType(String value) {
        this.value = value;
    }


    public static Optional<CommandType> getCommandFromString(String commandType) {
        return Arrays.stream(values())
                .filter(i -> i.value.equalsIgnoreCase(commandType))
                .findFirst();
    }
}