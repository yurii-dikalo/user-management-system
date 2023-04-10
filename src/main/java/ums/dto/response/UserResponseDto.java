package ums.dto.response;

import ums.model.Role;
import ums.model.User;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponseDto(
        Long id,
        String username,
        String firstName,
        String lastName,
        Set<Role> roles,
        User.Status status,
        LocalDateTime createdAt
) {
}
