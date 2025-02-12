package service;

import dto.OrderDTO;

public class OrderService {
    public OrderDTO createOrderDTO(Long petId, Integer quantity, boolean complete) {
        return OrderDTO.builder()
                .petId(petId)
                .quantity(quantity)
                .complete(complete)
                .build();
    }
}
