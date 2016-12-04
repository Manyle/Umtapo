package org.lendi.umtapo.configuration.security;

import org.lendi.umtapo.enumeration.UserProfileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

 @Autowired
 @Qualifier("customUserDetailsService")
 UserDetailsService userDetailsService;

 @Autowired
 public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
  auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
 }

 @Override
 protected void configure(HttpSecurity http) throws Exception {
  http
	  .csrf().disable()
	  .authorizeRequests()
	  //		 .antMatchers("/**").permitAll() Disable security
	  .antMatchers("/consoleh2/**").hasRole(UserProfileType.ADMIN.getUserProfileType())
	  .anyRequest().authenticated()
	  .and()
	  .formLogin()
	  //		 .loginPage("/login")
	  .usernameParameter("username").passwordParameter("password");

  http.headers().frameOptions().disable();

 }

 @Autowired
 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  auth
	  .inMemoryAuthentication()
	  .withUser("user").password("password").roles(UserProfileType.USER.getUserProfileType());
  auth
	  .inMemoryAuthentication()
	  .withUser("admin").password("password").roles(UserProfileType.ADMIN.getUserProfileType());
 }
}