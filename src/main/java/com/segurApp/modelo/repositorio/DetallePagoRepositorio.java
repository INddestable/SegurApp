
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.DetallePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePagoRepositorio extends JpaRepository<DetallePago, Integer>{
    
}
