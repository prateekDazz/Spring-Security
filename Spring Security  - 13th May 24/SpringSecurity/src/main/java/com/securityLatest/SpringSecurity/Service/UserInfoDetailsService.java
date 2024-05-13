package com.securityLatest.SpringSecurity.Service;

import com.securityLatest.SpringSecurity.config.UserInfoUserDetails;
import com.securityLatest.SpringSecurity.entity.UserInfo;
import com.securityLatest.SpringSecurity.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoDetailsService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo>userInfo =  userInfoRepository.findByName(username);
return userInfo.map(UserInfoUserDetails::new).orElseThrow(()->new IllegalArgumentException("User not found with the  username :"+username));
    }
}
