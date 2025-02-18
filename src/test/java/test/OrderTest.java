package test;

import com.google.inject.Guice;
import dto.OrderDTO;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scoped.OrderModule;
import service.OrderService;

import javax.inject.Inject;

import static org.hamcrest.Matchers.equalTo;

public class OrderTest {
    private OrderDTO orderDTO;
    private Long orderId;

    @Inject
    private OrderService orderService;


    @BeforeEach
    public void setUp() {
        var injector = Guice.createInjector(new OrderModule());
        injector.injectMembers(this);

        orderService = new OrderService();

        orderDTO = OrderDTO.builder()
                .petId(1L)
                .quantity(0)
                .complete(true)
                .build();

        var response = orderService.createOrder(orderDTO);
        response.statusCode(200);
        orderId = response.extract().path("id");
    }

    @AfterEach
    public void tearDown() {
        if (orderId != null) {
            var orderToDelete = OrderDTO.builder()
                    .orderId(orderId)
                    .build();

            orderService.deleteOrder(orderToDelete).statusCode(200);
        }
    }

    @Test
    public void checkOrderExists() {
        orderService.createOrder(orderDTO)
                .statusCode(200)
                .body("petId", equalTo(1))
                .body("quantity", equalTo(0))
                .body("complete", equalTo(true));
    }

    @Test
    public void checkCreateNewOrder() {
        var newOrder = OrderDTO.builder()
                .petId(15L)
                .quantity(1000)
                .complete(true)
                .build();

        orderService.createOrder(newOrder)
                .statusCode(200)
                .body("petId", equalTo(15))
                .body("quantity", equalTo(1000))
                .body("complete", equalTo(true));
    }

    @Test
    public void checkDeleteOrder() {
        var orderToCreate = OrderDTO.builder()
                .petId(15L)
                .quantity(1000)
                .complete(true)
                .build();

        var response = orderService.createOrder(orderToCreate);
        response.statusCode(200);

        Long createdOrderId = response.extract().path("id");
        orderToCreate.setOrderId(createdOrderId);

        orderService.deleteOrder(orderToCreate)
                .statusCode(200);
    }

    @Test
    public void checkOrderNotExists() {
        orderService.createOrderFind(OrderDTO.builder()
                        .orderId(9999L)
                        .build())
                .statusCode(404)
                .body("code", equalTo(1))
                .body("type", equalTo("error"))
                .body("message", equalTo("Order not found"));
    }
}







