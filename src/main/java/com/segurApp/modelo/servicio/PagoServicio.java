/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.Factura;
import com.segurApp.modelo.entidad.Pago;
import com.segurApp.modelo.entidad.PolizaCliente;
import com.segurApp.modelo.repositorio.PagoRepositorio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class PagoServicio {
    @Autowired
    private PagoRepositorio pagoRepo;

    public Pago registrarPago(PolizaCliente poliza, Float total, Factura factura) {
        Pago pago = new Pago();
        pago.setPoliza(poliza);
        pago.setFecha_pago(LocalDate.now().toString());
        pago.setTotal(total);
        pago.setFactura(factura);
        pago.setDetalles_pago(new ArrayList<>()); // si tienes detalles opcionales

        return pagoRepo.save(pago);
    }

    public List<Pago> listarPagos() {
        return pagoRepo.findAll();
    }
    
}
