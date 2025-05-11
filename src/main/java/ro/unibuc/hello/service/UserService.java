package ro.unibuc.hello.service;

import ro.unibuc.hello.data.User;
import ro.unibuc.hello.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.exception.EntityNotFoundException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MeterRegistry meterRegistry;

    @Autowired
    public UserService(UserRepository userRepository, MeterRegistry meterRegistry) {
        this.userRepository = userRepository;
        this.meterRegistry = meterRegistry;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        Counter counter = meterRegistry.counter("custom_counter", "type", "user_created");
        counter.increment();
        return userRepository.save(user);
    }

    public User updateUser(String id, User user) throws EntityNotFoundException {
        Counter counter = meterRegistry.counter("custom_counter", "type", "user_updated");
        counter.increment();
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    public User patchUser(String id, User user) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(String id) throws EntityNotFoundException {
        Counter counter = meterRegistry.counter("custom_counter", "type", "user_deleted");
        counter.increment();
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
