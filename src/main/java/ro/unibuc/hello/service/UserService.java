package ro.unibuc.hello.service;

import ro.unibuc.hello.data.User;
import ro.unibuc.hello.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Find user by their email
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Find user by their String ID
    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    // Get all users
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Update an existing user
    public User updateUser(String id, User user) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        user.setId(id);  // Ensure the ID is set for the update
        return userRepository.save(user);
    }

    // Patch a user (partial update)
    public User patchUser(String id, User user) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        user.setId(id);  // Ensure the ID is set for the patch
        return userRepository.save(user);
    }

    // Delete a user by ID
    public void deleteUser(String id) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}
