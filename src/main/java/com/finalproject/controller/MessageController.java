package com.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.repository.MessageRepository;

@RestController
public class MessageController {

	@Autowired
	MessageRepository messageRepository;
	
}
