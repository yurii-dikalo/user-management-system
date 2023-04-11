package ums.service;

import ums.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User save(User user);
    User findById(Long id);
    Optional<User> findByUsername(String username);
}
