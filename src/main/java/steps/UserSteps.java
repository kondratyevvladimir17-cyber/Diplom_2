package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CreateUserModel;
import model.LoginUserModel;

import static io.restassured.RestAssured.given;

public class UserSteps {

    @Step("Создание пользователя с помощью ручки /api/auth/register")
    public static Response createUser (CreateUserModel user) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(user)
                .when()
                .post("/api/auth/register");
    }


    @Step("Логин пользователя с помощью ручки /api/auth/login")
    public static Response loginUser (LoginUserModel user) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(user)
                .when()
                .post("/api/auth/login");
    }

    @Step("Удаление пользователя с помощью ручки /api/auth/user")
    public static Response deleteUser (String accessToken) {
        return given()
                .log().all()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user");
    }
}
