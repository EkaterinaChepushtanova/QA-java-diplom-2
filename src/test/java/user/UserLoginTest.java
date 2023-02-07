package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.user.*;
import org.junit.Test;

public class UserLoginTest {

    private final UserClient client = new UserClient();
    private final ChecksForUser checks = new ChecksForUser();
    private final UserGenerator generator = new UserGenerator();

    @Test
    @DisplayName("Check that the user can log in to the system with correct data")
    public void loginCourierTest() {
        User user = generator.loginData();
        Response response = client.login(UserCredentials.from(user));
        checks.doneSuccessfully(response);
    }

    @Test
    @DisplayName("Check that the user can not log in to the system with wrong data")
    public void loginCourierWithWrongDataTest() {
        User user = generator.randomData();
        Response response = client.login(UserCredentials.from(user));
        checks.loggedInWithWrongData(response);
    }
}
