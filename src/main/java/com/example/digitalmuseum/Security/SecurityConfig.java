package com.example.digitalmuseum.Security;

import com.example.digitalmuseum.model.Security.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;

@CrossOrigin
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    @CrossOrigin
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/", "/signup", "/login", "/logout").permitAll();
        http.authorizeRequests().antMatchers("/userInfo").access("hasRole('" + AppRole.ROLE_USER + "')");
        http.authorizeRequests().antMatchers("/admin").access("hasRole('" + AppRole.ROLE_ADMIN + "')");
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        http.authorizeRequests().and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                //.successForwardUrl("/userInfo")
                .failureUrl("/login?error=true")
                //.permitAll();
                .usernameParameter("username")
                .passwordParameter("password");
        http.authorizeRequests().and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
        http.apply(new SpringSocialConfigurer()).signupUrl("/signup");
    }

    @Override
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}
