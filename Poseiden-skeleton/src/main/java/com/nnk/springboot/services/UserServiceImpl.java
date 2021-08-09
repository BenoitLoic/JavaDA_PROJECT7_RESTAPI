package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.CreateUserDto;
import com.nnk.springboot.dto.GetUserDto;
import com.nnk.springboot.dto.UpdateUserDto;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implementation for UserService.
 * Contains method used by User CRUD controller.
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LogManager.getLogger(getClass().getName());

    @Autowired
    UserRepository userRepository;

    /**
     * This method will return all users saved in DB.
     *
     * @return the users collection
     */
    @Override
    public Collection<GetUserDto> getAllUsers() {

        log.trace("Getting all users.");

        Collection<GetUserDto> getUsers = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            GetUserDto temp = new GetUserDto();
            BeanUtils.copyProperties(user, temp, "password");
            getUsers.add(temp);
        }

        log.info("Returning " + getUsers.size() + " user.");

        return getUsers;
    }

    /**
     * This method will return the user with the given Id.
     *
     * @param id the user id.
     * @return the dto fo the user
     */
    @Override
    public UpdateUserDto getUserWithID(int id) {

        log.info("Get user with id: " + id);
        Optional<User> temp = userRepository.findById(id);

        if (temp.isEmpty()) {
            log.warn("KO - can't find user wih id: " + id);
            throw new NoSuchElementException("Error - can't find user.");
        }

        UpdateUserDto userDto = new UpdateUserDto();
        BeanUtils.copyProperties(temp.get(), userDto, "password");

        log.info("Return user: " + userDto);

        return userDto;
    }

    /**
     * This method will create a new user.
     *
     * @param createUserDto the user to create.
     */
    @Transactional
    @Override
    public void createUser(CreateUserDto createUserDto) {

        createUserDto.setPassword(new BCryptPasswordEncoder().encode(createUserDto.getPassword()));

        User user = new User();
        BeanUtils.copyProperties(createUserDto, user);

        log.info("Saving user : " + user);

        userRepository.save(user);

        log.info("Success - User saved with id: " + user.getId());
    }

    /**
     * This method will update the given user.
     *
     * @param id            the user id
     * @param updateUserDto the user's data to update
     */
    @Transactional
    @Override
    public void updateUser(int id, UpdateUserDto updateUserDto) {

        UpdateUserDto userToUpdate = this.getUserWithID(id);
        User userEntity = new User();
        BeanUtils.copyProperties(userToUpdate, userEntity);

        if (!updateUserDto.getPassword().isBlank()) {
            log.trace("updating password.");
            userEntity.setPassword(new BCryptPasswordEncoder().encode(updateUserDto.getPassword()));
        }
        if (!updateUserDto.getUsername().isBlank()) {
            log.trace("updating username.");
            userEntity.setUsername(updateUserDto.getUsername());
        }
        if (!updateUserDto.getFullname().isBlank()) {
            log.trace("updating fullname.");
            userEntity.setFullname(updateUserDto.getFullname());
        }
        if (!updateUserDto.getRole().isBlank()) {
            log.trace("updating role.");
            userEntity.setRole(updateUserDto.getRole());
        }

        log.info("Updating user : " + userEntity);

        userRepository.save(userEntity);

        log.info("Update - OK");
    }

    /**
     * this method will delete the user identified by the given id.
     *
     * @param id the id of the user to delete
     */
    @Override
    public void deleteUser(int id) {

        log.info("Deleting user with id: " + id);

        if (userRepository.findById(id).isEmpty()) {
            log.error("KO - can't find user with id: " + id);
            throw new NoSuchElementException("KO - can't find user");
        }

        userRepository.deleteById(id);
        log.info("Delete - OK");

    }
}
