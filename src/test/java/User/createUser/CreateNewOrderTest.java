package User.createUser;

import dto.OrderDTO;
import org.junit.jupiter.api.Test;
import services.PetStore;

import static org.hamcrest.Matchers.equalTo;

public class CreateNewOrderTest {
    private PetStore petStore = new PetStore();

    @Test
    public void checkCreateOrder() {

//возвращает статуст код 200, передает параметры petI и complete и сравниваем ответ
        OrderDTO order = OrderDTO
                .builder()
                .petId(1)
                .complete(true)
                .build();

        petStore.createOrder(order)
                .statusCode(200)
                .body("petId", equalTo(1))
                .body("quantity", equalTo(0))
                .body("complete", equalTo(true));

        //возвращает статус код 200, передаем только quantity и сравниваем ответ
        OrderDTO order1 = OrderDTO
                .builder()
                .quantity(0)
                .build();

        petStore.createOrder(order1)
                .statusCode(200)
                .body("petId", equalTo(0))
                .body("quantity", equalTo(0))
                .body("complete", equalTo(false));
    }
}


