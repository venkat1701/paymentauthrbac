package io.github.venkat1701.paymentauthrbacpoc.rbac.repositories.users;

import io.github.venkat1701.paymentauthrbacpoc.rbac.entities.users.User;
import io.github.venkat1701.paymentauthrbacpoc.utils.exceptions.UserNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Transactional
    User findByEmail(String email) throws UserNotFoundException;
}
