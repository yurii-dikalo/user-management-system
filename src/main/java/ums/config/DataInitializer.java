package ums.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ums.model.Role;
import ums.model.User;
import ums.service.RoleService;
import ums.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(Role.RoleName.ADMIN);
        roleService.save(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(Role.RoleName.USER);
        roleService.save(userRole);
        User admin = new User();
        admin.setUsername("admin");
        String encodedPassword = passwordEncoder.encode("a1234");
        admin.setPassword(encodedPassword);
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setRoles(Set.of(adminRole));
        admin.setStatus(User.Status.ACTIVE);
        userService.save(admin);
    }
}
