package in.reqres;

import data.People;
import data.PeopleCreated;
import data.UserData;
import data.UsersFromPage;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static in.reqres.Specification.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APITests {
    /**
     * Запрос и проверка данныйх через Rest-Assured
     */
    @Test(testName = "Запрос и проверка данных")
    public void checkFieldsNotNull() {
        given()
                .spec(reqSpec())
                .when()
                .get("/users?page=2")
                .then()
                .body("page", notNullValue())
                .body("per_page", notNullValue())
                .body("total", notNullValue())
                .body("total_pages", notNullValue())
                .body("data.id", not(hasItem(nullValue())))
                .body("data.first_name", hasItem("Lindsay"))
                .spec(respSpec());
    }

    /**
     * Создание нового пользователя и проверка добавления через assert
     */
    @Test(testName = "Создание нового пользователя")
    public void createNewUser() {

        Map<String, String> requestData = new HashMap<>();
        requestData.put("name", "Kirill");
        requestData.put("job", "Teacher");

        Response response = given()
                .spec(reqSpec())
                .when()
                .body(requestData)
                .when()
                .post("/users")
                .then()
                .spec(respCreateUser())
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
                .spec(reqSpec())
                .when()
                .body(people)
                .when()
                .post("/users")
                .then()
                .spec(respCreateUser())
                .extract().as(PeopleCreated.class);
        Assert.assertEquals(peopleCreated.getName(), people.getName(), "Name is not valid");
        Assert.assertEquals(peopleCreated.getJob(), people.getJob(), "Job is not valid");
    }

    /**
     * Запрос и проверка данных(DTO)
     */
    @Test(testName = "Запрос и проверка данных(DTO)")
    public void setFieldsWithDTO() {
        UsersFromPage usersFromPage = given()
                .spec(reqSpec())
                .when()
                .get("/users?page=2")
                .then()
                .spec(respSpec())
                .extract().as(UsersFromPage.class);
        System.out.println(usersFromPage.getSupport().getUrl());
    }

    /**
     * Получение пользователей из всех данных данных(DTO)
     */
    @Test(testName = "Получение пользователей из данных")
    public void setUsersFromAllData() {
        List<UserData> userDataList = given()
                .spec(reqSpec())
                .when()
                .get("/users?page=2")
                .then()
                .spec(respSpec())
                .extract().body().jsonPath().getList("data", UserData.class);
        userDataList.stream().forEach(i -> System.out.println(i.getFirst_name()));
    }
}
