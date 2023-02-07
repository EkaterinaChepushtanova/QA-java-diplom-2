package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order.*;
import org.junit.Test;
import org.example.user.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderCreateTest {

    private final OrderClient orderClient = new OrderClient();
    private final ChecksForOrder orderChecks = new ChecksForOrder();
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private String accessToken;
    private final Order orderCorrectIngredients = new Order(new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa75")));
    private final Order orderIncorrectIngredients = new Order(new ArrayList<>(Arrays.asList("61c0c5a71d1f8","61c0c5a71","61c0c5a71da75")));
    private final Order orderNoIngredients = new Order(new ArrayList<>(List.of()));

    @Test
    @DisplayName("Check that the order can be created with authorization and with ingredients")
    public void orderCreationWithAuthorizationWithIngredientsTest() {
        User user = generator.loginData();
        Response response = client.login(UserCredentials.from(user));
        accessToken = response.path("accessToken");

        Response response2 = orderClient.createOrder(orderCorrectIngredients, accessToken);
        orderChecks.orderCreatedWithIngredients(response2);
    }

    @Test
    @DisplayName("Check that the order can not be created with authorization and without ingredients")
    public void orderCreationWithAuthorizationWithoutIngredientsTest() {
        User user = generator.loginData();
        Response response = client.login(UserCredentials.from(user));
        accessToken = response.path("accessToken");

        Response response2 = orderClient.createOrder(orderNoIngredients, accessToken);
        orderChecks.orderNotCreatedWithoutIngredients(response2);
    }

    @Test
    @DisplayName("Check that the order can be created without authorization and with ingredients")
    public void orderCreationWithoutAuthorizationWithIngredientsTest() {
        accessToken = "";
        Response response = orderClient.createOrder(orderCorrectIngredients, accessToken);
        orderChecks.orderCreatedWithIngredients(response);
    }

    @Test
    @DisplayName("Check that the order can not be created without authorization and without ingredients")
    public void orderCreationWithoutAuthorizationWithoutIngredientsTest() {
        accessToken = "";
        Response response = orderClient.createOrder(orderNoIngredients, accessToken);
        orderChecks.orderNotCreatedWithoutIngredients(response);
    }

    @Test
    @DisplayName("Check that the order can not be created with incorrect hash of ingredients")
    public void orderCreationWithIncorrectHashOfIngredientsTest() {
        User user = generator.loginData();
        Response response = client.login(UserCredentials.from(user));
        accessToken = response.path("accessToken");

        Response response2 = orderClient.createOrder(orderIncorrectIngredients, accessToken);
        orderChecks.orderNotCreatedWithInvalidHash(response2);
    }
}
