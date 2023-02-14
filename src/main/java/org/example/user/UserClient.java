package org.example.user;

import io.restassured.response.Response;
import org.example.Client;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {

    public static final String END_POINT = "api/auth/";

    public Response create(User user) {
        return given().log().all()
                .spec(getSpec())
                .body(user)
                .when()
                .post(END_POINT + "register");
    }

    public Response login(UserCredentials credentials) {
        return given().log().all()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(END_POINT + "login");
    }

    public Response change(String accessToken, User user) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(END_POINT + "user");
    }

    public Response delete(String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(END_POINT + "user");
    }
}
