package com.coremaker.authentication.repository;

import com.coremaker.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(final String email);

    Optional<User> findByName(final String name);
}
