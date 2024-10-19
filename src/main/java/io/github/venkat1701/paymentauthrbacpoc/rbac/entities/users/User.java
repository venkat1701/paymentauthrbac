package io.github.venkat1701.paymentauthrbacpoc.rbac.entities.users;

import io.github.venkat1701.paymentauthrbacpoc.paymentintegrations.payments.entities.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String mobile;
    private String gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Payment> paymentInfo = new HashSet<>();
}
