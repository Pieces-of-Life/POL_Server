package com.umc.pol.User.repository;

import com.umc.pol.User.repository.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
