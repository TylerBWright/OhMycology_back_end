package com.finalproject.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.finalproject.entity.Comment;
import com.finalproject.entity.Post;
import com.finalproject.entity.User;
import com.finalproject.repository.CommentRepository;
import com.finalproject.repository.PostRepository;
import com.finalproject.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class PostController {

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	CommentRepository commentRepository;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity createPost(@RequestBody Post post, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		post.setCreated(LocalDateTime.now());
		post.setUserId(user.getId());
		postRepository.save(post);
		return new ResponseEntity(post, HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity getPosts() {
		List<Post> posts = postRepository.findAll();
		for (int i = 0; i < posts.size(); i++) {
			User user = userRepository.getById(posts.get(i).getUserId());
			posts.get(i).setUser(user);
		}
		
		return new ResponseEntity(posts, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/posts/{postId}/image", method = RequestMethod.POST)
	public ResponseEntity createPost(@RequestParam("file") MultipartFile file, @PathVariable("postId") int postId, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		Blob blob;
		try {
			blob = new SerialBlob(file.getBytes());
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		postRepository.addImage(blob, postId);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	private ResponseEntity getPost(@PathVariable("postId") int postId) {
		Post post = postRepository.findId(postId);
		if (post == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		List<Comment> comments = commentRepository.findByPostId(postId);
		for (int i = 0; i < comments.size(); i++) {
			comments.get(i).setUser(userRepository.findUserId(comments.get(i).getUserId()));
		}
		post.setComments(comments);
		return ResponseEntity.ok(post);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/posts/{postId}/image", method = RequestMethod.GET)
	@ResponseBody
	private ResponseEntity getPostImage(@PathVariable("postId") int postId) throws SQLException, IOException {
		Post post = postRepository.findId(postId);
		if (post == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		Blob image = post.getImage();
		byte[] imageBytes = image.getBytes(1, (int) image.length());
		InputStream is = new ByteArrayInputStream(imageBytes);
		
		String mimeType = URLConnection.guessContentTypeFromStream(is);
		return ResponseEntity.ok()
				.contentLength(imageBytes.length)
				.contentType(MediaType.parseMediaType(mimeType))
                .body(imageBytes);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public ResponseEntity deletePost(@PathVariable("postId") int postId, HttpSession session) {
		User loginUser = (User) session.getAttribute("user");
		if (loginUser != null && loginUser.getRole().equals("REISHI")) {
			commentRepository.deleteCommentsFromPost(postId);
			postRepository.deletePost(postId);
			return new ResponseEntity(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}
}
