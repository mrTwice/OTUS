package ru.otus.basic.yampolskiy.repository;

import ru.otus.basic.yampolskiy.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> add(User user);

    Optional<User> getById(String id);

    List<User> getAll();

    Optional<User> removeById(String id);

    Optional<User> update(User user);

    Optional<User> getByEmail(String email);
}
