package ma.gstcmd.product.requests;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequest {
    @NotBlank(message = "ce champs est vide.")
    @Size( max = 20, message = "ce champs est très long.")
    String productName;

//    @NotBlank(message = "ce champs est vide.")
//    @Size(max = 20, message = "ce champs est très long.")
//    @Pattern(regexp = "^.+\\.(png|jpg|jpeg)$", message = "ce champs a une extension non valid.")
//    String productImage;

    @Positive(message = "la valeur entrer est négative.")
    Double productPrice;

    @Positive(message = "la valeur entrer est négative.")
    int productQuantity;
}
