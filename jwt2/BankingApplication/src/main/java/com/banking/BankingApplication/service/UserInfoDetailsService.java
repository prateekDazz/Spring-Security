package com.banking.BankingApplication.service;
import com.banking.BankingApplication.model.BankUser;
import com.banking.BankingApplication.repository.BankUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserInfoDetailsService implements UserDetailsService {

    @Autowired
    private BankUserRepository bankUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        Optional<BankUser> bankUser = bankUserRepository.findByfirstName(username);

        if(bankUser.isPresent())
        {
            if(bankUser.get().getRole()!=null && bankUser.get().getRole().equalsIgnoreCase("ADMIN"))
            {
                return new User(username, bankUser.get().getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
            }
            else if (bankUser.get().getRole()!=null && bankUser.get().getRole().equalsIgnoreCase("CITIZEN"))
            {
                return new User(username, bankUser.get().getPassword(), List.of(new SimpleGrantedAuthority("ROLE_CITIZEN")));

            }
            return new User(username, bankUser.get().getPassword(),new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not found");

    }

}
