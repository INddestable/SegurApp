
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepositorio extends JpaRepository<Administrador, Integer>{
    Administrador findByEmail(String email);
}
