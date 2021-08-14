package com.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.finalproject.entity.Message;

@Repository

public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("SELECT M FROM Message M WHERE M.id = ?1") Message findId(int id);
	
}
