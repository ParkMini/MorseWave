package kr.pah.morsewave.repository;

import java.util.Optional;

import kr.pah.morsewave.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}