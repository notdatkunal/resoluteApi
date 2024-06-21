package com.resolute.zero.security;

import com.resolute.zero.repositories.EndpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .addFilterBefore(jwtAuthenticatioFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth->{
//                    String[] excludedUrlsArray =  JwtAuthenticatioFilter.EXCLUDED_URLS.toArray(new String[0]);
//                    auth.requestMatchers(excludedUrlsArray).permitAll();
//                    auth.requestMatchers(HttpMethod.GET).permitAll();
//                    auth.anyRequest().authenticated();
                    auth.requestMatchers("/admin/**","/case/**","/bank/**").authenticated();
                    auth.anyRequest().permitAll();

        })
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }


    @Bean
    public JwtAuthenticatioFilter jwtAuthenticatioFilter(){
        return new JwtAuthenticatioFilter();
    }

    @Autowired
    private EndpointRepository endpointRepository;

    @Bean
    public EndPointsMapping endPointsMapping(){
        var list = endpointRepository.findAll();
        return EndPointsMapping.builder()
                .endpointList(list)
                .build();
    }

}
