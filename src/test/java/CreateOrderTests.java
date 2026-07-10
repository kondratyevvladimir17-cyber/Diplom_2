import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.CreateOrderModel;
import model.CreateUserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static data.TestsData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static steps.OrderSteps.createOrderAutorization;
import static steps.OrderSteps.createOrderNotAutorization;
import static steps.UserSteps.createUser;
import static steps.UserSteps.deleteUser;

public class CreateOrderTests extends BaseApiTests {

    private String accessToken;

    @Before
    @Description("Создание пользователя")
    public void courierCreates() {
        CreateUserModel createUserModel = new CreateUserModel(EMAIL, PASSWORD, NAME);
        Response response = createUser(createUserModel);
        accessToken = response.body().jsonPath().getString("accessToken");
    }

    @After
    @Description("Удаление пользователя")
    public void cleanUp() {
        if (accessToken != null) {
            deleteUser(accessToken);
        }
    }

    @Test
    @Description("Успешное создание заказа с авторизацией")
    public void createOrderSuccess() {
        CreateOrderModel createOrderModel = new CreateOrderModel(TWO_INGREDIENTS);
        createOrderAutorization(accessToken, createOrderModel)
                .then()
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.ingredients", hasSize(TWO_INGREDIENTS.size()))
                .body("order._id", notNullValue())
                .body("order.owner.name", equalTo(NAME))
                .body("order.owner.email", equalTo(EMAIL));
    }

    @Test
    @Description("Успешное создание заказа без авторизации")
    public void createOrderNoAutorization() {
        CreateOrderModel createOrderModel = new CreateOrderModel(TWO_INGREDIENTS);
        createOrderNotAutorization(createOrderModel)
                .then()
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }
    @Test
    @Description("Создание заказа без ингредиентов")
    public void createOrderNotIngredients() {
        CreateOrderModel createOrderModel = new CreateOrderModel();
        createOrderAutorization(accessToken, createOrderModel)
                .then()
                .log().all()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @Description("Создание заказа с неверным хэшем ингредиентов")
    @Ignore("Баг: ожидаемый код '400' отличается от фактического '500'")
    public void createOrderFalseHashIngredients() {
        CreateOrderModel createOrderModel = new CreateOrderModel(FALSE_INGREDIENT);
        createOrderAutorization(accessToken, createOrderModel)
                .then()
                .log().all()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }


}
