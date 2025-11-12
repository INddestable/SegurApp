
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.PolizaModelo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PolizaModeloRepositorio extends JpaRepository<PolizaModelo, Integer> {
    
    @Query("SELECT s FROM PolizaModelo s WHERE " +
           "(:nombre_plan IS NULL OR s.nombre_plan LIKE %:nombre_plan%)")
    List<PolizaModelo> findByFiltros(
            
            @Param("nombre_plan") String nombre_plan
    );
    
}
