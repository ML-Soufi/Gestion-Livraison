package ma.gstcmd.orderdetail.requests;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailRequest {
    @NotBlank(message = "ce champs est vide.")
    @Size( max = 20, message = "ce champs est très long.")
    String orderId;

    @NotBlank(message = "ce champs est vide.")
    @Size( max = 20, message = "ce champs est très long.")
    String productId;

    @Positive(message = "la valeur entrer est négative ou null.")
    int orderedQuantity;
}
