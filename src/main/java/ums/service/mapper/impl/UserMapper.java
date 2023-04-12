package ums.service.mapper.impl;

import org.springframework.stereotype.Component;
import ums.dto.request.UserRequestDto;
import ums.dto.response.UserResponseDto;
import ums.model.User;
import ums.service.mapper.DtoRequestMapper;
import ums.service.mapper.DtoResponseMapper;

@Component
public class UserMapper implements DtoRequestMapper<UserRequestDto, User>,
        DtoResponseMapper<UserResponseDto, User> {
    @Override
    public User toModel(UserRequestDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        return user;
    }

    @Override
    public UserResponseDto toDto(User model) {
        return new UserResponseDto(
                model.getId(),
                model.getUsername(),
                model.getFirstName(),
                model.getLastName(),
                model.getRoles(),
                model.getStatus(),
                model.getCreatedAt()
        );
    }
}
