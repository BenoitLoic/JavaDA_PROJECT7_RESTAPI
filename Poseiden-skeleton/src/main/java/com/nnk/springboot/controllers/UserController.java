package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Contains CRUD method for User model/entity.
 */
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * This method will get all user in data base.
     *
     * @param model the model that serve to inject user list for the view
     * @return the list of all user.
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    /**
     * This method will return the form to add a new user.
     *
     * @return the html form.
     */
    @GetMapping("/user/add")
    public String addUserForm(User user) {
        return "user/add";
    }

    /**
     * This method will create a new user.
     *
     * @param user   the user to create
     * @param result validation check for user.
     * @param model  the model that serve to inject user list for the view
     * @return redirection to /user/list (get all user)
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * This method will return the update form with user's data with the given id.
     *
     * @param id    the id of the user to get data.
     * @param model the model where we inject the user data.
     * @return the form with user's data inside.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * This method will update the user with the given id.
     *
     * @param id     the id of the user to update
     * @param user   the user to update
     * @param result validation check
     * @param model  the model that serve to inject user list for the view
     * @return redirection to /user/list (get all user)
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    /**
     * This method delete the user with the given id.
     *
     * @param id    the id of the user to delete
     * @param model the model that serve to inject user list for the view
     * @return redirection to /user/list (get all user)
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
