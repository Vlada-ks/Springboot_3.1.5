package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class RestControllerApiAdmin {


    private final UserServiceImpl userServiceImpl;


    public RestControllerApiAdmin(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;

    }

    @GetMapping
    public ResponseEntity<List<User>> showAllUsers() {
        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = userServiceImpl.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody @Valid User newUser) {
        userServiceImpl.saveUser(newUser);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User userUpdate) {
        userServiceImpl.updateUser(userUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
