
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguroRepositorio extends JpaRepository<Seguro, Integer>{
    
}
