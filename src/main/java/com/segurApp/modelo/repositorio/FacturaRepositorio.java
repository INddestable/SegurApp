
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepositorio extends JpaRepository<Factura, Integer>{
    
}
