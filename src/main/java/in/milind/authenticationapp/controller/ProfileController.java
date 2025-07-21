package in.milind.authenticationapp.controller;

import in.milind.authenticationapp.io.ProfileRequest;
import in.milind.authenticationapp.io.ProfileResponse;
import in.milind.authenticationapp.service.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest request)
    {
        ProfileResponse response = authenticationService.createProfile(request);
        //TODO:send welcome Email

        return response;
    }


    @GetMapping("/test")
    public String test(){
       return  "Auth is working";
    }
}
