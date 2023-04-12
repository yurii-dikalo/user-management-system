package ums.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record UserEditRequestDto(
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
