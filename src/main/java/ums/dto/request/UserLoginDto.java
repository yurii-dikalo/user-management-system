package ums.dto.request;

import javax.validation.constraints.NotBlank;

public record UserLoginDto(
        @NotBlank(message = "Username can't be null or blank!")
        String username,
        @NotBlank(message = "Password can't be null or blank!")
        String password
) {
}
