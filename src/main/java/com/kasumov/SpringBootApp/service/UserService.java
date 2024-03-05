package com.kasumov.SpringBootApp.service;



import com.kasumov.SpringBootApp.model.User;

import java.util.Optional;

public interface UserService extends GenericService<User,Long>{
    User create(User user);

    User update(User user);

    User findByUsername(String username);

   Optional<User> findByEmail(String email);



}

