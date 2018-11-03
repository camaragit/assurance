package com.ajit.assuranceprojet.dao;

import com.ajit.assuranceprojet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByLoginIgnoreCase(String username);
}
