package pl.wolke.doghouse.modules.commons.exception;

public class UserNotFoundException extends Exception {
    private final String username;

    public static UserNotFoundException createWith(String username) {
        return new UserNotFoundException(username);
    }

    private UserNotFoundException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "User '" + username + "' not found";
    }
}
