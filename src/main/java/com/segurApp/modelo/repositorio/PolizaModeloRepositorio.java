
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.PolizaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolizaModeloRepositorio extends JpaRepository<PolizaModelo, Integer> {
   
}
