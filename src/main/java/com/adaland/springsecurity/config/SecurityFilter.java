package com.adaland.springsecurity.config;


import com.adaland.springsecurity.model.auth.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilter {


    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( authConfig -> {
                    authConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/auth/register").permitAll();
                    authConfig.requestMatchers("/error").permitAll();

                    authConfig.requestMatchers(HttpMethod.GET, "/products").hasAuthority(Permission.READ_ALL_PRODUCTS.name());
                    authConfig.requestMatchers(HttpMethod.GET, "/api/**").hasRole("CUSTOMER");
                    authConfig.requestMatchers(HttpMethod.POST, "/products").hasAuthority(Permission.SAVE_ONE_PRODUCT.name());

                    authConfig.anyRequest().authenticated();



                });

        return http.build();

    }
}
