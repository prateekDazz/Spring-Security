package net.prateekdazz.springsecurityproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableWebSecurity
public class SpringSecurityProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityProjectApplication.class, args);
	}

//	@Bean
//
//    public SecurityFilterChain defaultSecurityChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(configurer ->configurer.requestMatchers("/myAccount","/myBalance","/myCards","/home","/myLoans").authenticated().requestMatchers("/myNotices","/myContacts").permitAll())
//                .formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }
//	@Bean
//	public PasswordEncoder passwordEncoder()
//	{
//		return NoOpPasswordEncoder.getInstance();
//	}
}
