package ro.andramates.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.andramates.realestate.domain.User;
import ro.andramates.realestate.domain.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByRole(UserRole role);
}