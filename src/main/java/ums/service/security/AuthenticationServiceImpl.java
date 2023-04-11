package ums.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ums.exception.AuthenticationException;
import ums.model.User;
import ums.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User login(String username, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isEmpty()
                || !passwordEncoder.matches(password, userOptional.get().getPassword())
        ) {
            throw new AuthenticationException("Incorrect username or password.");
        }
        if (userOptional.get().getStatus().equals(User.Status.INACTIVE)) {
            throw new AuthenticationException("User with username " + username
                    + " is not active.");
        }
        return userOptional.get();
    }
}
