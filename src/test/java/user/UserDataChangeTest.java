package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.example.user.*;

public class UserDataChangeTest {

    private final UserClient client = new UserClient();
    private final ChecksForUser checks = new ChecksForUser();
    private final UserGenerator generator = new UserGenerator();
    private String accessToken;
    private String changedAccessToken;

    @Test
    @DisplayName("Check that the user data can be changed with authorization")
    public void userDataCanBeChangedWithAuthorizationTest() {
        User user = generator.randomData();
        Response createResponse = client.create(user);
        checks.toDoSuccessfully(createResponse);

        accessToken = createResponse.path("accessToken");
        Response changeResponse = client.change(accessToken, generator.randomData());
        checks.toDoSuccessfully(changeResponse);
    }

    @Test
    @DisplayName("Check that the user data can not be changed without authorization")
    public void userDataCannotBeChangedWithoutAuthorizationTest() {
        User user = generator.randomData();
        Response createResponse = client.create(user);
        checks.toDoSuccessfully(createResponse);

        accessToken = createResponse.path("accessToken");

        Response changeResponse = client.change("", generator.randomData());
        checks.toChangeUserDataWithoutAuthorization(changeResponse);
    }

    @After
    public void cleanUp() {
        client.delete(accessToken);
    }
}