package com.techmarket.app.Repositories;

import com.techmarket.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {


    User findByEmail(String email);

    // Find user ids by their role
    List<User> findByRoles(String role);
}