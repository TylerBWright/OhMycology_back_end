package com.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.finalproject.entity.User;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT U FROM User U WHERE U.username = ?1")
	User findUsername(String username);
	
	@Query("SELECT U FROM User U WHERE U.id = ?1")
	User findUserId(int Id);
}
