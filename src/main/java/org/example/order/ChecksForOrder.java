package org.example.order;

import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ChecksForOrder {

    public void orderCreatedWithIngredients(Response response) {
        response.then().log().all()
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    public void orderNotCreatedWithoutIngredients(Response response) {
        response.then().log().all()
                .assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    public void orderNotCreatedWithInvalidHash(Response response) {
        response.then().log().all()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    public void listOfOrdersDisplayedWithAuthorization(Response response) {
        response.then().log().all()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    public void listOfOrdersNotDisplayedWithoutAuthorization(Response response) {
        response.then().log().all()
                .assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
}
