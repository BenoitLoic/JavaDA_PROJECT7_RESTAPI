package com.nnk.springboot.services;

import com.nnk.springboot.dto.CreateUserDto;
import com.nnk.springboot.dto.GetUserDto;
import com.nnk.springboot.dto.UpdateUserDto;
import java.util.Collection;

/**
 * Interface for User service.
 * Contains method used by user CRUD controller.
 */
public interface UserService {

  /**
   * This method will return all users saved in DB.
   *
   * @return the users collection
   */
  Collection<GetUserDto> getAllUsers();

  /**
   * This method will return the user with the given id.
   *
   * @param id the user id.
   * @return the dto fo the user
   */
  UpdateUserDto getUserWithId(int id);

  /**
   * This method will create a new user.
   *
   * @param createUserDto the user to create.
   */
  void createUser(CreateUserDto createUserDto);

  /**
   * This method will update the given user.
   *
   * @param id            the user id
   * @param updateUserDto the user's data to update
   */
  void updateUser(int id, UpdateUserDto updateUserDto);

  /**
   * this method will delete the user identified by the given id.
   *
   * @param id the id of the user to delete
   */
  void deleteUser(int id);

}
