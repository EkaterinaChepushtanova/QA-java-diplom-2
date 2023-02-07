package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.example.user.*;

public class UserCreateTest {

    private final UserClient client = new UserClient();
    private final ChecksForUser checks = new ChecksForUser();
    private final UserGenerator generator = new UserGenerator();

    @Test
    @DisplayName("Check that user can be created")
    public void userCanBeCreatedTest() {
        Response response = client.create(generator.randomData());
        checks.doneSuccessfully(response);

        String accessToken = response.path("accessToken");
        client.delete(accessToken);
    }

    @Test
    @DisplayName("Check that user can't be created twice with the same data")
    public void userCannotBeCreatedTwiceTest() {
        client.create(generator.getDefault());
        Response response = client.create(generator.getDefault());
        checks.creatingTwiceFailed(response);
    }

    @Test
    @DisplayName("Check that user can't be created without all necessary data")
    public void userCannotBeCreatedWithoutPasswordTest() {
        Response response = client.create(generator.notFullData());
        checks.creatingWithoutPasswordFailed(response);
    }
}
