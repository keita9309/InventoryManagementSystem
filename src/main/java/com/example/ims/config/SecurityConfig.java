package com.example.ims.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**", "/images/**", "/fonts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // for h2-console
        http
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().disable();
        http
                .authorizeRequests()
                .mvcMatchers("/auth/**").permitAll()
                .mvcMatchers("/user/**").permitAll()
                .mvcMatchers("/validation/**").permitAll()
                .mvcMatchers("/users/successCreateUser").permitAll()
                .mvcMatchers("/users/creationForm").permitAll()
                .mvcMatchers("/users/list").hasAuthority("ADMIN")
                .mvcMatchers("/users/deleteForm").hasAuthority("ADMIN")
                //その他はログインしないとアクセス不可
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll();
        http
                // ここで、ログアウトが成功した時に遷移するパス（普通は"login"）を指定すると、
                // logout()した時に、ログイン画面に遷移させる
                .logout().logoutSuccessUrl("/login").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}


