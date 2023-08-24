package in.reqres;

import data.People;
import data.PeopleCreated;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APITests {

    /**
     * Запрос и проверка данныйх через Rest-Assured
     */
    @Test(testName = "Запрос и проверка данных")
    public void checkFieldsNotNull() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page", notNullValue())
                .body("per_page", notNullValue())
                .body("total", notNullValue())
                .body("total_pages", notNullValue())
                .body("data.id",not(hasItem(nullValue())))
                .body("data.first_name", hasItem("Lindsay"));

    }

    /**
     * Создание нового пользователя и проверка добавления через assert
     */
    @Test(testName = "Создание нового пользователя")
    public void createNewUser() {

        Map<String,String> requestData = new HashMap<>();
        requestData.put("name", "Kirill");
        requestData.put("job", "Teacher");

        Response response = given()
                .when()
                .contentType("application/json")
                .body(requestData)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .extract()
                .response();
        JsonPath jsonResponse = response.jsonPath();
        Assert.assertEquals(jsonResponse.get("name").toString(), requestData.get("name"), "Name is not valid");
        Assert.assertEquals(jsonResponse.get("job").toString(), requestData.get("job"), "Job is not valid");
    }

    /**
     * Создание нового пользователя и проверка добавления через assert(DTO)
     */
    @Test(testName = "Создание нового пользователя(DTO)")
    public void createNewUserWithDTO() {
        People people = new People("Kirill", "Teacher");
        PeopleCreated peopleCreated = given()
                .when()
                .contentType("application/json")
                .body(people)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .extract()
                .as(PeopleCreated.class);
        Assert.assertEquals(peopleCreated.getName(), people.getName(), "Name is not valid");
        Assert.assertEquals(peopleCreated.getJob(), people.getJob(), "Job is not valid");

    }
}
