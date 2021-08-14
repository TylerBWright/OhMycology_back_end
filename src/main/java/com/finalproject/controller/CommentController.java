package com.finalproject.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.entity.Comment;
import com.finalproject.entity.User;
import com.finalproject.repository.CommentRepository;
import com.finalproject.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class CommentController {

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userRepository;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/posts/{postId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity createComment(@RequestBody Comment comment, @PathVariable("postId") int postId,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		comment.setCreated(LocalDateTime.now());
		comment.setUserId(user.getId());
		comment.setPostId(postId);
		commentRepository.save(comment);
		comment.setUser(userRepository.findUserId(comment.getUserId()));
		return new ResponseEntity(comment, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	private ResponseEntity<List<Comment>> findByPostId(@PathVariable("postId") int postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/posts/{postId}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public ResponseEntity deleteComment(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId,
			HttpSession session) {
		User loginUser = (User) session.getAttribute("user");
		if (loginUser != null && loginUser.getRole().equals("REISHI")) {
			commentRepository.deleteComment(commentId);
			return new ResponseEntity(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

}
