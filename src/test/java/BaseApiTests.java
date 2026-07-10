import io.restassured.RestAssured;
import org.junit.BeforeClass;

import static data.TestsData.BASE_URI;

public class BaseApiTests {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URI;
    }
}
