package com.ted.auction_app.Configurations;

//import com.ted.auction_app.Utils.Constants;
import com.ted.auction_app.Security.Jwtauthfilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

// TODO IM WORKING ON WEB SECURITY CONFIGURATIONS -> CHECK UDEMY

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
//    private final UserDetailsService userDetailsService;                    // TODO Change UserDetailsService to UserService after you extend it first -> IF I USE UDEMYS APPROACH

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    public WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // The Jwt token authentication filter. This filter will intercept all the requests other than the “/token” uri.
    // The class is created to fetch the authentication token from the request, parse and validate the jwt token for further processing.
    @Bean
    public Jwtauthfilter jwtAuthenticationFilter() {
        return new Jwtauthfilter();
    }


    // Configure some of the service entry points in our application as public and other as protected
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //UDEMY
//        http.
//                csrf().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, Constants.SIGN_UP_URL)            // this specific post method/request
//                .permitAll()        // can be accessed by everyone
//                .anyRequest()               // any other request
//                .authenticated();           // will need to be authenticated

        // URLs matching for access rights
        http
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/users/username/{username}").permitAll()
                .antMatchers("/users/id/{id}").permitAll()
                .antMatchers("/users/all").permitAll()
                .antMatchers("/users/verify").permitAll()
                .antMatchers("/categories").permitAll()
                .antMatchers("/categories/enum").permitAll()
                .antMatchers("/items/update").permitAll()
                .antMatchers("/items").permitAll()
                .antMatchers("/items/getAll").permitAll()
                .antMatchers("/items/seller/{sellerId}").permitAll()
                .antMatchers("/items/category/{categoryId}").permitAll()
                .antMatchers("/items/{itemId}/images").permitAll()
                .antMatchers("/items/images/{imageId}").permitAll()
                .antMatchers("/items/{itemId}").permitAll()
                .antMatchers("/items/toXML/{itemId}").permitAll()
                .antMatchers("/items/toJSON/{itemId}").permitAll()
                .antMatchers("/bids").permitAll()
                .antMatchers("/items/{itemId}/start").permitAll()
                .antMatchers("/bids/{itemId}").permitAll()
                .antMatchers("/items/bids/by/{bidderId}").permitAll()
                .antMatchers("/items/bids/won/by/{bidderId}").permitAll()
                .antMatchers("/messages").permitAll()
                .antMatchers("/messages/inbox/{userId}").permitAll()
                .antMatchers("/messages/outbox/{userId}").permitAll()
                .antMatchers("/messages/{messageId}/seen").permitAll()
                .antMatchers("/messages/{messageId}/delete").permitAll()
                .antMatchers("/messages/unseenNum/{userId}").permitAll()
                .antMatchers("/ratings/seller/{sellerId}/from/{fromUser}").permitAll()
                .antMatchers("/ratings/buyer/{buyerId}/from/{fromUser}").permitAll()
                .antMatchers("/ratings/exist/for/{forUser}/from/{fromUser}").permitAll()
                .antMatchers("/items/search").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/", "home").permitAll()
//                .antMatchers("/authenticate").permitAll()
//                .antMatchers("/register").permitAll()
//                .antMatchers("/users").permitAll()
////                .antMatchers("/users/").hasAnyAuthority("ADMIN", "SITE_USER")
//                .anyRequest()
//                .authenticated().addFilterBefore(jwtAuthenticationFilter(),
//                UsernamePasswordAuthenticationFilter.class);
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //UDEMY
//        auth.
//                userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .passwordEncoder(bCryptPasswordEncoder);
    }
}

//TODO using jdbc authentication
