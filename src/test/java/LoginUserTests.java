import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.CreateUserModel;
import model.LoginUserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static data.TestsData.*;
import static data.TestsData.EMAIL;
import static data.TestsData.NAME;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.UserSteps.*;

public class LoginUserTests extends BaseApiTests {


    private String CreatedAccessToken;

    @Before
    @Description("Создание пользователя")
    public void courierCreates(){
        CreateUserModel createUserModel = new CreateUserModel(EMAIL, PASSWORD, NAME);
       Response response = createUser(createUserModel);
        CreatedAccessToken = response.body().jsonPath().getString("accessToken");
    }
    @After
    @Description("Удаление пользователя")
    public void cleanUp() {
        if (CreatedAccessToken != null) {
            deleteUser(CreatedAccessToken);
        }
    }

    @Test
    @Description("Проверка успешного логина пользователя")
    public void loginUserSuccess() {
        LoginUserModel loginUserModel = new LoginUserModel(EMAIL, PASSWORD);
        loginUser(loginUserModel)
                .then()
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(EMAIL))
                .body("user.name", equalTo(NAME))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    @Description("Проверка логина пользователя с несуществующим email")
    public void loginWithNonExistingEmail() {
        LoginUserModel loginUserModel = new LoginUserModel("privetbro@yamdex.ru", PASSWORD);
        loginUser(loginUserModel)
                .then()
                .log().all()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
    @Test
    @Description("Проверка логина пользователя с несуществующим паролем")
    public void loginWithWrongPassword() {
        LoginUserModel loginUserModel = new LoginUserModel(EMAIL, "111222");
        loginUser(loginUserModel)
                .then()
                .log().all()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
    @Test
    @Description("Проверка логина пользователя с несуществующим логином и паролем")
    public void loginWrongEmailAndPassword() {
        LoginUserModel loginUserModel = new LoginUserModel("privetbro@yamdex.ru", "111222");
        loginUser(loginUserModel)
                .then()
                .log().all()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }


}
