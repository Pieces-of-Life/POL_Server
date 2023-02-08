package com.umc.pol.domain.user.repository;

import com.umc.pol.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
