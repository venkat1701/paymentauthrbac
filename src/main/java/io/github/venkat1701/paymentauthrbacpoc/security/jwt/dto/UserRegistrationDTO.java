package io.github.venkat1701.paymentauthrbacpoc.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String gender;
}
