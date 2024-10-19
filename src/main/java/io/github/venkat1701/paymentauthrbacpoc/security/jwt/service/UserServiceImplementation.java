package io.github.venkat1701.paymentauthrbacpoc.security.jwt.service;

import io.github.venkat1701.paymentauthrbacpoc.rbac.entities.users.User;
import io.github.venkat1701.paymentauthrbacpoc.rbac.repositories.users.UserRepository;
import io.github.venkat1701.paymentauthrbacpoc.security.jwt.dto.UserRegistrationDTO;
import io.github.venkat1701.paymentauthrbacpoc.security.jwt.service.core.UserService;
import io.github.venkat1701.paymentauthrbacpoc.security.jwt.utils.JwtProvider;
import io.github.venkat1701.paymentauthrbacpoc.utils.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(username);
            if(user == null) {
                throw new UserNotFoundException("User not found");
            }
            List<GrantedAuthority> authorities = new ArrayList<>();
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        } catch(UserNotFoundException unfe) {
            unfe.printStackTrace();
            return null;
        }
    }

    public User findUserById(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user.get();
        }

        throw new UserNotFoundException("User with corresponding email not found");
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);
        if(user == null)
            throw new UserNotFoundException("User with profile not found");

        return user;
    }

    @Override
    public User registerUser(UserRegistrationDTO registrationDTO) {
        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setGender(registrationDTO.getGender());
        user.setMobile(registrationDTO.getPhone());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        return userRepository.save(user);
    }
}
