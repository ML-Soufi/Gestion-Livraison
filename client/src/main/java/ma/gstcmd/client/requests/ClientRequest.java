package ma.gstcmd.client.requests;

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
public class ClientRequest {
    @NotBlank(message = "ce champs est vide.")
    @Size(max = 50, message = "ce champs est très long.")
    String clientFirstName;


    @NotBlank(message = "ce champs est vide.")
    @Size(max = 50, message = "ce champs est très long.")
    String clientLastName;

    @NotBlank(message = "ce champs est vide.")
    @Size(max = 13, message = "ce champs est très long.")
    @Pattern(regexp = "(\\+212|0)([567])\\d{8}", message = "ce champs ne respect pas le format télèphone.")
    String clientPhone;

    @NotBlank(message = "ce champs est vide.")
    @Size(max = 50, message = "ce champs est très long.")
    @Email(message = "ce champs ne respect pas le format email.")
    String clientEmail;

    @NotBlank(message = "ce champs est vide.")
    @Size(max = 150, message = "ce champs est très long.")
    String clientAddress;
}
