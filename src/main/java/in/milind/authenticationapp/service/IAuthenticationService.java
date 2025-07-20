package in.milind.authenticationapp.service;

import in.milind.authenticationapp.io.ProfileRequest;
import in.milind.authenticationapp.io.ProfileResponse;

public interface IAuthenticationService {
   ProfileResponse createProfile(ProfileRequest request);
}
