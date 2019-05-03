package com.lastminute.fderatedsso.javaoauth2server;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created by vvaudi on 26/05/17.
 */

@EnableWebSecurity
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class LoginConfig extends WebSecurityConfigurerAdapter {

    public static final String[] WHITE_LIST = new String[]{"/login", "/user-info", "/sign-key/public", "/oauth/authorize", "/oauth/confirm_access", "/webjars/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().disable()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .requestMatchers().antMatchers(WHITE_LIST)
                .and()
                .authorizeRequests().anyRequest().permitAll()
        .and().oauth2ResourceServer().jwt();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        UserDetails userDetails = User.withUsername("user")
                .password("secret")
                .roles("USER")
                .passwordEncoder(passwordEncoder::encode)
                .build();
        inMemoryUserDetailsManager.createUser(userDetails);
        return inMemoryUserDetailsManager;
    }

}