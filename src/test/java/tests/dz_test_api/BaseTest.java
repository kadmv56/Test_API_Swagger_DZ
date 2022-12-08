package tests.dz_test_api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

public class BaseTest {

    //-----------------------------------------------------------------------
    /*
    Метод возвращает RequestSpecification для метода POST basUrl/store/order
  */
    protected RequestSpecification getCreateOrder(String body) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("store/order")
                .setBody(body)
                .build();
    }

    /*
           Метод возвращает спецификацию проверки тела ответа на запрос GET basUrl/store/order
            */
    protected ResponseSpecification getOrderSpecResp(int id, int petId, int quantity, String status) {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec())
                .expectBody("id", equalTo(id))
                .expectBody("petId", equalTo(petId))
                .expectBody("quantity", equalTo(quantity))
                .expectBody("status", equalTo(status));

        return builder.build();
    }

    protected ResponseSpecification getOrderSpecResp1(int petId, int quantity, String status) {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec())
                .expectBody("petId", equalTo(petId))
                .expectBody("quantity", equalTo(quantity))
                .expectBody("status", equalTo(status));

        return builder.build();
    }

    /*
Метод возвращает RequestSpecification для метода GET basUrl/order/{orderId}
 */
    protected RequestSpecification getOrderSpec(int Id) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("store/order/" + Id)
                .build();
    }

    protected RequestSpecification getOrderInvent() {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("store/inventory")
                .build();
    }
    protected RequestSpecification getOrderInventNegative() {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecificationNegative2())
                .setBasePath("store/inventory")
                .build();
    }
    protected RequestSpecification getOrderSpecStr(String Id) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("store/order/" + Id)
                .build();
    }

    protected ResponseSpecification getAssertRespDeleteOrder() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec())
                .expectBody("code", equalTo(200))
                .expectBody("type", equalTo("unknown"))
                .expectBody("message", equalTo("10"));

        return builder.build();
    }

    protected ResponseSpecification getAssertRespDeleteOrder404() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec404())
                .expectBody("code", equalTo(404))
                .expectBody("type", equalTo("unknown"))
                .expectBody("message", equalTo("Order Not Found"));

        return builder.build();
    }

    protected ResponseSpecification getAssertRespDeleteOrder404Str() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec404())
                .expectBody("code", equalTo(404))
                .expectBody("type", equalTo("unknown"))
                .expectBody("message", containsString("java.lang.NumberFormatException: For input string:"));

        return builder.build();
    }

    //-----------------------------------------------------------------------
    /*
     Метод возвращает базовую RequestSpecification со значениями общими для всех вызываемых методов
      */
    private RequestSpecification getBaseSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json;;charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("api_key", "api_key")
                .build();
    }

    private RequestSpecification getBaseSpecificationNegative() {
        return new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/")
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("api_key", "api_key")
                .build();
    }

    private RequestSpecification getBaseSpecificationNegative1() {
        return new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("api_key", "api_key")
                .build();
    }
    private RequestSpecification getBaseSpecificationNegative2() {
        return new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/")
                .build();
    }
    /*
    Метод возвращает ResponseSpecification с проверкой кода статуса ответа 200
     */
    protected ResponseSpecification getAssertionSpec() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        return builder.build();
    }

    /*
  Метод возвращает ResponseSpecification с проверкой кода статуса ответа 404
   */
    protected ResponseSpecification getAssertionSpec404() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(404);
        return builder.build();
    }

    /*
  Метод возвращает ResponseSpecification с проверкой кода статуса ответа 405
   */
    protected ResponseSpecification getAssertionSpec405() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(405);
        return builder.build();
    }

    /*
  Метод возвращает ResponseSpecification с проверкой кода статуса ответа 415
   */
    protected ResponseSpecification getAssertionSpec415() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(415);
        return builder.build();
    }

    /*
       Метод возвращает ResponseSpecification с проверкой кода статуса ответа
        */
    protected ResponseSpecification getAssertionLogSpec() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec())
                .expectBody("code", equalTo(200))
                .expectBody("type", equalTo("unknown"))
                .expectBody("message", containsString("logged in user session:"));

        return builder.build();
    }

    /*
  Метод возвращает спецификацию проверки тела ответа на запрос POST basUrl/createWithArray(createWithList)
   */
    protected ResponseSpecification getAssertCreateResp() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec())
                .expectBody("code", equalTo(200))
                .expectBody("type", equalTo("unknown"))
                .expectBody("message", equalTo("ok"));

        return builder.build();
    }

    protected ResponseSpecification getAssertCreateUserRespNegative() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec())
                .expectBody("code", equalTo(200))
                .expectBody("type", equalTo("unknown"))
                .expectBody("message", equalTo("0"));

        return builder.build();
    }

    protected ResponseSpecification getAssertCreateResp404() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec404())
                .expectBody("code", equalTo(1))
                .expectBody("type", equalTo("error"))
                .expectBody("message", equalTo("Order not found"));

        return builder.build();
    }

    protected ResponseSpecification getAssertCreateResp415() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec415())
                .expectBody("code", equalTo(415))
                .expectBody("type", equalTo("unknown"))
                .expectBody("message", containsString(""));

        return builder.build();
    }

    protected ResponseSpecification getAssertCreateRespUser404() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec404())
                .expectBody("code", equalTo(1))
                .expectBody("type", equalTo("error"))
                .expectBody("message", equalTo("User not found"));

        return builder.build();
    }

    /*
       Метод возвращает спецификацию проверки тела ответа на запрос GET basUrl/user/{username}
        */
    protected ResponseSpecification getAssertUserSpecResp(int id, String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec())
                .expectBody("id", equalTo(id))
                .expectBody("username", equalTo(username))
                .expectBody("firstName", equalTo(firstName))
                .expectBody("lastName", equalTo(lastName))
                .expectBody("email", equalTo(email))
                .expectBody("password", equalTo(password))
                .expectBody("phone", equalTo(phone))
                .expectBody("userStatus", equalTo(userStatus))
        ;
        return builder.build();
    }

    /*
 Метод возвращает RequestSpecification для метода POST basUrl/user/createWithArray
  */
    protected RequestSpecification getCreateUser(String body) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("user/createWithArray")
                .setBody(body)
                .build();
    }

    protected RequestSpecification getCreateUserNegative(String body) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecificationNegative())
                .setBasePath("user/createWithArray")
                .setBody(body)
                .build();
    }

    /*
Метод возвращает RequestSpecification для метода POST basUrl/user/createWithList
 */
    protected RequestSpecification getCreateUserList(String body) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("user/createWithList")
                .setBody(body)
                .build();
    }

    protected RequestSpecification getCreateUserListNegative(String body) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecificationNegative())
                .setBasePath("user/createWithList")
                .setBody(body)
                .build();
    }

    /*
    Метод возвращает RequestSpecification для метода GET basUrl/user/{username}
     */
    protected RequestSpecification getUserSpec(String username) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("user/" + username)
                .build();
    }

    protected RequestSpecification getUserSpecNegative(Integer username) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("user/" + username)
                .build();
    }

    protected RequestSpecification getUserSpecBody(String username, String body) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("user/" + username)
                .setBody(body)
                .build();
    }

    /*
       Метод возвращает спецификацию проверки тела ответа на запрос PUT basUrl/pet
        */
    protected ResponseSpecification getAssertUpdateUserResp() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder
                .addResponseSpecification(getAssertionSpec())
                .expectBody("code", equalTo(200))
                .expectBody("type", equalTo("unknown"))
                .expectBody("message", equalTo("123"));

        return builder.build();
    }

    protected RequestSpecification getUserLogin(String login, String password) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("user/login")
                .addQueryParam("login", login)
                .addQueryParam("password", password)
                .build();

    }

    protected RequestSpecification getUserLoginDelPar(String login) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("user/login")
                .addQueryParam("login", login)
                .build();

    }

    /*
Метод возвращает RequestSpecification для метода Get basUrl/user/logout
*/
    protected RequestSpecification getUserLogout() {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("user/logout")
                .build();
    }

    protected RequestSpecification getUserLogoutNegative() {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecificationNegative())
                .setBasePath("user/logout")
                .build();
    }

    /*
Метод возвращает RequestSpecification для метода POST basUrl/user
*/
    protected RequestSpecification getUser(String body) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecification())
                .setBasePath("user")
                .setBody(body)
                .build();
    }

    protected RequestSpecification getUserNegative(String body) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpecificationNegative1())
                .setBasePath("user")
                .setBody(body)
                .build();
    }
}