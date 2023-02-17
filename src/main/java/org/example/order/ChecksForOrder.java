package org.example.order;

import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ChecksForOrder {

    public void toCreateOrderWithIngredients(Response response) {
        response.then().log().all()
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    public void notToCreateOrderWithoutIngredients(Response response) {
        response.then().log().all()
                .assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    public void notToCreateOrderWithInvalidHash(Response response) {
        response.then().log().all()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    public void toDisplayOrdersListWithAuthorization(Response response) {
        response.then().log().all()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    public void notToDisplayedOrdersListWithoutAuthorization(Response response) {
        response.then().log().all()
                .assertThat().body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
}
