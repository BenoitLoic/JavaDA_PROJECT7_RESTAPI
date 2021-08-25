package com.nnk.springboot.services;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.CreateUserDto;
import com.nnk.springboot.dto.UpdateUserDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  UserRepository userRepositoryMock;
  @InjectMocks
  UserServiceImpl userService;

  @Test
  void findAllUser() {

    // GIVEN
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      User temp = new User();
      temp.setId(i);
      temp.setUsername("userName" + i);
      users.add(temp);
    }
    // WHEN
    when(userRepositoryMock.findAll()).thenReturn(users);
    // THEN
    assertEquals(3, userService.getAllUsers().size());

  }

  @Test
  void findAllUserEmptyList() {

    // GIVEN

    // WHEN
    when(userRepositoryMock.findAll()).thenReturn(new ArrayList<>());
    // THEN
    assertEquals(0, userService.getAllUsers().size());

  }

  @Test
  void createUser() {
    // GIVEN
    CreateUserDto createUserDto = new CreateUserDto();
    createUserDto.setUsername("username1");
    createUserDto.setFullname("fullname1");
    createUserDto.setRole("role1");
    createUserDto.setPassword("passtest0$");

    User expected = new User();
    expected.setUsername("username1");
    expected.setFullname("fullname1");
    expected.setRole("role1");


    // WHEN
    userService.createUser(createUserDto);
    // THEN
    verify(userRepositoryMock, times(1)).save(expected);

  }


  @Test
  void getUserWithIdValid() {
    // GIVEN
    User user = new User();
    user.setId(5);
    user.setUsername("username1");
    user.setFullname("fullname1");
    user.setRole("role1");
    user.setPassword("passtest0$");
    // WHEN
    when(userRepositoryMock.findById(5)).thenReturn(Optional.of(user));
    UpdateUserDto actual = userService.getUserWithID(5);
    // THEN
    assertEquals("username1", actual.getUsername());
    assertEquals(5, actual.getId());

  }

  @Test
  void getUserWithIdWhenDataNotFind_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(userRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

    // THEN
    assertThrows(DataNotFoundException.class, () -> userService.getUserWithID(1));

  }

  @Test
  void updateUserValid() {
    // GIVEN

    User user = new User();
    user.setId(5);
    user.setUsername("usern");
    user.setFullname("fulle1");
    user.setRole("ro1");
    user.setPassword("passtest0$");


    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setId(5);
    updateUserDto.setUsername("username1");
    updateUserDto.setFullname("fullname1");
    updateUserDto.setRole("role1");
    updateUserDto.setPassword("passtest0$UPDATED");


    User expected = new User();
    expected.setId(5);
    expected.setUsername("username1");
    expected.setFullname("fullname1");
    expected.setRole("role1");
    expected.setPassword("passtest0$UPDATED");
    // WHEN

    Mockito.when(userRepositoryMock.findById(anyInt())).thenReturn(Optional.of(user));
    userService.updateUser(2, updateUserDto);

    // THEN

    verify(userRepositoryMock).save(expected);



  }

  @Test
  void updateUserWithNoChange() {
    // GIVEN

    User user = new User();
    user.setId(5);
    user.setUsername("usern");
    user.setFullname("full1");
    user.setRole("ro1");
    user.setPassword("passtest0$");

    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setId(5);
    updateUserDto.setUsername("usern");
    updateUserDto.setFullname("full1");
    updateUserDto.setRole("ro1");
    updateUserDto.setPassword("passtest0$");
    // WHEN
    when(userRepositoryMock.findById(anyInt())).thenReturn(Optional.of(user));
    userService.updateUser(5, updateUserDto);
    // THEN
    verify(userRepositoryMock, times(1)).save(user);

  }

  @Test
  void updateUserWhenDataNotFound_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(userRepositoryMock.findById(2)).thenReturn(Optional.empty());
    // THEN
    assertThrows(DataNotFoundException.class, () -> userService.updateUser(2, any()));
  }

  @Test
  void deleteUserValid() {
    // GIVEN

    // WHEN
    when(userRepositoryMock.findById(2)).thenReturn(Optional.of(new User()));
    userService.deleteUser(2);
    // THEN
    verify(userRepositoryMock, times(1)).deleteById(2);

  }

  @Test
  void deleteUserWhenDataNotFound_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(userRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
    // THEN
    assertThrows(DataNotFoundException.class, () -> userService.deleteUser(1));

  }


}