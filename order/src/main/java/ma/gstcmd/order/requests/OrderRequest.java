package ma.gstcmd.order.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequest {
    @NotBlank(message = "ce champs est vide.")
    @Size(max = 20, message = "ce champs est très long.")
    String clientId;

    @NotBlank(message = "ce champs est vide.")
    @Size(max = 20, message = "ce champs est très long.")
    String deliverId;

    @NotBlank(message = "ce champs est vide.")
    @Size(max = 20, message = "ce champs est très long.")
    String orderAddress;

    @NotBlank(message = "ce champs est vide.")
    @Size(max = 1, message = "ce champs est très long.")
    @Pattern(regexp = "^[rlci]$", message = "la valeur entrer n'est pas valid.")
    String orderState;

    @Positive(message = "la valeur entrer est négative ou null.")
    Double orderPrice;

    @PositiveOrZero(message = "la valeur entrer est négative.")
    Double orderDeliveryPrice;

    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+1")
    //@FutureOrPresent(message = "la date est très ancienne.")
    //Date deliveryDate;
}
