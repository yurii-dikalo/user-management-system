package ums.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ums.dto.request.StatusRequestDto;
import ums.dto.request.UserRequestDto;
import ums.dto.response.UserResponseDto;
import ums.service.mapper.impl.UserMapper;
import ums.model.User;
import ums.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserResponseDto> findAll() {
        return userService.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @PostMapping("/new")
    public UserResponseDto save(@RequestBody @Valid UserRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);
        user.setStatus(User.Status.ACTIVE);
//        user.setCreatedAt(LocalDateTime.now());
        return userMapper.toDto(userService.save(user));
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable Long id) {
        return userMapper.toDto(userService.findById(id));
    }

    @PutMapping("/{id}/edit")
    public UserResponseDto update(@PathVariable Long id,
                                  @RequestBody @Valid UserRequestDto requestDto) {
        User existingUser = userService.findById(id);
        existingUser.setUsername(requestDto.username());
        existingUser.setPassword(requestDto.password());
        existingUser.setFirstName(requestDto.firstName());
        existingUser.setLastName(requestDto.lastName());
        return userMapper.toDto(userService.save(existingUser));
    }

    @PutMapping("/{id}/status")
    public UserResponseDto status(@PathVariable Long id,
                                  @RequestBody StatusRequestDto requestDto) {
        User user = userService.findById(id);
        User.Status status = User.Status.valueOf(requestDto.status().toUpperCase());
        user.setStatus(status);
        return userMapper.toDto(userService.save(user));
    }
}
