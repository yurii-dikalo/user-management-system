package ums.validation.validator;

import lombok.RequiredArgsConstructor;
import ums.repository.UserRepository;
import ums.validation.UniqueUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final UserRepository userRepository;
    @Override
    public void initialize(UniqueUsername constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && userRepository.findByUsername(value).isEmpty();
    }
}
