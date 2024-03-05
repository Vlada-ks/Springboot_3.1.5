package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import ru.kata.spring.boot_security.demo.exception_handling.UserValidationException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class RestControllerApiAdmin {


    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleServiceImpl;


    public RestControllerApiAdmin(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;

        this.roleServiceImpl = roleServiceImpl;
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

    @PostMapping("/addUser")
    public ResponseEntity<HttpStatus> addUser(@RequestBody @Valid User newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserValidationException(bindingResult);
        }
        userServiceImpl.saveUser(newUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/updateUser")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User userUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserValidationException(bindingResult);
        }
        userServiceImpl.updateUser(userUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleServiceImpl.getListRoles();
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Integer id) {
        Role role = roleServiceImpl.getRoleById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
