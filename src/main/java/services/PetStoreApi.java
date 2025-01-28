package services;


import dto.OrderFindDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetStoreApi {
    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private static final String USER_PATH = "store/order/{orderId}";
    private static RequestSpecification spec;

    public PetStoreApi() {
        spec = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .log().all();  // Логирование запросов и ответов
    }

    public static ValidatableResponse createOrderFind(OrderFindDTO orderFindDTO) {

        return given(spec)
                .basePath(USER_PATH)
                .pathParam("orderId", orderFindDTO.getOrderId())  // Подставляем orderId в путь
                .when()
                .get()  // GET запрос, не передаем тело
                .then()
                .log().all();
    }
}