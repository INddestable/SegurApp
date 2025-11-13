
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.DetallePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePagoRepositorio extends JpaRepository<DetallePago, Integer> {
    
    @Modifying
    @Query("DELETE FROM DetallePago dp WHERE dp.pago.pago_id = :pagoId")
    void deleteByPagoId(@Param("pagoId") Integer pagoId);
}