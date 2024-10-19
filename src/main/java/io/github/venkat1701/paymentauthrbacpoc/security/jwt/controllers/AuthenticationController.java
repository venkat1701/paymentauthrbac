package io.github.venkat1701.paymentauthrbacpoc.security.jwt.controllers;

import io.github.venkat1701.paymentauthrbacpoc.rbac.entities.users.User;
import io.github.venkat1701.paymentauthrbacpoc.rbac.repositories.users.UserRepository;
import io.github.venkat1701.paymentauthrbacpoc.security.jwt.dto.UserRegistrationDTO;
import io.github.venkat1701.paymentauthrbacpoc.security.jwt.dto.requests.LoginRequestDTO;
import io.github.venkat1701.paymentauthrbacpoc.security.jwt.dto.response.AuthResponseDTO;
import io.github.venkat1701.paymentauthrbacpoc.security.jwt.service.core.UserService;
import io.github.venkat1701.paymentauthrbacpoc.security.jwt.utils.JwtProvider;
import io.github.venkat1701.paymentauthrbacpoc.utils.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins="http://localhost:3000", allowCredentials="true")
public class AuthenticationController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(UserService userService, UserRepository repository, JwtProvider provider, PasswordEncoder encoder) {
        this.userService = userService;
        this.userRepository = repository;
        this.jwtProvider = provider;
        this.passwordEncoder = encoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> createUserHandler(@RequestBody UserRegistrationDTO registrationDTO)
            throws UserNotFoundException, UsernameNotFoundException {

        String email = registrationDTO.getEmail();
        String password = registrationDTO.getPassword();

        if (userRepository.findByEmail(email) != null) {
            throw new UserNotFoundException("User with this corresponding email already exists!");
        }
        User createdUser = userService.registerUser(registrationDTO);
        List<GrantedAuthority> authorities = new ArrayList<>();

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication, createdUser.getId());

        return ResponseEntity.ok().body(new AuthResponseDTO(jwt, "User Generated Successfully"));
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDTO> loginUserHandler(@RequestBody LoginRequestDTO loginRequestDTO) throws UserNotFoundException {
        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();
        Authentication authentication = this.authenticate(email, password);
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new BadCredentialsException("Invalid Email or Password");
        }

        String jwt = jwtProvider.generateToken(authentication, user.getId());
        return ResponseEntity.ok().body(new AuthResponseDTO(jwt, "User Successfully Login"));

    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = userService.loadUserByUsername(email);
        if(userDetails == null) {
            throw new BadCredentialsException("Invalid Email or Password");
        } if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
