package com.nnk.springboot.securityconfig;


import com.nnk.springboot.services.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests()
                .antMatchers("/bidList/**", "/admin/*", "/trade/**", "/ruleName/**", "/rating/**", "/curvePoint/**", "/user/update/**", "/user/delete/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/", "/login", "/user/list").permitAll()
                .and().formLogin()  //login configuration
                .defaultSuccessUrl("/bidList/list")
                .and().logout()    //logout configuration
                .logoutUrl("/app-logout")
                .logoutSuccessUrl("/")
                .and().exceptionHandling() //exception handling configuration
                .accessDeniedPage("/403");
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1);

        // autorisation des rôles USER et ADMIN
    }

    // TODO : collection/List de role ?
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<Role> roles = user.getRole();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(user.getRole()));
//        }
//
//        return authorities;
//    }

    // TODO : hasRole ?
    // @Override
    //public void configure(HttpSecurity http) throws Exception {
    //        http
    //            .authorizeRequests()
    //            .antMatchers("/admin").hasRole("ADMIN")
    //            .antMatchers("/user").hasRole("USER")
    //            .anyRequest().authenticated()
    //            .and()
    //            .formLogin();
    //}

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }

}