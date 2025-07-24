package in.milind.authenticationapp.io;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "Password is required")
    private String newPassword;

    @NotBlank(message = "OTP is required")
    private String otp;

    @NotBlank(message = "Email is required")
    private String email;
}
