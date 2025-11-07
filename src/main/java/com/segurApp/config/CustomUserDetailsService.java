package com.segurApp.config;

import com.segurApp.modelo.entidad.Administrador;
import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.repositorio.AdministradorRepositorio;
import com.segurApp.modelo.repositorio.ClienteRepositorio;
import java.util.Collections;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdministradorRepositorio adminRepo;
    private final ClienteRepositorio clienteRepo;

    public CustomUserDetailsService(AdministradorRepositorio adminRepo, ClienteRepositorio clienteRepo) {
        this.adminRepo = adminRepo;
        this.clienteRepo = clienteRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Administrador admin = adminRepo.findByEmail(email);
        if (admin != null) {
            return User.withUsername(admin.getEmail())
                    .password(admin.getPassword()) // sin codificar, se usa NoOp más adelante
                    .roles(admin.getRol()) // normalmente "ADMIN"
                    .build();
        }

        Cliente cliente = clienteRepo.findByEmail(email);
        if (cliente != null) {
            return User.withUsername(cliente.getEmail())
                    .password(cliente.getPassword()) // sin codificar
                    .roles(cliente.getRol()) // normalmente "USER"
                    .build();
        }

        throw new UsernameNotFoundException("No se encontró el usuario con email: " + email);
    }
}
