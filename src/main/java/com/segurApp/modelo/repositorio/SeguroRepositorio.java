
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.Seguro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguroRepositorio extends JpaRepository<Seguro, Integer>{
    
    @Query("SELECT s FROM Seguro s WHERE " +
           "(:tipo IS NULL OR s.tipo LIKE %:tipo%) AND " +
           "(:cobertura IS NULL OR s.cobertura LIKE %:cobertura%) AND " +
           "(:costo IS NULL OR s.costo = :costo) AND " +
           "(:duracion IS NULL OR s.duracion LIKE %:duracion%) AND " +
           "(:aseguradora IS NULL OR s.aseguradora LIKE %:aseguradora%)")
    List<Seguro> findByFiltros(
            @Param("tipo") String tipo,
            @Param("cobertura") String cobertura,
            @Param("costo") Double costo,
            @Param("duracion") String duracion,
            @Param("aseguradora") String aseguradora
    );
    
}
