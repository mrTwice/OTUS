package ru.otus.basic.yampolskiy.service;

import ru.otus.basic.yampolskiy.entities.User;
import ru.otus.basic.yampolskiy.exception.UserAlreadyExistException;
import ru.otus.basic.yampolskiy.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    User createNewUser(User user) throws UserAlreadyExistException;

    User getUserById(String id) throws UserNotFoundException;

    List<User> getAllUsers();

    User removeUserById(String id) throws UserNotFoundException;

    User updateUser(User user) throws UserNotFoundException;

    User getUserByEmail(String email) throws UserNotFoundException;
}
