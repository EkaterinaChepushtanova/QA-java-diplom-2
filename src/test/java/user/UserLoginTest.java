package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.user.*;
import org.junit.After;
import org.junit.Test;

public class UserLoginTest {

    private final UserClient client = new UserClient();
    private final ChecksForUser checks = new ChecksForUser();
    private final UserGenerator generator = new UserGenerator();
    private String accessToken;

    @Test
    @DisplayName("Check that the user can log in to the system with correct data")
    public void loginCourierTest() {
        User user = generator.randomData();
        client.create(user);
        Response loginResponse = client.login(UserCredentials.from(user));
        checks.toDoSuccessfully(loginResponse);

        accessToken = loginResponse.path("accessToken");
    }

    @Test
    @DisplayName("Check that the user can not log in to the system with wrong data")
    public void loginCourierWithWrongDataTest() {
        User user = generator.randomData();
        Response loginResponse = client.login(UserCredentials.from(user));
        checks.toLogInWithWrongData(loginResponse);
    }

    @After
    public void cleanUp() {
        if ((accessToken != null)) {
            client.delete(accessToken);
        }
    }
}
