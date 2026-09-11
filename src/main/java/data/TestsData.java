package data;

import com.github.javafaker.Faker;

import java.util.List;

public class TestsData {
    public static final String BASE_URI = "https://stellarburgers.education-services.ru";
    static Faker user = new Faker();
    public static final String EMAIL = user.internet().emailAddress();
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String NAME = user.name().firstName();
    public static final List<String> TWO_INGREDIENTS = List.of(
            "61c0c5a71d1f82001bdaaa6d",
            "61c0c5a71d1f82001bdaaa6f"
    );
    public static final List<String> FALSE_INGREDIENT = List.of(
            "61c0c5a71d1f820012222222222222222",
            "61c0c5a71d1f82001b414222333123333223"
    );
}
