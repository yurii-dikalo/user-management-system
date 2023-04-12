package ums.dto.response;

import java.time.LocalDateTime;
import java.util.Set;
import ums.model.Role;
import ums.model.User;

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
