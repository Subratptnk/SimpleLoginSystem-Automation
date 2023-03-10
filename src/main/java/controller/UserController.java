package controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.User;
import repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	 @Autowired
	 private UserRepository userRepository;
	 
	 @PostMapping("/register")
	    public ResponseEntity<?> registerUser(@RequestBody User user) {
	        if (userRepository.findByEmail(user.getEmail()) != null) {
	            return new ResponseEntity<>("Email address already exists", HttpStatus.BAD_REQUEST);
	        }
	        userRepository.save(user);
	        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
	 }
	 
	 @PostMapping("/login")
	    public ResponseEntity<?> loginUser(@RequestBody User user) {
	        User existingUser = userRepository.findByEmail(user.getEmail());

	        if (existingUser == null) {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }

	        if (!existingUser.getPassword().equals(user.getPassword())) {
	            return new ResponseEntity<>("Invalid password", HttpStatus.BAD_REQUEST);
	        }

	        return new ResponseEntity<>("Login successful", HttpStatus.OK);
	    }
	 
	 @PostMapping("/reset-password")
	 public ResponseEntity<String> resetPassword(@RequestBody User user) {
	 // Get the user with the specified email
	 User existingUser = userRepository.findByEmail(user.getEmail());
	// If the user doesn't exist, return a 404 Not Found response
	 if (existingUser == null) {
	     return new ResponseEntity<>("User with email " + user.getEmail() + " not found", HttpStatus.NOT_FOUND);
	 }
	 
	 existingUser.setPassword(generateRandomPassword());
	 userRepository.save(existingUser);

	 return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
	 }
	 private String generateRandomPassword() {
		// Generate a random alphanumeric string with length 10
		String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
		builder.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
		}
		return builder.toString();
		}
}
