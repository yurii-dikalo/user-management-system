package ums.service;

import ums.model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User user);
    User findById(Long id);
}
