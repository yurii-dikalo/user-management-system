package ums.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ums.model.Role;
import ums.repository.RoleRepository;
import ums.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findRoleByRoleName(Role.RoleName roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }
}
