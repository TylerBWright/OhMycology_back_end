package com.finalproject.repository;

import java.sql.Blob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.finalproject.entity.Post;

@Repository

public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query("SELECT P FROM Post P WHERE P.id = ?1")
	Post findId(int id);

	@Transactional
	@Modifying
	@Query("DELETE FROM Post P WHERE P.id = ?1")
	void deletePost(int postId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Post p SET p.image = ?1 WHERE p.id = ?2")
	void addImage(Blob file, int postId);
}
