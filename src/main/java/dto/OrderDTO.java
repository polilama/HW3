package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    public Long id;
    public Long petId;
    public Integer quantity;
    public Boolean complete;
    public Long orderId;
}
