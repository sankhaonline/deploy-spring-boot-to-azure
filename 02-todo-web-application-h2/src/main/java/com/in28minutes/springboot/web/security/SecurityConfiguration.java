package com.in28minutes.springboot.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {
  @Bean
  public UserDetailsService users() {
    var user =
        User.builder()
            .username("user")
            .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
            .roles("USER")
            .build();
    var admin =
        User.builder()
            .username("admin")
            .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
            .roles("USER", "ADMIN")
            .build();
    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .requestMatchers(AntPathRequestMatcher.antMatcher("/login"))
        .permitAll()
        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
        .permitAll()
        .requestMatchers(AntPathRequestMatcher.antMatcher("/"))
        .access("hasRole('USER')")
        .requestMatchers(AntPathRequestMatcher.antMatcher("/*todo*/**"))
        .access("hasRole('USER')")
        .and()
        .formLogin(Customizer.withDefaults())
        .csrf(
            csrf ->
                csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
    return http.build();
  }
}
