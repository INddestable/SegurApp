/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.segurApp.controlador;

import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.entidad.PolizaCliente;
import com.segurApp.modelo.servicio.ClienteServicio;
import com.segurApp.modelo.servicio.PolizaClienteServicio;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author User
 */
@Controller
public class PolizaClienteControlador {
    
    private final PolizaClienteServicio polizaClienteServicio;
    private final ClienteServicio clienteServicio;

    public PolizaClienteControlador(PolizaClienteServicio polizaClienteServicio, ClienteServicio clienteServicio) {
        this.polizaClienteServicio = polizaClienteServicio;
        this.clienteServicio = clienteServicio;
    }

    @GetMapping("/misPolizas")
    public String mostrarMisPolizas(@RequestParam("documento") Integer documento, Model model) {
        Cliente cliente = clienteServicio.buscarPorDocumento(documento);
        if (cliente == null) {
            return "redirect:/error";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("polizas", polizaClienteServicio.listarPorCliente(cliente));
        return "clientes/polizasUsuarios";
    }
    
    @GetMapping("/clientes/polizasUsuarios")
    public String polizasUsuarios(
        @RequestParam(required = false) String estado, Model modelo){
        Cliente cliente = clienteServicio.obtenerClienteActual(); 


        List<PolizaCliente> polizasCliente = polizaClienteServicio.listarPorCliente(cliente);

       
        modelo.addAttribute("cliente", cliente);
        modelo.addAttribute("polizas", polizasCliente);
        
        if ((estado == null || estado.isEmpty())) {
            polizasCliente = polizaClienteServicio.listarPorCliente(cliente);
        } else {
            // Aplicar filtros personalizados
            polizasCliente = polizaClienteServicio.buscarPolizas(estado);
        }
        
        modelo.addAttribute("estado", estado);
        return "clientes/polizasUsuarios";
    }
    
    
    /*@GetMapping("/clientes/misPolizas")
    public String mostrarMisPolizas(Model model) {
        
        Cliente cliente = clienteServicio.obtenerClienteActual(); 


        List<PolizaCliente> polizasCliente = polizaClienteServicio.listarPorCliente(cliente);

       
        model.addAttribute("cliente", cliente);
        model.addAttribute("polizas", polizasCliente);

     
        return "clientes/polizasUsuarios";
}*/
    
    /*@PostMapping("/clientes/compraSeguros/{seguroId}")
    public String comprarSeguro(@PathVariable Integer seguroId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); 

        
        Cliente cliente = clienteServicio.busacarPorCorreo(username); 
        System.out.println("Documento: " + cliente.getDocumento());
        polizaClienteServicio.crearPolizaCliente(seguroId, cliente.getDocumento());

        return "redirect:/clientes/compraSeguros";
    }*/
   /* @PostMapping("/clientes/compraSeguros/{seguroId}")
    public String comprarSeguro(@PathVariable Integer seguroId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();  // 🔹 el username autenticado es el correo

        // Buscar el cliente por su correo
        Cliente cliente = clienteServicio.busacarPorCorreo(email);

        if (cliente == null) {
            System.out.println("⚠️ No se encontró cliente con correo: " + email);
            return "redirect:/clientes/loginUsuarios"; // Redirige si no está registrado
        }

        System.out.println("Cliente autenticado -> Documento: " + cliente.getDocumento() + ", Email: " + cliente.getEmail());

        // Crear la póliza cliente asociada al documento del cliente
        polizaClienteServicio.crearPolizaCliente(seguroId, cliente);

        return "redirect:/clientes/compraSeguros";
    }*/
}
    
