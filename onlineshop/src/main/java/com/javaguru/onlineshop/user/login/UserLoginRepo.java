package com.javaguru.onlineshop.user.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepo extends JpaRepository<UserLogin, Long> {

    UserLogin findByUsername(String username);
}
