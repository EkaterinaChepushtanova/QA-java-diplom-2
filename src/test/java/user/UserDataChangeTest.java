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

    @Test
    @DisplayName("Check that the user data can be changed with authorization")
    public void userDataCanBeChangedWithAuthorizationTest() {
        User user = generator.randomData();
        Response response = client.create(user);
        checks.doneSuccessfully(response);

        accessToken = response.path("accessToken");
        Response response2 = client.change(accessToken, generator.randomData());
        checks.doneSuccessfully(response2);
    }

    @Test
    @DisplayName("Check that the user data can not be changed without authorization")
    public void userDataCannotBeChangedWithoutAuthorizationTest() {
        User user = generator.randomData();
        Response response = client.create(user);
        checks.doneSuccessfully(response);

        accessToken = "";
        Response response1 = client.change(accessToken, generator.randomData());
        checks.changingUserDataWithoutAuthorization(response1);
    }

    @After
    public void cleanUp() {
        client.delete(accessToken);
    }
}
