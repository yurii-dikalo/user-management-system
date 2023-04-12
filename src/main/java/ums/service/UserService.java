package ums.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ums.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll(PageRequest pageRequest);;
    User save(User user);
    User findById(Long id);
    User findByUsername(String username);
}
