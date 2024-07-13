package com.example.security.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.security.filter.JwtAuthTokenFilter;
import com.example.security.jwt.AuthEntryPoint;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;


@Configuration
@EnableMethodSecurity
//Enable MEthodSecurity annotation is used when we use @PreAuthorize annotations on methods to enable method level security
public class SecurityConfiguration {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private AuthEntryPoint unauthorizedHandler;
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/h2-console/**").permitAll().
				requestMatchers("/signIn/**").permitAll().
		anyRequest().authenticated()) .headers(headers ->
        headers.frameOptions(frameOptions ->
        frameOptions.sameOrigin()
    )
);;

        http.csrf().disable();
        http.exceptionHandling(e->e.authenticationEntryPoint(unauthorizedHandler));

		
//		ststeless is used to make the cookies tab inside networks section invisible. by default it is visible
		http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.formLogin(withDefaults());
//		form based login  is used with web based applications having proper front end
//		http.httpBasic(withDefaults());----used when 
		http.addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean 
	public JwtAuthTokenFilter jwtAuthTokenFilter()
	{
		return new JwtAuthTokenFilter();
	}
//	@Bean
//	public UserDetailsService userDetailsService()
//	{
////		UserDetails user1 =  User.withUsername("user1").password("{noop}p09").roles("user").build();
//		UserDetails user1 =  User.withUsername("user1").password(passwordEncoder().encode("p09")).roles("user").build();
////		UserDetails admin =  User.withUsername("admin").password("{noop}p09").roles("user","admin").build();
//		UserDetails admin =  User.withUsername("admin").password(passwordEncoder().encode("p09")).roles("user").build();
//
//		JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//		userDetailsManager.createUser(user1);
//		userDetailsManager.createUser(admin);
//		//		return new InMemoryUserDetailsManager(user1,admin);
//		return userDetailsManager;
//		
//	}
	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource)
	{
		return new JdbcUserDetailsManager(dataSource);
}
	
	@Bean
	
	public CommandLineRunner initData(UserDetailsService userDetailsService)
	{
		return args ->
		{
			JdbcUserDetailsManager manager = (JdbcUserDetailsManager)userDetailsService;
			UserDetails user1 =  User.withUsername("user1").password(passwordEncoder().encode("p09")).roles("USER").build();
			UserDetails admin =  User.withUsername("admin").password(passwordEncoder().encode("p09")).roles("ADMIN").build();
			JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
			userDetailsManager.createUser(user1);
			userDetailsManager.createUser(admin);
		};
	}
	
@Bean
public PasswordEncoder passwordEncoder()
{
	return new BCryptPasswordEncoder();
}


@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
}
}
