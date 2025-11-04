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
                .requestMatchers("/",
                                 "/index",
                                 "/index.html",
                                 "/style.css",
                                 "/clientes/loginUsuarios",
                                 "/clientes/registroUsuario", // primero esto
                                 "/clientes/ayuda",
                                 "/clientes/listar", //cambiar a administradores
                                 "clientes/guardar", //revisar
                                 "/clientes/dashboard",
                                 "/clientes/compraSeguros",
                                 "/clientes/contacto",
                                 "/clientes/pagosUsuarios",
                                 "/clientes/polizasUsuarios",
                                 "/administradores/loginAdministrador",
                                 "/administradores/dashboard",
                                 "/administradores/gestionClientes",
                                 "/administradores/gestionPolizas",
                                 "/administradores/informes",
                                 "/administradores/registroAdministrador",
                                 "/administradores/registroSeguros",
                                 "/images/**",
                                 "/webjars/**",
                                 "/css/**",//Importa todo lo de la carpeta static/css
                                 "/administrador/**",
                                 "/cliente/**",
                                 "/js/**" //Importa todo lo de la carpeta static/js
                ).permitAll()

                // luego las rutas protegidas
                .requestMatchers("/clientes/**").hasRole("USER")
                .requestMatchers("/administradores/**").hasRole("ADMIN")

                // resto requiere auth
                .anyRequest().authenticated()
            )

                
            .formLogin(form -> form
                .loginPage("/clientes/loginUsuarios")
                .loginProcessingUrl("/perform_login")
                .successHandler(successHandler)
                .permitAll()
            )
                
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
