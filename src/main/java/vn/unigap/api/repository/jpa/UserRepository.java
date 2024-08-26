package vn.unigap.api.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.unigap.api.entity.jpa.User;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigInteger> {
    boolean existsUserByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
