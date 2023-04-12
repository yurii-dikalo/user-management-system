package ums.service.security;

import static org.springframework.security.core.userdetails.User.withUsername;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ums.model.User;
import ums.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        UserBuilder userBuilder;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getStatus() != User.Status.ACTIVE) {
                throw new UsernameNotFoundException("Active user not found with username: "
                        + username);
            }
            userBuilder = withUsername(username);
            userBuilder.password(user.getPassword());
            userBuilder.roles(user.getRoles()
                    .stream()
                    .map(role -> role.getRoleName().name())
                    .toArray(String[]::new));
            return userBuilder.build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
