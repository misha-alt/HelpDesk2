package misha.config;

import misha.service.CustomAuthenticationProvider;
import misha.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
@Autowired
    private UserDetailsServiceImpl userDetailsService;

   @Bean
   public PasswordEncoder passwordEncoder() {
       return NoOpPasswordEncoder.getInstance();
   }
 /*  @Bean
   public PasswordEncoder encoder() {
       return new BCryptPasswordEncoder();
   }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers("/test").permitAll()
                .antMatchers("/regForm").permitAll()
                .antMatchers("/regForm2").permitAll()
                .antMatchers("/manager").hasRole("MANAGER")
                .antMatchers("/emploeeContr").hasRole("USER")
                .antMatchers("/engineer").hasRole("ENGINEER")
                .antMatchers("/create_ticket").hasAnyRole("USER", "MANAGER")
                .antMatchers("/editForm/**").hasAnyRole("USER", "MANAGER", "ENGINEER")

                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/enterPage") // Set the success login page URL
                .and().logout()
                .logoutSuccessUrl("/test")
                .and()
                .csrf().disable();
        //https://www.youtube.com/watch?v=Mb8nlh4m0HM
    }


    /*@Bean
   public DaoAuthenticationProvider daoAuthenticationProvider(){
       DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
       daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
       daoAuthenticationProvider.setUserDetailsService(userDetailsService);
       return daoAuthenticationProvider;
   }*/



}





