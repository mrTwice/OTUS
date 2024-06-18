package ru.otus.basic.yampolskiy.repository;

import ru.otus.basic.yampolskiy.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserUserRepositoryImpl implements UserRepository {
    private static UserUserRepositoryImpl userRepository;
    private final ConcurrentMap<String, User> users = new ConcurrentHashMap<>();

    private UserUserRepositoryImpl() {
    }

    public static UserUserRepositoryImpl getUserRepository() {
        if(userRepository == null) {
            userRepository = new UserUserRepositoryImpl();
            return userRepository;
        }
        return userRepository;
    }

    @Override
    public Optional<User> add(User user) {
        String email = user.getEmail();
        Optional<User> createdUser = users.values().stream().filter(user1 -> user1.getEmail().equals(email)).findFirst();
        if (createdUser.isEmpty()) {
            users.put(user.getId(), user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> removeById(String id) {
       return Optional.ofNullable(users.remove(id));
    }

    @Override
    public Optional<User> update(User user) {
        String id = user.getId();
        Optional<User> updatedUser = Optional.ofNullable(users.get(id));
        if (updatedUser.isPresent()) {
            users.put(updatedUser.get().getId(), updatedUser.get());
            return updatedUser;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        Optional<User> user = users.values().stream().filter(user1 -> user1.getEmail().equals(email)).findFirst();
        return user;
    }
}
