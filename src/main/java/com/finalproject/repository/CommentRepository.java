package com.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.finalproject.entity.Comment;

	@Repository

	public interface CommentRepository extends JpaRepository<Comment, Integer> {	
		
		@Query("SELECT C FROM Comment C WHERE C.postId = ?1 ORDER BY C.created ASC") List<Comment> findByPostId(int postId);
		
		@Transactional
		@Modifying
		@Query("DELETE FROM Comment C WHERE C.id = ?1") void deleteComment(int commentId);

		@Transactional
		@Modifying
		@Query("DELETE FROM Comment C WHERE C.postId = ?1")
		void deleteCommentsFromPost(int postId);
		
}
