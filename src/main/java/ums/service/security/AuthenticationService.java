package ums.service.security;

import ums.exception.AuthenticationException;
import ums.model.User;

public interface AuthenticationService {
    User login(String username, String password) throws AuthenticationException;
}
