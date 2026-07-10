import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.CreateUserModel;
import org.junit.After;
import org.junit.Test;
import static data.TestsData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.UserSteps.createUser;
import static steps.UserSteps.deleteUser;

public class CreateUserTests extends BaseApiTests {

    private String lastCreatedAccessToken;

    @After
    @Description("Удаление пользователя")
    public void cleanUp() {
        if (lastCreatedAccessToken != null) {
           deleteUser(lastCreatedAccessToken);
        }
    }


    @Test
    @Description("Проверка успешного создания пользователя")
    public void createUserSuccess() {
        CreateUserModel createUserModel = new CreateUserModel(EMAIL, PASSWORD, NAME);
        Response response = createUser(createUserModel);
                response.then()
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(EMAIL))
                .body("user.name", equalTo(NAME))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
                lastCreatedAccessToken = response.body().jsonPath().getString("accessToken");
    }


    @Test
    @Description("Проверка создания уже созданного пользователя")
    public void createUserAlreadyExists() {
        CreateUserModel createUserModel = new CreateUserModel(EMAIL, PASSWORD, NAME);
        Response response = createUser(createUserModel);
        response.then()
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true));
        createUser(createUserModel)
                .then()
                .log().all()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
        lastCreatedAccessToken = response.body().jsonPath().getString("accessToken");
    }


    @Test
    @Description("Проверка создания пользователя без email")
    public void createUserMissingEmail() {
        CreateUserModel createUserModel = new CreateUserModel(null, PASSWORD, NAME);
        createUser(createUserModel)
               .then()
                .log().all()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @Description("Проверка создания пользователя без password")
    public void createUserMissingPassword() {
        CreateUserModel createUserModel = new CreateUserModel(EMAIL, null, NAME);
        createUser(createUserModel)
                .then()
                .log().all()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @Description("Проверка создания пользователя без name")
    public void createUserMissingName() {
        CreateUserModel createUserModel = new CreateUserModel(EMAIL, PASSWORD, null);
        createUser(createUserModel)
                .then()
                .log().all()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }


}
