package User.createUser;

import dto.OrderFindDTO;
import org.junit.jupiter.api.Test;
import services.PetStoreApi;

import static org.hamcrest.Matchers.equalTo;

public class GetOrderTest {
    private PetStoreApi petStoreApi = new PetStoreApi();

    @Test
    public void checkUpdateOrder() {
        //возвращает статуст код 200, передает параметры orderId, сравниваем ответ
        OrderFindDTO orderFind = OrderFindDTO
                .builder()
                .orderId(5)
                .build();

        petStoreApi.createOrderFind(orderFind)
                .statusCode(200);

        //Согласно сваггеру возdращаются ид до 10, отправляем ид 11, должна быть ошибка
        OrderFindDTO orderFind1 = OrderFindDTO
                .builder()
                .orderId(11)
                .build();
        //сравнение с ответом = ошибка
        petStoreApi.createOrderFind(orderFind1)
                .statusCode(404)
                .body("code", equalTo(1))
                .body("type", equalTo("error"))
                .body("message", equalTo("Order not found"));
    }
}
