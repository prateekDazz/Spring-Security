package net.prateekdazz.springsecurityproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {
@Bean

    public SecurityFilterChain defaultSecurityChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->configurer.requestMatchers("/myAccount","/myBalance","/myCards","/home","/myLoans").authenticated().requestMatchers("/myNotices","/myContacts").permitAll())
                .formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());

        return http.build();
    }


//    @Bean
//    public InMemoryUserDetailsManager userDetaisService()
//    {

//        Approach 1---By Using Default PasswordEncoder
//        UserDetails admin = User.withDefaultPasswordEncoder().username("ble").password("p09").authorities("admin","employee").build();
//        UserDetails user = User.withDefaultPasswordEncoder().username("admin").password("p09").build();
//        return new InMemoryUserDetailsManager(admin,user);

//        Approach 2 ---by using password encoder bean
//    InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        UserDetails admin = User.withUsername("admin").password("p09").authorities("admin").build();
//        UserDetails user = User.withUsername("ble").password("p09").authorities("employee").build();
//        inMemoryUserDetailsManager.createUser(admin);
//        inMemoryUserDetailsManager.createUser(user);
//        return inMemoryUserDetailsManager;
//
//    }
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource)
    {
        return new JdbcUserDetailsManager(dataSource);
    }
//    @Bean
//public PasswordEncoder passwordEncoder()
//{
//    return NoOpPasswordEncoder.getInstance();
//}
}


