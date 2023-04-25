package ums.controller;

import java.util.List;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import ums.model.User;
import ums.service.UserService;
import ums.service.mapper.impl.UserMapper;
import ums.util.SortUtil;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SortUtil sortUtil;

    @Operation(description = "Get the list of all users sorted and with pagination.")
    @GetMapping
    public List<UserResponseDto> findAll(@RequestParam(defaultValue = "20")
                                         @Parameter(description =
                                                 "The number of users per page. Default value is 20.")
                                         Integer count,
                                         @RequestParam(defaultValue = "0")
                                         @Parameter(description =
                                                 "The page number to retrieve. Default value is 0.")
                                         Integer page,
                                         @RequestParam(defaultValue = "id")
                                         @Parameter(description =
                                                 "The field to sort by. Default value is id.")
                                         String sortBy) {
        Sort sort = sortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return userService.findAll(pageRequest)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Operation(description = "Create a new user.")
    @PostMapping("/new")
    public UserResponseDto save(@RequestBody
                                @Valid
                                @Parameter(description =
                                        "The user information to create.")
                                UserRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);
        user.setStatus(User.Status.ACTIVE);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userMapper.toDto(userService.save(user));
    }

    @Operation(description = "Get a user by ID.")
    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable
                                    @Parameter(description =
                                            "The ID of the user to retrieve.")
                                    Long id) {
        return userMapper.toDto(userService.findById(id));
    }

    @Operation(description = "Update an existing user.")
    @PutMapping("/{id}/edit")
    public UserResponseDto update(@PathVariable
                                  @Parameter(description =
                                          "The ID of the user to update.")
                                  Long id,
                                  @RequestBody
                                  @Valid
                                  @Parameter(description =
                                          "The updated information of the user to edit.")
                                  UserEditRequestDto requestDto) {
        User existingUser = userService.findById(id);
        existingUser.setFirstName(requestDto.firstName());
        existingUser.setLastName(requestDto.lastName());
        return userMapper.toDto(userService.save(existingUser));
    }

    @Operation(description = "Update a user's status.")
    @PutMapping("/{id}/status")
    public UserResponseDto status(@PathVariable
                                  @Parameter(description =
                                          "The ID of the user whose status should be updated.")
                                  Long id,
                                  @Parameter(description =
                                          "The status to update to.")
                                  @RequestBody StatusRequestDto requestDto) {
        User user = userService.findById(id);
        User.Status status = User.Status.valueOf(requestDto.status().toUpperCase());
        user.setStatus(status);
        return userMapper.toDto(userService.save(user));
    }
}
