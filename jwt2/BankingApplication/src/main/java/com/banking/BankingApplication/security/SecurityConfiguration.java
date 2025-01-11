package com.banking.BankingApplication.security;

import com.banking.BankingApplication.filter.JwtTokenFilter;
import com.banking.BankingApplication.jwt.AuthEntryPoint;
import com.banking.BankingApplication.service.UserInfoDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration {

    private AuthEntryPoint authEntryPoint;
    private UserInfoDetailsService userInfoDetailsService;
    @Bean
    public UserDetailsService userDetailsService() {


//        configuration without using database(in memory)
//        UserDetails admin = User.withUsername("thomas").password(encoder.encode("p09")).roles("ADMIN").build();
//        UserDetails user = User.withUsername("john").password(encoder.encode("p09")).roles("USER").build();
//return new InMemoryUserDetailsManager(admin,user);
        return new UserInfoDetailsService();

    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain (HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/bank/**").permitAll().requestMatchers("/consumePublicApi").permitAll().requestMatchers("/bank/login/**").permitAll().
                anyRequest().authenticated()) .headers(headers ->
                headers.frameOptions(frameOptions ->
                        frameOptions.sameOrigin()
                )
        );;

        http.csrf(csrf -> csrf.disable());
        http.exceptionHandling(e->e.authenticationEntryPoint(authEntryPoint));


//		ststeless is used to make the cookies tab inside networks section invisible. by default it is visible
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.formLogin(withDefaults());
//		form based login  is used with web based applications having proper front end
//		http.httpBasic(withDefaults());----used when
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider());
        return http.build();


    }
    
    @Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		System.out.println("authentication manager bean create ho rha h");
	    return config.getAuthenticationManager();
	}
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    JwtTokenFilter jwtTokenFilter()
    {
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
