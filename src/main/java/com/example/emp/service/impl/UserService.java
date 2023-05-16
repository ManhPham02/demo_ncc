package com.example.emp.service.impl;


import com.example.emp.dao.entity.User;
import com.example.emp.dao.repository.RoleRepository;
import com.example.emp.dao.repository.UserRepository;
import com.example.emp.dto.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " User name not found");
        }

        List<GrantedAuthority> authorities = resultListGrantedAuthority(user.getUsername());
        return new CustomUserDetails(user, authorities);
    }

    public List<GrantedAuthority> resultListGrantedAuthority(String username) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roleRepository.findRoleNameByUser(username)) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    // JWTAuthenticationFilter sẽ sử dụng hàm này
    public UserDetails loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        List<GrantedAuthority> authorities = resultListGrantedAuthority(user.getUsername());
        return new CustomUserDetails(user, authorities);
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("\t>> Đối tượng UserService sau khi khởi tạo xong sẽ chạy hàm này");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("\t>> Đối tượng UserService trước khi bị destroy thì chạy hàm này");
    }
}
