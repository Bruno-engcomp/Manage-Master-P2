package com.sigfe.backend.repository;

import com.sigfe.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

    boolean existsByUsername(String username);
}
