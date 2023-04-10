package ums.service;

import ums.model.Role;

public interface RoleService {
    Role save(Role role);

    Role findRoleByRoleName(Role.RoleName name);
}
