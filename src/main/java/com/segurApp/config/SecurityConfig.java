package com.segurApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // sin encriptar
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final RoleBasedSuccessHandler successHandler;

    public SecurityConfig(CustomUserDetailsService userDetailsService, RoleBasedSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // páginas públicas
                .requestMatchers("/", "/index", 
                                 "/clientes/registroUsuario",
                                 "/clientes/loginUsuarios",
                                 "/css/**", "/js/**", "/images/**","/error/**","/clientes/guardar").permitAll()
                // zonas privadas
                .requestMatchers("/administradores/**").hasRole("ADMIN")
                .requestMatchers("/clientes/**").hasRole("USER")
                // todo lo demás necesita login
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/index")
                .loginProcessingUrl("/perform_login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureUrl("/index?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/perform_logout")
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .exceptionHandling(exception -> exception
                .accessDeniedHandler(accessDeniedHandler())
            );
            
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // sin encriptación (solo para desarrollo)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl handler = new AccessDeniedHandlerImpl();
        handler.setErrorPage("/error/403");
        return handler;
    }
}
