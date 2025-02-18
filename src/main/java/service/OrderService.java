package service;

import config.ConfigLoader;
import dto.OrderDTO;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderService {
    private final String baseUrl;
    private static final String STORE_ORDER_ORDER_ID = "store/order/{orderId}";
    private static final String STORE_ORDER = "store/order";
    public OrderService() {
        this.baseUrl = new ConfigLoader().getBaseUrl();
    }

    public ValidatableResponse createOrderFind(OrderDTO order) {
        return given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .log().all()
                .basePath(STORE_ORDER_ORDER_ID)
                .pathParam("orderId", order.getOrderId())
                .when()
                .get()
                .then()
                .log().all();
    }

    public ValidatableResponse createOrder(OrderDTO order) {
        return given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .log().all()
                .basePath(STORE_ORDER)
                .body(order)
                .when()
                .post()
                .then()
                .log().all();
    }

    public ValidatableResponse deleteOrder(OrderDTO order) {
        return given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .log().all()
                .basePath(STORE_ORDER_ORDER_ID)
                .pathParam("orderId", order.getOrderId())
                .when()
                .delete()
                .then()
                .log().all();
    }
}