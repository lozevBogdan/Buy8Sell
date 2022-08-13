package com.example.sellbuy.config;

import com.example.sellbuy.repository.UserRepository;
import com.example.sellbuy.service.impl.SellAndBuyDetailService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.

                        authorizeRequests().

                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().

                        antMatchers("/","/products/all", "/products/all/promotion","/products/info/**").permitAll().

                        antMatchers( "/users/login", "/users/register").anonymous().

                        anyRequest().
                authenticated().
                and().

                        formLogin().

                        loginPage("/users/login").

                        usernameParameter("email").

                        passwordParameter(UsernamePasswordAuthenticationFilter.
                        SPRING_SECURITY_FORM_PASSWORD_KEY).

                        defaultSuccessUrl("/",true).

                        failureForwardUrl("/users/login-error").
                and().

                        logout().

                        logoutUrl("/users/logout").

                        logoutSuccessUrl("/users/login").

                        invalidateHttpSession(true).
                deleteCookies("JSESSIONID");


        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new SellAndBuyDetailService(userRepository);
    }
}
