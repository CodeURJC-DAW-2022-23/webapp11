package com.techmarket.app.Repositories;

import com.techmarket.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {


    //<User> findById(Long id);
    //Not really sure if we need this one

    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    User findByEmail(String email);
    User findByFirstNameAndLastName(String firstName, String lastName);


    // Find user ids by their role
    List<User> findByRoles(String role);
}