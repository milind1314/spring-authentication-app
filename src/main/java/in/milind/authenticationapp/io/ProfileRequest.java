package in.milind.authenticationapp.io;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileRequest {
    @NotBlank(message = "Name should not be empty")
    private String name;

    @Email(message = "Enter valid email address")
    @NotNull(message = "Email should not be emplty")
    private String email;

    @Size(min = 6, message = "Minimum password should be atleast 6 characters")
    private String password;
}
