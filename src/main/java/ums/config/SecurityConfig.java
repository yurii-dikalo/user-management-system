package ums.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import ums.model.Role;
import ums.repository.BlacklistedTokenRepository;
import ums.service.security.jwt.JwtConfigurer;
import ums.service.security.jwt.JwtTokenBlacklistLogoutHandler;
import ums.service.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = Role.RoleName.ADMIN.name();
    private static final String USER = Role.RoleName.USER.name();
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/new").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/users/**").hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.PUT, "/users/{id}/edit").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, "/users/{id}/status").hasRole(ADMIN)
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(new JwtTokenBlacklistLogoutHandler(jwtTokenProvider,
                        blacklistedTokenRepository))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .cors().and()
                .headers().frameOptions().disable();
    }
}
