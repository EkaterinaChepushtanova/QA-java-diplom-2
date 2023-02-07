package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order.*;
import org.junit.Test;
import org.example.user.*;

public class ListOfOrdersTest {

    private final OrderClient orderClient = new OrderClient();
    private final ChecksForOrder orderChecks = new ChecksForOrder();
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private String accessToken;

    @Test
    @DisplayName("Check that the list of orders is displayed with authorization")
    public void listOfOrdersDisplayedTest() {
        User user = generator.loginData();
        Response response = client.login(UserCredentials.from(user));
        accessToken = response.path("accessToken");

        Response response2 = orderClient.receivingListOfOrders(accessToken);
        orderChecks.listOfOrdersDisplayedWithAuthorization(response2);
    }

    @Test
    @DisplayName("Check that the list of orders is not displayed without authorization")
    public void listOfOrdersNotDisplayedTest() {
        accessToken = "";
        Response response = orderClient.receivingListOfOrders(accessToken);
        orderChecks.listOfOrdersNotDisplayedWithoutAuthorization(response);
    }
}
