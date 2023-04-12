package ums.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ums.exception.AuthenticationException;
import ums.model.User;
import ums.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(String username, String password) throws AuthenticationException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new AuthenticationException("Invalid credentials.");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Invalid password.");
        }
        if (user.getStatus().equals(User.Status.INACTIVE)) {
            throw new AuthenticationException("Status inactive.");
        }
        return user;
    }
}
