package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order.*;
import org.junit.After;
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
        User user = generator.randomData();
        client.create(user);
        Response loginResponse = client.login(UserCredentials.from(user));
        accessToken = loginResponse.path("accessToken");

        Response orderListResponse = orderClient.receivingListOfOrders(accessToken);
        orderChecks.toDisplayOrdersListWithAuthorization(orderListResponse);
    }

    @Test
    @DisplayName("Check that the list of orders is not displayed without authorization")
    public void listOfOrdersNotDisplayedTest() {
        accessToken = "";
        Response orderListResponse = orderClient.receivingListOfOrders(accessToken);
        orderChecks.notToDisplayedOrdersListWithoutAuthorization(orderListResponse);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            client.delete(accessToken);
        }
    }
}
