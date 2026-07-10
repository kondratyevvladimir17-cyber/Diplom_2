package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CreateOrderModel;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Cоздание заказа с помощью ручки /api/orders с авторизацией")
    public static Response createOrderAutorization (String accessToken, CreateOrderModel ingredients) {
        return given()
                .log().all()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .body(ingredients)
                .when()
                .post("/api/orders");
    }

    @Step("Cоздание заказа с помощью ручки /api/orders без авторизацией")
    public static Response createOrderNotAutorization (CreateOrderModel ingredients) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(ingredients)
                .when()
                .post("/api/orders");
    }

}
