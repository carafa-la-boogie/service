package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.data.User;
import ro.unibuc.hello.service.UserService;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Constructor-based Dependency Injection
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get a user by their email
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws EntityNotFoundException {
        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) throws EntityNotFoundException {
        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Patch an existing user
    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable String id, @RequestBody User user) throws EntityNotFoundException {
        User patchedUser = userService.patchUser(id, user);
        return new ResponseEntity<>(patchedUser, HttpStatus.OK);
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) throws EntityNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.NO_CONTENT);
    }
}
