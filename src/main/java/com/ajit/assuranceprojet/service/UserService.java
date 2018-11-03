package com.ajit.assuranceprojet.service;


import com.ajit.assuranceprojet.model.User;

import java.util.List;

public interface UserService {
    public User save(User user) ;

    List<User> findAll();

    User getUserByEmail(String name);
}
