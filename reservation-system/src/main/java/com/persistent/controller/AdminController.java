
package com.persistent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.model.User;
import com.persistent.repository.UserRepository;

@RestController

@RequestMapping("/secure/auth")
public class AdminController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/admin/add")
	public String addUser(@RequestBody User user) {
		String pwd = user.getPassword();
		String encrptedPwd = passwordEncoder.encode(pwd);
		user.setPassword(encrptedPwd);
		userRepository.save(user);
		return "user Added Successfully";

	}

}
