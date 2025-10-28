package com.segurApp.modelo.repositorio;

import com.segurApp.modelo.entidad.Pago;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PagoRepositorio extends JpaRepository<Pago, Integer>{
    
}
