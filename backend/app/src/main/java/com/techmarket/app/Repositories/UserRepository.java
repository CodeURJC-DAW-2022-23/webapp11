package com.techmarket.app.Repositories;


import com.techmarket.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    //<User> findById(Long id);
    //Not really sure if we need this one

    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    List<User> findByEmail(String email);
    User findByFirstNameAndLastName(String firstName, String lastName);



}