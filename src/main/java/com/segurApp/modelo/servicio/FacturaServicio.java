/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.entidad.Factura;
import com.segurApp.modelo.repositorio.FacturaRepositorio;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class FacturaServicio {
    @Autowired
    private FacturaRepositorio facturaRepo;

    public Factura crearFactura(Cliente cliente, Float total, String metodoPago, String descripcion) {
        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setFecha_emision(LocalDate.now().toString());
        factura.setTotal(total);
        factura.setMetodo_principal(metodoPago);
        factura.setDescripcion(descripcion);

        return facturaRepo.save(factura);
    }

    public Factura guardar(Factura factura) {
        return facturaRepo.save(factura);
    }
    
    public Factura buscarPorId(Integer id) {
        return facturaRepo.findById(id).orElse(null);
    }
    
    public void eliminar(Integer facturaId) {
        facturaRepo.deleteById(facturaId);
    }
}
