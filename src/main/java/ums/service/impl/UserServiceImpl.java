package ums.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ums.model.Role;
import ums.model.User;
import ums.repository.RoleRepository;
import ums.repository.UserRepository;
import ums.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<User> findAll(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest).toList();
    }

    @Override
    public User save(User user) {
        if (user.getRoles() == null) {
            user.setRoles(Set.of(roleRepository.findRoleByRoleName(Role.RoleName.USER)));
        }
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("There is no user with id: " + id)
        );
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("There is no user with username: " + username)
        );
    }
}
