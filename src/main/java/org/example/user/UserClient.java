package org.example.user;

import io.restassured.response.Response;
import org.example.Client;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {

    public Response create(User user) {
        return given().log().all()
                .spec(getSpec())
                .body(user)
                .when()
                .post("api/auth/register");
    }

    public Response login(UserCredentials credentials) {
        return given().log().all()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post("api/auth/login");
    }

    public Response change(String accessToken, User user) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch("api/auth/user");
    }

    public Response delete(String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete("api/auth/user");
    }
}
