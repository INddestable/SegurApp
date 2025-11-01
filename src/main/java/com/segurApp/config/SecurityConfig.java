package com.segurApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            AuthenticationSuccessHandler successHandler) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                // recursos públicos
                .requestMatchers("/", "/index",
                                 "/clientes/loginUsuarios",
                                 "/clientes/registroUsuarios",
                                 "/administradores/loginAdministrador",
                                 "/css/**","/style.css", "/js/**", "/images/**", "/webjars/**").permitAll()
                // rutas por rol (asegúrate de que tus roles sean ROLE_CLIENTE y ROLE_ADMIN)
                .requestMatchers("/clientes/**").hasRole("CLIENTE")
                .requestMatchers("/administradores/**").hasRole("ADMIN")
                // resto requiere auth
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // GET -> mostrás el formulario
                .loginPage("/clientes/loginUsuarios")
                // POST -> url que procesa credenciales (configurada abajo)
                .loginProcessingUrl("/perform_login")
                // manejador para redirigir según rol
                .successHandler(successHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .permitAll()
            )
            // durante pruebas puedes desactivar CSRF; en producción no lo dejes así
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
