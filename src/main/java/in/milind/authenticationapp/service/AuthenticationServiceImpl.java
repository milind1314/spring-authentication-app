package in.milind.authenticationapp.service;

import in.milind.authenticationapp.entity.UserEntity;
import in.milind.authenticationapp.io.ProfileRequest;
import in.milind.authenticationapp.io.ProfileResponse;
import in.milind.authenticationapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

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

    @Override
    public ProfileResponse getProfile(String email) {
         UserEntity existingUser = userRepository.findByEmail(email)
                 .orElseThrow(() -> new UsernameNotFoundException("User not found: "+email));


        return convertToProfileResponse(existingUser);
    }

    @Override
    public void sendResetOtp(String email) {
       UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found: "+email));

       //Generate 6 digit otp
       String otp =String.valueOf(ThreadLocalRandom.current().nextInt(100000,1000000));

       //calculate expiry time(current time + 15 hours in milliseconds)
        long expiryTime = System.currentTimeMillis()+ (15 * 60 * 1000);

        //update the profile/user
        existingUser.setResetOtp(otp);
        existingUser.setResetOtpExpireAt(expiryTime);

        //save into the database
        userRepository.save(existingUser);


        try {
            emailService.sendResetOtpEmail(existingUser.getEmail(), otp);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to send email");
        }
    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found: "+email));

        if (existingUser.getResetOtp() == null || !existingUser.getResetOtp().equals(otp)){
            throw new RuntimeException("Invalid OTP");
        }

        if (existingUser.getResetOtpExpireAt() < System.currentTimeMillis()){
            throw new RuntimeException("OTP Expired");
        }

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        existingUser.setResetOtp(null);
        existingUser.setResetOtpExpireAt(0L);

        userRepository.save(existingUser);
    }

    @Override
    public void sendOtp(String email) {

        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found: "+email));

        if (existingUser.getIsAccountVerified() != null && existingUser.getIsAccountVerified()){
            return;
        }

        //Generate 6 digit otp
        String otp =String.valueOf(ThreadLocalRandom.current().nextInt(100000,1000000));

        //calculate expiry time(current time + 15 hours in milliseconds)
        long expiryTime = System.currentTimeMillis()+ (15 * 60 * 1000);

        //update the user entity
        existingUser.setVerifyOTP(otp);
        existingUser.setVerifyOtpExpireAt(expiryTime);

        //save into the database
        userRepository.save(existingUser);

        try {
            emailService.sendOtpEmail(existingUser.getEmail(), otp);
        } catch (Exception e) {
            throw new RuntimeException("Unable to send email.");
        }
    }

    @Override
    public void verifyOtp(String email, String otp) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found: "+email));

        if (existingUser.getVerifyOTP() == null || !existingUser.getVerifyOTP().equals(otp)){

            throw new RuntimeException("Invalid OTP");
        }

        if (existingUser.getVerifyOtpExpireAt() < System.currentTimeMillis()){
            throw new RuntimeException("OTP Expired");
        }

        existingUser.setIsAccountVerified(true);
        existingUser.setVerifyOTP(null);
        existingUser.setResetOtpExpireAt(0L);

        userRepository.save(existingUser);
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
