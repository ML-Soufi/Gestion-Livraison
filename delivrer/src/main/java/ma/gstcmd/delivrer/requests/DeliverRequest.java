package ma.gstcmd.delivrer.requests;

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
public class DeliverRequest {
    @NotBlank(message = "ce champs est obligatoire.")
    @Size(max = 10, message = "ce champs est très long.")
    @Pattern(regexp = "^[a-z|aA-Z]{2}\\d+$", message = "ce champs ne respect pas le format cin.")
    String deliverCni;

    @NotBlank(message = "ce champs est obligatoire.")
    @Size(max = 50, message = "ce champs est très long.")
    String firstName;

    @NotBlank(message = "ce champs est obligatoire.")
    @Size(max = 50, message = "ce champs est très long.")
    String lastName;

    @NotBlank(message = "ce champs est obligatoire.")
    @Size(max = 1, message = "ce champs est très long.")
    @Pattern(regexp = "^[fm]$", message = "la valeur entrer n'est pas valid.")
    String deliverSex;

    @NotBlank(message = "ce champs est obligatoire.")
    @Size(max = 13, message = "ce champs est très long.")
    @Pattern(regexp = "^(\\+212|0)([567])\\d{8}$", message = "ce champs ne respect pas le format télèphone.")
    String deliverPhone;

    @NotBlank(message = "ce champs est obligatoire.")
    @Size(max = 50, message = "ce champs est très long.")
    @Email(message = "ce champs ne respect pas le format email.")
    String deliverEmail;
}
