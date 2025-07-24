package in.milind.authenticationapp.controller;

import in.milind.authenticationapp.io.ProfileRequest;
import in.milind.authenticationapp.io.ProfileResponse;
import in.milind.authenticationapp.service.AuthenticationServiceImpl;
import in.milind.authenticationapp.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final AuthenticationServiceImpl authenticationService;

    private final EmailService emailService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest request)
    {
        ProfileResponse response = authenticationService.createProfile(request);

        emailService.sendWelcomeEmail(response.getEmail(), response.getName());

        return response;
    }

@GetMapping("/profile")
    public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name") String email){
        return authenticationService.getProfile(email);
    }


}
