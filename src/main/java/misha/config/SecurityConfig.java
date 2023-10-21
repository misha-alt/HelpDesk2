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
/*@Transactional*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
@Autowired
    private UserDetailsServiceImpl userDetailsService;
  /*  @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;*/

    /*@Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }*/

  /* @Autowired
    private PasswordEncoder passwordEncoder;*/
   @Bean
   public PasswordEncoder passwordEncoder() {
       return NoOpPasswordEncoder.getInstance();
   }
  /* @Bean
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

   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider)
                .userDetailsService(userDetailsService);
    }*/
    @Bean
   public DaoAuthenticationProvider daoAuthenticationProvider(){
       DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
       daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
       daoAuthenticationProvider.setUserDetailsService(userDetailsService);
       return daoAuthenticationProvider;
   }


   /* @Bean
    public SimpleUrlAuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/manager");
        return handler;
    }*/
}





/* @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/


// .antMatchers("/emploeeContr").hasAnyRole("USER","ENGINEER","MANAGER")
//.antMatchers("/engineer").hasAnyRole("ENGINEER","MANAGER")
//.antMatchers( "/manager").hasRole("MANAGER")

/*private AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                response.sendRedirect("/manager");
            } else {
                response.sendRedirect("/engineer");
            }
        };
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService());
        provider.setAuthenticationManager(myAuthenticationManager);
        return provider;
    }
    @Bean
    public MyAuthenticationManager authenticationManager() {
        return new MyAuthenticationManager();
    }*/





               /* .antMatchers("/makeTest").permitAll()
                .antMatchers("/testContr").permitAll()
                .anyRequest().authenticated()
                *//*  .antMatchers("/emploeeContr").hasAnyRole("USER","ENGINEER","MANAGER")
                  .antMatchers("/engineer").hasAnyRole("ENGINEER","MANAGER")
                  .antMatchers("/manager").hasRole("MANAGER")

  *//*
                .and().formLogin()

                .loginPage("/login")
                .successHandler(myAuthenticationSuccessHandler())
                //.permitAll()
                .passwordParameter(passwordEncoder.encode("password"))
                //.usernameParameter("login")

                .and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/makeTest")
                .and()
                .csrf().disable();

        //https://www.youtube.com/watch?v=Mb8nlh4m0HM
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                response.sendRedirect("/manager");
            } else {
                response.sendRedirect("/engineer");
            }
        };
    }

}
          */

/* http.authorizeRequests()
               // .antMatchers("makeTest").permitAll()
                .antMatchers("/manager/**").hasRole("MANAGER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/manager")
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}

*/