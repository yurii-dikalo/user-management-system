package ums.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ums.dto.request.UserLoginDto;
import ums.exception.AuthenticationException;
import ums.model.User;
import ums.service.security.AuthenticationService;
import ums.service.security.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto)
        throws AuthenticationException {
        User user = authenticationService.login(userLoginDto.username(),
                userLoginDto.password());
        String token = jwtTokenProvider.createToken(
                user.getUsername(),
                user.getRoles()
                        .stream()
                        .map(r -> r.getRoleName().name())
                        .toList()
        );
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
