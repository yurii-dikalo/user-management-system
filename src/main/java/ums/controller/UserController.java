package ums.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ums.dto.request.StatusRequestDto;
import ums.dto.request.UserEditRequestDto;
import ums.dto.request.UserRequestDto;
import ums.dto.response.UserResponseDto;
import ums.service.mapper.impl.UserMapper;
import ums.model.User;
import ums.service.UserService;
import ums.util.SortUtil;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SortUtil sortUtil;

    @GetMapping
    public List<UserResponseDto> findAll(@RequestParam(defaultValue = "20")
                                             Integer count,
                                         @RequestParam(defaultValue = "0")
                                             Integer page,
                                         @RequestParam(defaultValue = "id")
                                             String sortBy) {
        Sort sort = sortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return userService.findAll(pageRequest)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @PostMapping("/new")
    public UserResponseDto save(@RequestBody @Valid UserRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);
        user.setStatus(User.Status.ACTIVE);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userMapper.toDto(userService.save(user));
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable Long id) {
        return userMapper.toDto(userService.findById(id));
    }

    @PutMapping("/{id}/edit")
    public UserResponseDto update(@PathVariable Long id,
                                  @RequestBody @Valid UserEditRequestDto requestDto) {
        User existingUser = userService.findById(id);
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
