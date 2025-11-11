/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.entidad.PolizaCliente;
import com.segurApp.modelo.entidad.PolizaModelo;
import com.segurApp.modelo.entidad.Seguro;
import com.segurApp.modelo.repositorio.ClienteRepositorio;
import com.segurApp.modelo.repositorio.PolizaClienteRepositorio;
import com.segurApp.modelo.repositorio.PolizaModeloRepositorio;
import com.segurApp.modelo.repositorio.SeguroRepositorio;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class PolizaClienteServicio {
 private final PolizaClienteRepositorio repo;
 
 @Autowired
 private SeguroRepositorio seguroRepo;
 
 @Autowired 
 private ClienteRepositorio clienteRepo; 
 
 @Autowired
 private PolizaModeloRepositorio polizaModRepo;
 
 

    public PolizaClienteServicio(PolizaClienteRepositorio repo) {
        this.repo = repo;
    }

    public List<PolizaCliente> listarPorCliente(Cliente cliente) {
        return repo.findByCliente(cliente);
    }

    public void guardar(PolizaCliente poliza) {
        repo.save(poliza);
    }  
    
   /* public void crearPolizaCliente(Integer seguroId, Integer documento){
        Optional<Seguro> segurId = seguroRepo.findById(seguroId);
        Optional<Cliente> clienteId = clienteRepo.findById(documento);
        
        if(segurId.isPresent() && clienteId.isPresent()){
            Seguro seguro = segurId.get();
            Cliente cliente = clienteId.get();
            System.out.println("Objeto: " +  cliente.getDocumento()  + "/" + cliente.getEmail() + "/" + cliente.getNombre());
            
            List<PolizaModelo> polizaMod = seguro.getPolizas_Modelos();
            
            for(PolizaModelo modPoliza : polizaMod){
                PolizaCliente clientePoliza = new PolizaCliente();
                clientePoliza.setPoliza_modelo(modPoliza);
                clientePoliza.setCliente(cliente);
                clientePoliza.setFecha_inicio(LocalDate.now().toString());
                clientePoliza.setFecha_fin("2026/11/10");
                clientePoliza.setEstado("Activa");
                repo.save(clientePoliza);
                
            }
        }
    }*/
    
    public void crearPolizaCliente(Integer seguroId, Cliente cliente) {

    Optional<Seguro> seguroOpt = seguroRepo.findById(seguroId);

        Seguro seguro = seguroOpt.get();

        System.out.println("Cliente encontrado: " + cliente.getNombre() + " (" + cliente.getEmail() + ")");
        System.out.println("Seguro encontrado: " + seguro.getTipo());
        System.out.println("Polizas modelo encontradas: " + (seguro.getPolizas_Modelos() != null ? seguro.getPolizas_Modelos().size() : "null"));

        List<PolizaModelo> polizaMod = seguro.getPolizas_Modelos();

       
            for (PolizaModelo modPoliza : polizaMod) {
                PolizaCliente clientePoliza = new PolizaCliente();
                clientePoliza.setPoliza_modelo(modPoliza);
                System.out.println("Poliza." + modPoliza.getNombre_plan());
                clientePoliza.setCliente(cliente);
                System.out.println("cliente" + cliente.getDocumento());
                clientePoliza.setFecha_inicio(LocalDate.now().toString());
                clientePoliza.setFecha_fin("2026/11/10");
                clientePoliza.setEstado("Activa");
                repo.save(clientePoliza);
                System.out.println("💾 Póliza guardada para modelo " + modPoliza.getNombre_plan());
            }
        }
    } 


