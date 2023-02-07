package org.example.order;

import io.restassured.response.Response;
import org.example.Client;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {

    public static final String END_POINT = "/api/orders";
    public Response createOrder(Order order, String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(END_POINT);
    }

    public Response receivingListOfOrders(String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(END_POINT);
    }
}
