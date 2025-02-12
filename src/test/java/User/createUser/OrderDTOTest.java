package User.createUser;

import dto.OrderDTO;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OrderDTOTest {
    private OrderDTO orderDTO;
    private Long orderId;
    private static final String PET_STORE_SWAGGER = "https://petstore.swagger.io/v2";
    private static final String STORE_ORDER_ORDER_ID = "store/order/{orderId}";
    private static final String STORE_ORDER = "store/order";

    @BeforeEach
    public void beforeTest() {
        orderDTO = OrderDTO.builder()
                .petId(1L)
                .quantity(0)
                .complete(true)
                .build();

        var response = createOrder(orderDTO);
        response.statusCode(200);
        orderId = response.extract().path("id");
    }

    @AfterEach
    public void afterTest() {
        if (orderId != null) {
            var orderToDelete = OrderDTO.builder()
                    .orderId(orderId)
                    .build();

            deleteOrder(orderToDelete).statusCode(200);
        }
    }

    @Test
    public void checkOrderExist() {
        createOrder(orderDTO)
                .statusCode(200)
                .body("petId", equalTo(1))
                .body("quantity", equalTo(0))
                .body("complete", equalTo(true));
    }

    @Test
    public void checkCreateNewOrder() {
        var order = OrderDTO.builder()
                .petId(15L)
                .quantity(1000)
                .complete(true)
                .build();

        createOrder(order)
                .statusCode(200)
                .body("petId", equalTo(15))
                .body("quantity", equalTo(1000))
                .body("complete", equalTo(true));
    }

    @Test
    public void checkDeleteOrder() {
        var order = OrderDTO.builder()
                .petId(15L)
                .quantity(1000)
                .complete(true)
                .build();

        var response = createOrder(order);
        response.statusCode(200);

        Long orderId = response.extract().path("id");
        order.setOrderId(orderId);

        deleteOrder(order)
                .statusCode(200);
    }

    @Test
    public void checkOrderNotExist() {
        createOrderFind(OrderDTO.builder()
                .orderId(9999L)
                .build()
        )
                .statusCode(404)
                .body("code", equalTo(1))
                .body("type", equalTo("error"))
                .body("message", equalTo("Order not found"));
    }

    private ValidatableResponse createOrderFind(OrderDTO order) {
        return given()
                .baseUri(PET_STORE_SWAGGER)
                .contentType("application/json")
                .log().all()
                .basePath(STORE_ORDER_ORDER_ID)
                .pathParam("orderId", order.getOrderId())
                .when()
                .get()
                .then()
                .log().all();
    }

    private ValidatableResponse createOrder(OrderDTO order) {
        return given()
                .baseUri(PET_STORE_SWAGGER)
                .contentType("application/json")
                .log().all()
                .basePath(STORE_ORDER)
                .body(order)
                .when()
                .post()
                .then()
                .log().all();
    }

    private ValidatableResponse deleteOrder(OrderDTO order) {
        return given()
                .baseUri(PET_STORE_SWAGGER)
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
