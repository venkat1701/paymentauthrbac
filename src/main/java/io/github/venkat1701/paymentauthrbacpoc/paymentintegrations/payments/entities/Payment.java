package io.github.venkat1701.paymentauthrbacpoc.paymentintegrations.payments.entities;


import io.github.venkat1701.paymentauthrbacpoc.rbac.entities.users.User;
import jakarta.persistence.*;

@Entity
@Table(name="payments")
public class Payment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    private String expiryDate;

    @Column(nullable = false)
    private String billingAddress;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
