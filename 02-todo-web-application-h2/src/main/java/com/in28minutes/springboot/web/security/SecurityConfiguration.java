package com.in28minutes.springboot.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {
  // Create User - in28Minutes/dummy
  @Autowired
  public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("in28minutes")
        .password("{noop}dummy")
        .roles("USER", "ADMIN");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    /*http.authorizeHttpRequests((authorize) -> authorize
            *//*.requestMatchers(AntPathRequestMatcher.antMatcher("/**"))
            .hasRole("USER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**"))
            .hasRole("ADMIN")*//*
            *//*.requestMatchers(PathRequest.toH2Console())
            .permitAll()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))*//*
            .anyRequest()
            .permitAll());

    return http.build();*/

    http.authorizeHttpRequests((authz) -> authz
            .requestMatchers(
                    new AntPathRequestMatcher("/actuator/**")
            ).permitAll()
            .requestMatchers(
                    new AntPathRequestMatcher("/h2-console/**")
            ).permitAll()
            .anyRequest().authenticated()
    );
    http.csrf((csrf) ->
            csrf.ignoringRequestMatchers(
                    new AntPathRequestMatcher("/h2-console/**")
            ).csrfTokenRepository(
                    CookieCsrfTokenRepository.withHttpOnlyFalse()
            )
    );
    http.headers((headers) -> headers
            .frameOptions(
                    HeadersConfigurer.FrameOptionsConfig::disable
            )
    );
    return http.build();
  }
}
