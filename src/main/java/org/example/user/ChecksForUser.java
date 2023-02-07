package org.example.user;

import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class ChecksForUser {

    public void doneSuccessfully(Response response) {
        response.then().log().all()
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    public void creatingTwiceFailed(Response response) {
        response.then().log().all()
                .assertThat().body("message", equalTo("User already exists"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }

    public void creatingWithoutPasswordFailed(Response response) {
        response.then().log().all()
                .assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }

    public void loggedInWithWrongData(Response response) {
        response.then().log().all()
                .assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    public void changingUserDataWithoutAuthorization(Response response) {
        response.then().log().all()
                .assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
}
