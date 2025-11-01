/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.segurApp.config;

import com.segurApp.modelo.servicio.ClienteServicio;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {/*
    private final ClienteServicio clienteServicio;
    
    public SeguridadConfig(ClienteServicio userService) {
        this.clienteServicio = userService;
    }
    
    @Bean
    public UserDetailsService userDetailsService() { //Validacion para ver si springboot si esta agarrando el txt
        return nombreUsuario -> clienteServicio.buscarPorUsuario(nombreUsuario)
            .map(u -> {
                // Verificación en consola
                System.out.println("Usuario encontrado: " + u.nombre +
                                   " | Rol: " + u.rol +
                                   " | Password: " + u.password);
                return User.withUsername(u.nombre)
                           .password(u.password)
                           .roles(u.rol.replace("ROLE_", "")) // quita prefijo ROLE_ y lo pasa a "" (osea nada)
                           .build();
            })
            .orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario)
            );
    }
 
    @Bean //autenticar usuario
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // solo para pruebas
        return auth;
    }
     
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Usa explícitamente tu AuthenticationProvider
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(authz -> authz
                // Solo login y estáticos públicos
                .requestMatchers("/login", "/css/**", "/js/**", "/registro/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form //importa el formulario del login del front
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout //cuando el usuario cierre secssion y pss lo lleva a la pagina del login
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }
 
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
 */   
}
