package ums.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import ums.validation.UniqueUsername;

public record UserRequestDto(
        @NotBlank(message = "Username is mandatory")
        @Size(min = 3, max = 16,
                message = "Username length must be between 3 and 16")
        @Pattern(regexp = "^[a-zA-Z]+$",
                message = "Username must contain only latin characters")
        @UniqueUsername
        String username,
        @NotBlank(message = "Password is mandatory")
        @Size(min = 3, max = 16,
                message = "Password length must be between 3 and 16")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$",
                message = "Password must contain at least one letter and one digit")
        String password,
        @NotBlank(message = "First name is mandatory")
        @Size(min = 1, max = 16, message = "First name length must be between 1 and 16")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only latin characters")
        String firstName,
        @NotBlank(message = "Last name is mandatory")
        @Size(min = 1, max = 16, message = "Last name length must be between 1 and 16")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only latin characters")
        String lastName
) {
}
