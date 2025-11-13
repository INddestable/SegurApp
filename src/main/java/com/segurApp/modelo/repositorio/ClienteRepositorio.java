
package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
    Cliente findByEmail(String email);

    @Query("SELECT s FROM Cliente s WHERE " +
           "(:nombre IS NULL OR s.nombre LIKE %:nombre%) AND " +
           "(:documento IS NULL OR s.documento = :documento) AND " +
           "(:email IS NULL OR s.email LIKE %:email%) ")
    List<Cliente> findByFiltros(
            @Param("nombre") String nombre,
            @Param("documento") Integer documento,
            @Param("email") String email
    );
}
