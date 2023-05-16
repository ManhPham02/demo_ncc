package com.example.emp.service.impl;

import com.example.emp.common.jwt.JwtTokenProvider;
import com.example.emp.common.utils.CustomException;
import com.example.emp.dto.CustomUserDetails;
import com.example.emp.dto.payload.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public String login(LoginRequest loginRequest) {
        try {
            // Xác thực thông tin người dùng Request lên
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

//            SecurityContextHolder.getContext().setAuthentication(authentication);


            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());

            return jwt;
        } catch (Exception e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.NOT_FOUND);
//            return "Username or password is incorrect";
        }
    }

    public String loginGG(HttpServletRequest request) {
        DefaultOidcUser user = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println(user.getAccessTokenHash());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UserDetails userDetail = new User(user.getEmail(),
                "", true, true, true, true, authorities);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateTokenGG(userDetail);

        return jwt;
    }

    public String logout() {
        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(null);
        return "Logout success";
    }
}
