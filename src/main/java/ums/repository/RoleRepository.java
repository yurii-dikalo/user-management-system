package ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ums.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findRoleByRoleName(Role.RoleName roleName);
}
