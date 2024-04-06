package net.prateekdazz.springsecurityproject.configuration;

import net.prateekdazz.springsecurityproject.entity.Customer;
import net.prateekdazz.springsecurityproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ApplicationAuthenticator implements AuthenticationProvider {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
       Optional <Customer> customer = customerRepository.findByEmail(username);
        if(customer.isPresent())
        {
           if(passwordEncoder.matches(pwd, customer.get().getPwd()))
           {
               List<GrantedAuthority>grantedAuthorityList = new ArrayList<>();
               grantedAuthorityList.add(new SimpleGrantedAuthority(customer.get().getRole()));
               return new UsernamePasswordAuthenticationToken(username,pwd,grantedAuthorityList);
           }
           else {
               throw new BadCredentialsException("Invalid Password/Credentials");
           }
        }
        else {
            throw new BadCredentialsException("User not Registered");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return(UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }


}
