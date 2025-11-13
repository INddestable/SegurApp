package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.Pago;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago, Integer> {

    @Query("SELECT p FROM Pago p WHERE p.poliza.id_poliza = :idPoliza")
    List<Pago> findByPolizaId(@Param("idPoliza") Integer idPoliza);
}
