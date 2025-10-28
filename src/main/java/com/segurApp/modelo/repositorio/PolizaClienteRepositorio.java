
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.PolizaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolizaClienteRepositorio extends JpaRepository<PolizaCliente, Integer> {
    
}
