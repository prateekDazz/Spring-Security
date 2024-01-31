package com.springBootSecurity.security.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
//	@Bean
//	public InMemoryUserDetailsManager userDetailsmanager()
//	{
//		UserDetails thomas =User.builder().username("thomas").password("{noop}p09").roles("EMPLOYEE").build();
//		
//		UserDetails kathy =User.builder().username("kathy").password("{noop}p09").roles("MANAGER","EMPLOYEE").build();
//		UserDetails manish =User.builder().username("manish").password("{noop}p09").roles("EMPLOYEE","LEAD").build();
//
//		return new InMemoryUserDetailsManager(thomas,kathy,manish);
//
//		
//		
//		
//	}
	@Bean 
	public UserDetailsManager userDetailsManager(DataSource dataSource)
	{
		return new JdbcUserDetailsManager(dataSource);
	}
	@Bean
	
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception
	{
		http.authorizeHttpRequests(configurer ->configurer.requestMatchers("/home").hasRole("EMPLOYEE").requestMatchers("/manager").hasRole("MANAGER").requestMatchers("/lead").hasRole("LEAD").anyRequest().authenticated()).
		formLogin(form ->form.loginPage("/showMyLoginPage").loginProcessingUrl("/authenticateTheUser").permitAll()).logout(logout ->logout.permitAll());
		
		return http.build();
	}
	
	

}
