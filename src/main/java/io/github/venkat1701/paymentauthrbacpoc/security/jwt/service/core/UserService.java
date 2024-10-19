package io.github.venkat1701.paymentauthrbacpoc.security.jwt.service.core;

import io.github.venkat1701.paymentauthrbacpoc.rbac.entities.users.User;
import io.github.venkat1701.paymentauthrbacpoc.security.jwt.dto.UserRegistrationDTO;
import io.github.venkat1701.paymentauthrbacpoc.utils.exceptions.UserNotFoundException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public User findUserById(Long userId) throws UserNotFoundException;

    public User findUserProfileByJwt(String jwt) throws Exception;

    public User registerUser(UserRegistrationDTO registrationDTO);

    public UserDetails loadUserByUsername(String username);
}
