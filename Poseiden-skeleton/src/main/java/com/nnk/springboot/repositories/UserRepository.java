package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa repository for User table.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByUsername(String username);

}
