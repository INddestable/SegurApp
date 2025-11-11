
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.entidad.PolizaCliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolizaClienteRepositorio extends JpaRepository<PolizaCliente, Integer> {
    List<PolizaCliente> findByCliente(Cliente cliente);
}
