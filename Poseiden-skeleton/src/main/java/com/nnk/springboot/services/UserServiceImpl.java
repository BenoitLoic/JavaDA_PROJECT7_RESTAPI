package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.CreateUserDto;
import com.nnk.springboot.dto.GetUserDto;
import com.nnk.springboot.dto.UpdateUserDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for UserService.
 * Contains method used by User CRUD controller.
 */
@Service
public class UserServiceImpl implements UserService {

  private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

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
   * This method will return the user with the given id.
   *
   * @param id the user id.
   * @return the dto fo the user
   */
  @Override
  public UpdateUserDto getUserWithId(int id) {

    log.info("Get user with id: " + id);
    Optional<User> temp = userRepository.findById(id);

    if (temp.isEmpty()) {
      log.warn("KO - can't find user wih id: " + id);
      throw new DataNotFoundException("Error - can't find user.");
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

    log.trace("Updating User with id: "
        + id
        + " and data: "
        + updateUserDto);

    Optional<User> optional = userRepository.findById(id);

    if (optional.isEmpty()) {
      log.warn("KO - Error can't find User with id: "
          + id);
      throw new DataNotFoundException("Error can't find user.");
    }
    User userEntity = optional.get();
    int count = 0;

    log.trace("updating password.");
    userEntity.setPassword(new BCryptPasswordEncoder().encode(updateUserDto.getPassword()));
    count++;

    if (!updateUserDto.getUsername().equals(userEntity.getUsername())) {
      log.trace("updating username.");
      userEntity.setUsername(updateUserDto.getUsername());
      count++;
    }
    if (!updateUserDto.getFullname().equals(userEntity.getFullname())) {
      log.trace("updating fullname.");
      userEntity.setFullname(updateUserDto.getFullname());
      count++;
    }
    if (!updateUserDto.getRole().equals(userEntity.getRole())) {
      log.trace("updating role.");
      userEntity.setRole(updateUserDto.getRole());
      count++;
    }

    log.info("Updating user : " + userEntity);

    userRepository.save(userEntity);

    log.info("Update - OK for user: "
        + id
        + ". "
        + count
        + " fields changed.");
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
      throw new DataNotFoundException("KO - can't find user");
    }

    userRepository.deleteById(id);
    log.info("Delete - OK");

  }
}
