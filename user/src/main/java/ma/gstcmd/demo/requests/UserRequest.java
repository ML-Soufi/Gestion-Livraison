package ma.gstcmd.demo.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequest {
    @NotBlank(message = "ce champs est vide.")
    @Size(max = 30, message = "ce champs est très long.")
    String userFirstName;

    @NotBlank(message = "ce champs est vide.")
    @Size(max = 30, message = "ce champs est très long.")
    String userLastName;

    @NotBlank(message = "ce champs est vide.")
    @Size(min = 8, max = 10, message = "ce champs est doit avoir au minimum 8 ou 10 au plus.")
    String userPassword;

    @NotBlank(message = "ce champs est vide.")
    @Pattern(regexp = "^(Admin|User|Deliver)$",  message = "ce champs contient une valeur non valid.")
    String userRole;
}
