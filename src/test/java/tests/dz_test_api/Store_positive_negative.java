package tests.dz_test_api;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static helpers.Utils.readFile;
import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Store_positive_negative extends BaseTest {
    String requestBody = null;
    //Назаначаем поля
    Integer Id = 10;
    Integer petId = 199;
    Integer quantity = 2;
    String status = "placed";
    String shipDate = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME);

    //String complete = String.valueOf(false);

    {
        //Получаем тело запроса
        String initialBody = null;
        try {
            initialBody = readFile("src/test/resources/PlaceNewOrder.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Изменяем тело запроса
        JSONObject jsonObject = new JSONObject(initialBody);
        jsonObject.put("id", Id);
        jsonObject.put("petId", petId);
        jsonObject.put("quantity", quantity);
        jsonObject.put("status", status);
        jsonObject.put("shipDate", shipDate);
        requestBody = jsonObject.toString();
    }

    @Test
    @Order(1)
    @DisplayName("Размещение нового заказа")
    public void test1() {

        given(getCreateOrder(requestBody))
                .post()
                .then()
                .spec(getOrderSpecResp(10, 199, 2, "placed"));
    }

    @Test
    @Order(2)
    @DisplayName("Поиск заказа по его id")
    public void test2() {

        given(getOrderSpec(10))
                .get()
                .then()
                .spec(getOrderSpecResp(10, 199, 2, "placed"));

    }

    @Test
    @Order(3)
    @DisplayName("Удаление заказа по его id")
    public void test3() {

        given(getOrderSpec(10))
                .delete()
                .then()
                .spec(getAssertRespDeleteOrder());
    }

    @Test
    @Order(4)
    @DisplayName("Return pet inventories by status")
    public void test4() {

        given(getOrderInvent())
                .get()
                .then()
                .spec(getAssertionSpec());
    }

    @Test
    @Order(5)
    @DisplayName("Негатив. Размещение нового заказа без ID")
    public void test5() {
        JSONObject requestBody1 = new JSONObject(requestBody);
        requestBody1.remove("id");
        given(getCreateOrder(requestBody1.toString()))
                .post()
                .then()
                .spec(getOrderSpecResp1(199, 2, "placed"));
    }

    @Test
    @Order(6)
    @DisplayName("Негатив. Размещение нового заказа пустым Json объектом")
    public void test6() {
        given(getCreateOrder("{}"))
                .post()
                .then()
                .spec(getOrderSpecResp1(0, 0, null));
    }

    @Test
    @Order(7)
    @DisplayName("Негатив. Поиск заказа по несуществующему id")
    public void test7() {

        given(getOrderSpec(112))
                .get()
                .then()
                .spec(getAssertCreateResp404());
    }

    @Test
    @Order(8)
    @DisplayName("Негатив. Поиск заказа по строковому значению id")
    public void test8() {

        given(getOrderSpecStr("one"))
                .get()
                .then()
                .spec(getAssertionSpec404());
    }

    @Test
    @Order(9)
    @DisplayName("Негатив. Удаление заказа по несуществующему id")
    public void test9() {

        given(getOrderSpec(155))
                .delete()
                .then()
                .spec(getAssertRespDeleteOrder404());
    }

    @Test
    @Order(10)
    @DisplayName("Негатив. Удаление заказа по строковому id")
    public void test10() {

        given(getOrderSpecStr("then"))
                .delete()
                .then()
                .spec(getAssertRespDeleteOrder404Str());
    }
    @Test
    @Order(11)
    @DisplayName("Негатив. Return of pet stocks by status, without headers in the request")
    public void test11() {

        given(getOrderInventNegative())
                .get()
                .then()
                .spec(getAssertionSpec());
    }
}
