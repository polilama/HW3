package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    public int id;
    public int petId;
    public int quantity;
    public LocalDate shipDate;
    public String status;
    public boolean complete;
}
