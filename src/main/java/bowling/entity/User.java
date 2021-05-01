package bowling.entity;

import java.util.Objects;

public class User {
    private final static int MAX_USER_NAME_LENGTH = 3;
    public final static String USER_NAME_ERROR_MESSAGE = "플레이어의 이름은 영문 3자로 입력해 주세요.";

    private final String userName;

    public User(String userName) {
        validateUserName(userName);
        this.userName = userName;
    }

    private void validateUserName(String userName) {
        if (userName.length() != MAX_USER_NAME_LENGTH) {
            throw new IllegalArgumentException(USER_NAME_ERROR_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
