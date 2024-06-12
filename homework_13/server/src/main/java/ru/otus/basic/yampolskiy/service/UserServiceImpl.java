package ru.otus.basic.yampolskiy.service;

import ru.otus.basic.yampolskiy.entities.User;
import ru.otus.basic.yampolskiy.exception.UserAlreadyExistException;
import ru.otus.basic.yampolskiy.exception.UserNotFoundException;
import ru.otus.basic.yampolskiy.repository.UserRepository;
import ru.otus.basic.yampolskiy.repository.UserUserRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{
    private static UserServiceImpl userService;
    private final UserRepository userRepository = UserUserRepositoryImpl.getUserRepository();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
            return userService;
        }
        return userService;
    }

    @Override
    public User createNewUser(User user) throws UserAlreadyExistException {
        Optional<User> newUser = userRepository.add(user);
        if(newUser.isEmpty())
            throw new UserAlreadyExistException("Пользователь с таким email адресом существует", user.getEmail());
        return newUser.get();
    }

    @Override
    public User getUserById(String id) throws UserNotFoundException {
        Optional<User> user = userRepository.getById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("Пользователь с таким id не существует: ", id);
        return user.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @Override
    public User removeUserById(String id) throws UserNotFoundException {
        Optional<User> deletedUser = userRepository.removeById(id);
        if(deletedUser.isEmpty())
            throw new UserNotFoundException("Пользователь с таким id не существует: ", id);
        return deletedUser.get();
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {
        Optional<User> updatedUser = userRepository.update(user);
        if(updatedUser.isEmpty())
            throw new UserNotFoundException("Пользователь не существует: ");
        return updatedUser.get();
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.getByEmail(email);
        if (user.isEmpty())
            throw new UserNotFoundException("Пользователь с таким email не существует: ", email);
        return user.get();
    }
}
