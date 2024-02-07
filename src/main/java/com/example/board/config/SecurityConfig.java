package com.example.board.config;

import com.example.board.config.auth.PrincipalDetailService;
import com.example.board.handler.CustomAuthFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PrincipalDetailService principalDetailService;
  private final CustomAuthFailureHandler customAuthFailureHandler;

  private static final String[] PERMIT_URL_ARRAY = {
    "/",
    "/auth/**",
    "/js/**",
    "/css/**",
    "/image/**",
    "/dummy/**",
    "/h2-console/**",
      // swagger v2
    "/configuration/ui",
    "/configuration/security",
    "/webjars/**",
    "/swagger-ui.html",
    "/swagger-resources",
    "/swagger-resources/**",
    "/v2/api-docs",

    // swagger v3
    "/swagger/ui/**",
    "/v3/api-docs/**",
  };

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(principalDetailService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers().frameOptions().sameOrigin();
    http.csrf().disable()
      .authorizeRequests()
      .antMatchers(PERMIT_URL_ARRAY).permitAll()
      .anyRequest().authenticated()
      .and()
      .formLogin()
      .loginPage("/auth/loginForm")
      .loginProcessingUrl("/auth/loginProc")
      .failureHandler(customAuthFailureHandler)
      .defaultSuccessUrl("/")
      .and()
      .logout()
      .and()
      .userDetailsService(principalDetailService);
  }
}
