package ums.service.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import ums.model.BlacklistedToken;
import ums.repository.BlacklistedTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtTokenBlacklistLogoutHandler implements LogoutHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    public JwtTokenBlacklistLogoutHandler(JwtTokenProvider jwtTokenProvider, BlacklistedTokenRepository blacklistedTokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.blacklistedTokenRepository = blacklistedTokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && !blacklistedTokenRepository.existsByToken(token)) {
            blacklistedTokenRepository.save(new BlacklistedToken(token));
        }
    }
}