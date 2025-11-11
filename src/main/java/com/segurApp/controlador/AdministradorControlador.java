/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.segurApp.controlador;

import com.segurApp.modelo.entidad.Administrador;
import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.servicio.AdministradorServicio;
import com.segurApp.modelo.servicio.ClienteServicio;
import com.segurApp.modelo.servicio.PolizaModeloServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author User
 */
@Controller
public class AdministradorControlador {
    
    @Autowired
    AdministradorServicio adminServ;
    
    @Autowired
    ClienteServicio clienteServ;
    
    @Autowired
    PolizaModeloServicio polizaModServ;
    
    @GetMapping("/administradores/prueba")
    public String pruebaCliente(){
        return "administradores/prueba";
    }
    
    /*
    @GetMapping("/administradores/loginAdministrador")
    public String loginAdmin() {
        return "administradores/loginAdministrador"; // templates/administradores/loginAdministrador.html
    }
    */
    
    @GetMapping("/administradores/dashboard")
    public String dashboardAdmi(){
        return "administradores/dashboard";
    }
    
    @GetMapping("/administradores/gestionClientes")
    public String gestionClientes(Model modelo){
        Cliente cliente = new Cliente();
        modelo.addAttribute(cliente);
        List<Cliente> listadoClientes = clienteServ.listarTodos();
        modelo.addAttribute("clientes", listadoClientes);
        return "administradores/gestionClientes";
    }
    
    @PostMapping("/administradores/eliminarClientes")
    public String eliminarCliente(@RequestParam ("documento") Integer documento){
        clienteServ.eliminarCliente(documento);
        return "redirect:/administradores/gestionClientes";
    }
    
    @PostMapping("/administradores/eliminarPolizas")
    public String eliminarPoliza(@RequestParam ("poliza_id") Integer polizaId){
        polizaModServ.eliminarPoliza(polizaId);
        return "redirect:/administradores/gestionPolizas";
    }
    
    @PostMapping("/administradores/editarCliente")
    public String editarCliente(@ModelAttribute Cliente cliente, Model modelo){
        modelo.addAttribute("cliente", cliente);
        clienteServ.actualizarCliente(cliente);
        return "redirect:/administradores/gestionClientes";
    }
    
    /*@GetMapping("/administradores/gestionPolizas")
    public String gestionPolizas(){
        return "administradores/gestionPolizas";
    }*/
    
    @GetMapping("/administradores/informes")
    public String informes(){
        return "administradores/informes";
    }
    
    @GetMapping("/administradores/registroAdministrador")
    public String registroAdministrador(Model model){
        Administrador administrador = new Administrador();
        model.addAttribute(administrador);
        return "administradores/registroAdministrador";
    }
    
    @PostMapping("/administradores/guardar")
    public String guardarAdmin(@ModelAttribute Administrador administrador, Model model) {
        System.out.println(">>> Entró al método guardarAdmin()");
        model.addAttribute("administrador", administrador);
        adminServ.guardarAdmin(administrador);
        return "redirect:/administradores/loginAdministrador";
    }
    
    @GetMapping("/administradores/listar")
    public String listarAdmin(Model model){
        List<Administrador> listadoAdmin = adminServ.listarAdmin();
        model.addAttribute("administradores", listadoAdmin);
        return "administradores/listar";
    }
    
    
}
