package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Implementation of UserDetailsService. Contains one method to get UserDetails in DB. */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

   @Autowired UserRepository userRepository;

    /**
     * Locates the user based on its username.
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never null)
     * @throws UsernameNotFoundException – if the user could not be found or the user has no
     *     GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Not Found UserName : " + username));

        return user.map(UserDetailsImpl::new).get();
    }
}
