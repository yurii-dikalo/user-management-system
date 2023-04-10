package ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ums.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findRoleByRoleName(Role.RoleName roleName);
}
