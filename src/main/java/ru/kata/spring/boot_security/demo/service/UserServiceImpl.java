package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(@Param("username") String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Invalid user Id:" + id));
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        User user1 = this.getUserById(id);
        if (user1 == null) {
            throw new EntityNotFoundException("User with this id not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {

        User userById = userRepository.getById(user.getId());

        if (!user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(userById.getPassword());
        }
        userRepository.save(user);
    }
}





