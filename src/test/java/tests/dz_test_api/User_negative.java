package tests.dz_test_api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static helpers.Utils.readFile;
import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class User_negative extends BaseTest {
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
    @DisplayName("Негатив. Создание пользователя через массив, с удаленным заголовком Content-Type")
    @Order(1)
    public void test1() throws IOException {
        JSONArray jsonObject = new JSONArray();
        jsonObject.put(new JSONObject(requestBody));
        given(getCreateUserNegative(jsonObject.toString()))
                .post()
                .then()
                .spec(getAssertCreateResp415());
    }

    @Test
    @DisplayName("Негатив. Создание пользователя через массив, с пустым json файлом")
    @Order(2)
    public void test2() throws IOException {
        JSONArray jsonObject = new JSONArray();
        jsonObject.put(new JSONObject());
        given(getCreateUser(jsonObject.toString()))
                .post()
                .then()
                .spec(getAssertCreateResp());
    }

    @Test
    @DisplayName("Негатив. Создание пользователя через список, с удаленным заголовком Content-Type")
    @Order(3)
    public void test3() throws IOException {
        JSONArray jsonObject = new JSONArray();
        jsonObject.put(new JSONObject(requestBody));
        given(getCreateUserListNegative(jsonObject.toString()))
                .post()
                .then()
                .spec(getAssertCreateResp415());
    }

    @Test
    @DisplayName("Негатив. Создание пользователя через список, с пустым json файлом")
    @Order(4)
    public void test4() throws IOException {
        JSONArray jsonObject = new JSONArray();
        jsonObject.put(new JSONObject());
        given(getCreateUserList(jsonObject.toString()))
                .post()
                .then()
                .spec(getAssertCreateResp());
    }

    @Test
    @DisplayName("Негатив. Запрос пользователя по не существующему username")
    @Order(5)
    public void test5() {
        given(getUserSpec("Ivan45"))
                .get()
                .then()
                .spec(getAssertCreateRespUser404());
    }

    @Test
    @DisplayName("Негатив. Запрос пользователя с незаполненным username")
    @Order(6)
    public void test6() {
        given(getUserSpec(""))
                .get()
                .then()
                .spec(getAssertionSpec405());
    }

    @Test
    @DisplayName("Негатив. Обновление данных пользователя с удалением элемента Json ")
    @Order(7)
    public void test7() {
        //Изменяем тело запроса
        JSONObject jsonObject = new JSONObject(requestBody);
        jsonObject.remove("userStatus");

        given(getUserSpecBody("Ivan56", jsonObject.toString()))
                .put()
                .then()
                .spec(getAssertUpdateUserResp());
    }

    @Test
    @DisplayName("Негатив. Обновление данных пользователя с заменой типа данных")
    @Order(8)
    public void test8() {
        //Изменяем тело запроса
        JSONObject jsonObject = new JSONObject(requestBody);
        jsonObject.put("id", "one");

        given(getUserSpecBody("Ivan56", jsonObject.toString()))
                .put()
                .then()
                .statusCode(500);
    }

    @Test
    @DisplayName("Негатив. Удаление несуществующего пользователя")
    @Order(9)
    public void test9() {
        given(getUserSpec("Vovan56"))
                .delete()
                .then()
                .spec(getAssertionSpec404());

    }

    @Test
    @DisplayName("Негатив. Удаление 0 пользователя c заменой его на тип Integer")
    @Order(10)
    public void test10() {

        JSONObject jsonObject = new JSONObject(requestBody);
        jsonObject.put("username", 0);

        given(getUserSpecNegative(0))
                .delete()
                .then()
                .spec(getAssertionSpec404());

    }

    @Test
    @DisplayName("Негатив. Вход в систему незарегистрированного пользователя")
    @Order(11)
    public void test11() {

        given(getUserLogin("Dimon56", "123456"))
                .get()
                .then()
                .spec(getAssertionSpec());
    }

    @Test
    @DisplayName("Негатив. Вход в систему с удаленным параметром - password")
    @Order(12)
    public void test12() {

        given(getUserLoginDelPar("Ivan56"))
                .get()
                .then()
                .spec(getAssertionLogSpec());
    }

    @Test
    @DisplayName("Негатив. Выход из системы с удаленным параметром из заголовка запроса")
    @Order(13)
    public void test13() {
        given(getUserLogoutNegative())
                .get()
                .then()
                .spec(getAssertCreateResp());

    }

    @Test
    @DisplayName("Негатив. Создание пользавателя с пустым Json объектом")
    @Order(14)
    public void test14() {
        JSONObject jsonObject = new JSONObject();
        given(getUser(jsonObject.toString()))
                .post()
                .then()
                .spec(getAssertCreateUserRespNegative());
    }

    @Test
    @DisplayName("Негатив. Создание пользавателя с пустым Json объектом")
    @Order(15)
    public void test15() {
        JSONObject jsonObject = new JSONObject(requestBody);
        given(getUserNegative(jsonObject.toString()))
                .post()
                .then()
                .spec(getAssertionSpec415());
    }
}
