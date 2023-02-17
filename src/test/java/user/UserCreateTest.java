package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.example.user.*;

public class UserCreateTest {

    private final UserClient client = new UserClient();
    private final ChecksForUser checks = new ChecksForUser();
    private final UserGenerator generator = new UserGenerator();
    private String accessToken;

    @Test
    @DisplayName("Check that user can be created")
    public void userCanBeCreatedTest() {
        Response createResponse = client.create(generator.randomData());
        checks.toDoSuccessfully(createResponse);

        accessToken = createResponse.path("accessToken");
    }

    @Test
    @DisplayName("Check that user can't be created twice with the same data")
    public void userCannotBeCreatedTwiceTest() {
        User user = generator.randomData();
        client.create(user);
        Response createResponse = client.create(user);
        accessToken = createResponse.path("accessToken");
        checks.toCreateTwiceFailed(createResponse);
    }

    @Test
    @DisplayName("Check that user can't be created without all necessary data")
    public void userCannotBeCreatedWithoutPasswordTest() {
        User user = generator.randomNotFullData();
        Response createResponse = client.create(user);
        accessToken = createResponse.path("accessToken");
        checks.toCreateWithoutPasswordFailed(createResponse);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            client.delete(accessToken);
        }
    }
}
