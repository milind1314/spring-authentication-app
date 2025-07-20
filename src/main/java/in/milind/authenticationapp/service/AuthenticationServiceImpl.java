package in.milind.authenticationapp.service;

import in.milind.authenticationapp.entity.UserEntity;
import in.milind.authenticationapp.io.ProfileRequest;
import in.milind.authenticationapp.io.ProfileResponse;
import in.milind.authenticationapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {

        UserEntity newProfile = convertToUserEntity(request);

        if (!userRepository.existsByEmail(request.getEmail()))
        {
            newProfile = userRepository.save(newProfile);
            return convertToProfileResponse(newProfile);
        }

        throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists");


    }

    private ProfileResponse convertToProfileResponse(UserEntity newProfile) {

        return ProfileResponse.builder()
                .name(newProfile.getName())
                .email(newProfile.getEmail())
                .userId(newProfile.getUserId())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build();
    }

    private UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .isAccountVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOTP(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .build();
    }
}
