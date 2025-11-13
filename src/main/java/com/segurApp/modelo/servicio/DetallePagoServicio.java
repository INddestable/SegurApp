/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.segurApp.modelo.servicio;

import com.segurApp.modelo.repositorio.DetallePagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class DetallePagoServicio {
    
    @Autowired
    private DetallePagoRepositorio detallePagoRepo;
    
    public void eliminarPorPago(Integer pagoId) {
        detallePagoRepo.deleteByPagoId(pagoId);
    }
}