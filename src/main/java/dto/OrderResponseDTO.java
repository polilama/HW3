package dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderResponseDTO {
    private UUID id;
    private int petId;
    private int quantity;
    private Boolean complete;
}
