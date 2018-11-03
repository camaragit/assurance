package com.ajit.assuranceprojet.service;

import com.ajit.assuranceprojet.dao.UserRepository;
import com.ajit.assuranceprojet.model.MyUserDetails;
import com.ajit.assuranceprojet.model.Profil;
import com.ajit.assuranceprojet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLoginIgnoreCase(username);
        if(user==null){
            throw  new UsernameNotFoundException(String.format("Le compte %s n'exite pas",username));

        }
        else {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (Profil role : user.getRoles()){
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getNomRole()));
            }
            return new MyUserDetails(user.getId(),user.getLogin(),user.getPassword(),user,user.isActive());

        }
    }
}
