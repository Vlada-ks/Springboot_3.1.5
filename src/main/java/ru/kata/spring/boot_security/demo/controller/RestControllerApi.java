package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestControllerApi {


    private final UserService userService;


    public RestControllerApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> showAllUser() {
        return userService.getAllUsers();
    }

//    @PostMapping("/new")
//    public String addNewUser(Model model) {
//        model.addAttribute("newUser", new User());
//        model.addAttribute("roles", roleServiceImpl.getListRoles());
//        return "redirect:/admin";
//    }
//
//    @PostMapping()
//    public String saveUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult, Model model) {
//        model.addAttribute("roles", roleServiceImpl.getListRoles());
//        if (bindingResult.hasErrors())
//            return "redirect:/admin";
//        userServiceImpl.saveUser(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/{id}")
//    public String showUpdate(@PathVariable("id") Integer id, Model model) {
//        User user = userServiceImpl.getUserById(id);
//        model.addAttribute("roles", roleServiceImpl.getListRoles());
//        model.addAttribute("newUpdate", user);
//        return "redirect:/admin";
//    }
//
//
//    @PostMapping("/{id}")
//    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult bindingResult, Model model) {
//        model.addAttribute("newUpdate", userServiceImpl.getUserById(id));
//        model.addAttribute("roles", roleServiceImpl.getListRoles());
//        if (bindingResult.hasErrors())
//            return "redirect:/admin";
//        userServiceImpl.updateUser(user);
//        return "redirect:/admin";
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable("id") Integer id) {
//        userServiceImpl.deleteUser(id);
//        return "redirect:/admin";
//    }

}
