package services;

import dto.OrderDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetStore {
    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private static final String ORDER_PATH = "store/order";
    private static RequestSpecification specOrder;

    public PetStore() {
        specOrder = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .log().all();
    }


    public static ValidatableResponse createOrder(OrderDTO OrderDTO) {

        return given(specOrder)
                .basePath(ORDER_PATH)
                .body(OrderDTO)
                .when()
                .post()
                .then()
                .log().all();
    }
}