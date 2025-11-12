
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.entidad.PolizaCliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.entidad.PolizaCliente;
import com.segurApp.modelo.entidad.PolizaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PolizaClienteRepositorio extends JpaRepository<PolizaCliente, Integer> {

    @Query("SELECT p FROM PolizaCliente p WHERE p.cliente = :cliente")
    List<PolizaCliente> findByCliente(@Param("cliente") Cliente cliente);

    @Query("SELECT COUNT(p) > 0 FROM PolizaCliente p WHERE p.cliente = :cliente AND p.poliza_modelo = :modelo")
    boolean existsByClienteAndPoliza_modelo(@Param("cliente") Cliente cliente, @Param("modelo") PolizaModelo modelo);
}
