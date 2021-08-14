package com.finalproject.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.entity.User;
import com.finalproject.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity registerUser(@RequestBody User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setCreated(LocalDateTime.now());
		user.setRole("MOREL");
		try {
			userRepository.save(user);
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	private ResponseEntity getUser(@PathVariable("id") int id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		User userLookUp = userRepository.findUserId(id);
		if (userLookUp == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(userLookUp);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	private ResponseEntity getUserMain(@PathVariable("id") int id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		if (user != null && !user.getRole().equals("REISHI")) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		User userLookUp = userRepository.findUserId(id);
		if (userLookUp == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(userLookUp);
	}

	// Only Reishi (admin) has the rights to update another user's profile
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable int id, HttpSession session) {
		User loginUser = (User) session.getAttribute("user");
		if ((loginUser != null && (loginUser.getId() == user.getId() || loginUser.getRole().equals("REISHI")))) {
			Optional<User> userOptional = userRepository.findById(id);
			if (!userOptional.isPresent())
				return ResponseEntity.notFound().build();
			user.setId(id);
			user.setRole(loginUser.getRole());
			user.setPassword(loginUser.getPassword());
			user.setCreated(loginUser.getCreated());
			userRepository.save(user);
			return ResponseEntity.noContent().build();
		}
		System.out.println("Editing a user is unauthorized.");
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/userLogin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity userLogin(@RequestBody User requestUser, HttpSession session) {
		User user = userRepository.findUsername(requestUser.getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (user != null && encoder.matches(requestUser.getPassword(), user.getPassword())
				&& user.getDeleted() == null) {
			session.setAttribute("user", user);
			System.out.println("Logged in.");
			return ResponseEntity.ok(user);
		}
		System.out.println("Unable to login.");
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/userLogout", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity userLogout(HttpSession session) {
		session.removeAttribute("user");
		System.out.println("Logged out.");
		return new ResponseEntity(HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public ResponseEntity userDeactivate(@PathVariable int userId, HttpSession session) {
		User loginUser = (User) session.getAttribute("user");

		// Check if a user is logged in and they have the reishi role.
		if (loginUser != null && loginUser.getRole().equals("REISHI")) {
			// Try to get the user by the passed in user id.
			Optional<User> deletedUser = userRepository.findById(userId);
			// If no user is found for user id, return 404
			if (deletedUser.isEmpty()) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}

			// Set deleted time on the user to time now.
			User user = deletedUser.get();
			user.setDeleted(LocalDateTime.now());
			userRepository.save(user);

			System.out.println("User deactivated.");
			return new ResponseEntity(HttpStatus.ACCEPTED);
		}
		System.out.println("Unable to delete user.");
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<User> ping(HttpSession session) {
		return ResponseEntity.ok((User) session.getAttribute("user"));
	}

}
