package ums.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import ums.model.User;

public interface UserService {
    List<User> findAll(PageRequest pageRequest);

    User save(User user);

    User findById(Long id);

    User findByUsername(String username);
}
