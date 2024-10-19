package io.github.venkat1701.paymentauthrbacpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories

public class PaymentAuthRbacPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentAuthRbacPocApplication.class, args);
    }

}
