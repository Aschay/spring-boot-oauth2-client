package com.aschay.keycloakoauth2client.controller;

import java.security.Principal;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping(value = "/", produces = "application/json")
	HashMap<String, String> printMsg() {
		HashMap<String, String> map = new HashMap<>();
		String greeting = "Greeting";
		String message = "Page for everyone";
		map.put(greeting, message);
		return map;
	}

	@GetMapping(value = "/secure", produces = "application/json")
	HashMap<String, String> printJSON1() {
		HashMap<String, String> map = new HashMap<>();
		map.put("note", "this page is secured, only of authorized!");
		return map;
	}

	@GetMapping("/user")
	public Principal getUser(Principal user) {
		return user;
	}

}
