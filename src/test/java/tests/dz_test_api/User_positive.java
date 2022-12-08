package tests.dz_test_api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static helpers.Utils.readFile;
import static io.restassured.RestAssured.given;


/**
 * Финальный вид тестов
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class User_positive extends BaseTest {

    String requestBody = null;
    Integer id = 123;
    String username = "Ivan56";
    String firstName = "Ivanov";
    String lastName = "Ivan";
    String email = "adf@mail.ru";
    String password = "45411254";
    String phone = "+78954575487";
    Integer userStatus = 15;

    {
        //Получаем тело запроса
        String initialBody = null;
        try {
            initialBody = readFile("src/test/resources/PlaceNewUser.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Изменяем тело запроса

        JSONObject jsonElement = new JSONObject(initialBody);
        jsonElement.put("id", id)
                .put("username", username)
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("email", email)
                .put("password", password)
                .put("phone", phone)
                .put("userStatus", userStatus);

        requestBody = jsonElement.toString();
    }

    @Test
    @DisplayName("Creates list of users with given input array")
    @Order(1)
    public void test1() throws IOException {
        JSONArray jsonObject = new JSONArray();
        jsonObject.put(new JSONObject(requestBody));
        given(getCreateUser(jsonObject.toString()))
                .post()
                .then()
                .spec(getAssertCreateResp());
    }

    @Test
    @DisplayName("Creates list of users with given input list")
    @Order(2)
    public void test2() throws IOException {
        JSONArray jsonObject = new JSONArray();
        jsonObject.put(new JSONObject(requestBody));
        given(getCreateUserList(jsonObject.toString()))
                .post()
                .then()
                .spec(getAssertCreateResp());
    }

    @Test
    @DisplayName("Get user by user name")
    @Order(3)
    public void test3() {
        given(getUserSpec("Ivan56"))
                .get()
                .then()
                .spec(getAssertUserSpecResp(123, "Ivan56", "Ivanov", "Ivan", "adf@mail.ru", "45411254", "+78954575487", 15));
    }

    @Test
    @DisplayName("Updater User")
    @Order(4)
    public void test4() throws IOException {
        //Изменяем тело запроса
        JSONObject jsonObject = new JSONObject(requestBody);
        jsonObject.put("userStatus", 32);

        given(getUserSpecBody("Ivan56", jsonObject.toString()))
                .put()
                .then()
                .spec(getAssertUpdateUserResp());
    }

    @Test
    @DisplayName("Delete User")
    @Order(8)
    public void test8() {
        given(getUserSpec("Ivan56"))
                .delete()
                .then()
                .spec(getAssertionSpec());

    }

    @Test
    @DisplayName("Logs user into the system")
    @Order(5)
    public void test5() {

        given(getUserLogin("Ivan56", "123456"))
                .get()
                .then()
                .spec(getAssertionLogSpec());
    }

    @Test
    @DisplayName("Logs out current logged i user session")
    @Order(6)
    public void test6() {
        given(getUserLogout())
                .get()
                .then()
                .spec(getAssertCreateResp());

    }

    @Test
    @DisplayName("Create user")
    @Order(7)
    public void test7() {
        JSONObject jsonObject = new JSONObject(requestBody);
        given(getUser(jsonObject.toString()))
                .post()
                .then()
                .spec(getAssertUpdateUserResp());
    }
}
