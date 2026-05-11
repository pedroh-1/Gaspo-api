package com.gaspo.api.config;

import com.gaspo.api.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/pacientes/cadastro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/pacientes/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/profissionais/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/unidades-saude/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/avisos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/avisos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/avaliacoes/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/avaliacoes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/avaliacoes/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/avaliacoes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/pacientes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/consultas/meu-historico").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/consultas/ativos").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/consultas/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/web/**").permitAll() // Liberar rotas da pagina web/thymeleaf
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
