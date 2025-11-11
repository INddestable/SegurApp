
package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.Administrador;
import com.segurApp.modelo.repositorio.AdministradorRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdministradorServicio {
    
    @Autowired
    private AdministradorRepositorio adminRepo;
    
    public void guardarAdmin(Administrador admin){
        adminRepo.save(admin);
    }
    
    public List<Administrador> listarAdmin(){
        return adminRepo.findAll();
    }
    
    public Administrador buscarPorId(Integer id){
        return adminRepo.findById(id).orElse(null);
    }
}
