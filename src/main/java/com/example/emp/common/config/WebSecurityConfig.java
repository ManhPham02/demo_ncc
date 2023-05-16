package com.example.emp.common.config;

import com.example.emp.EmpProjectApplication;
import com.example.emp.common.jwt.JwtAuthenticationFilter;
import com.example.emp.service.impl.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    UserService userService;
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager Bean
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService) // Cung cáp userservice cho spring security
                .passwordEncoder(passwordEncoder());
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/api/time-keep/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/api/login", "/api/logout", "/api/logout/success").permitAll()
                .antMatchers("/api/time-keep/**").hasAuthority("ROLE_USER")
                .antMatchers("/swagger-ui.html").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated().and()
                .oauth2Login().defaultSuccessUrl("/oath2/login/success", true)
                .failureUrl("/oath2/login/failure");
//        http.oauth2Login().loginPage("/login/oauth2/code/google").defaultSuccessUrl("/oath2/login/success", true).failureUrl("/failure");

        http.exceptionHandling().accessDeniedPage("/api/access-denied");
        http.logout().logoutUrl("/api/logout").logoutSuccessUrl("/api/logout/success");


        // Thêm một lớp Filter kiểm tra jwt
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
