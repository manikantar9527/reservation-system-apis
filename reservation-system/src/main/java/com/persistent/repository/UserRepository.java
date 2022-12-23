package com.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.persistent.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

}
