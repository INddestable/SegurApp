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
import com.segurApp.modelo.servicio.SeguroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    SeguroServicio seguroServicio;
    
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
    public String gestionClientes(@RequestParam (required = false) String nombre,
            @RequestParam (required = false) Integer documento, @RequestParam (required = false) String email, Model modelo){
        
        List<Cliente> listadoClientes;
        
        Cliente cliente = new Cliente();
        modelo.addAttribute(cliente);
        
        if((nombre == null || nombre.isEmpty()) && documento == null && (email == null || email.isEmpty())){
            listadoClientes = clienteServ.listarTodos();
        }else{
            listadoClientes = clienteServ.buscarCliente(nombre, documento, email);
        }
        
        
        modelo.addAttribute("clientes", listadoClientes);
        modelo.addAttribute("nombre", nombre);
        modelo.addAttribute("documento", documento);
        modelo.addAttribute("email", email);
        return "administradores/gestionClientes";
    }
    
    @PostMapping("/administradores/eliminarClientes")
    public String eliminarCliente(@RequestParam ("documento") Integer documento){
        clienteServ.eliminarCliente(documento);
        return "redirect:/administradores/gestionClientes";
    }
    
    @PostMapping("/administradores/eliminarSeguro/{id}")
    public String eliminarSeguro(@PathVariable Integer id) {
        try {
            seguroServicio.eliminarSeguro(id); // ✅ CAMBIADO: eliminarSeguroCompleto → eliminarSeguro
            return "redirect:/administradores/gestionSeguros?exito=Seguro+eliminado+correctamente";
        } catch (Exception e) {
            return "redirect:/administradores/gestionSeguros?error=Error+al+eliminar+seguro";
        }
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
        System.out.println("Entró al método guardarAdmin()");
        model.addAttribute("administrador", administrador);
        adminServ.guardarAdmin(administrador);
        return "redirect:/administradores/dashboard";
    }
    
    @GetMapping("/administradores/listar")
    public String listarAdmin(Model model){
        List<Administrador> listadoAdmin = adminServ.listarAdmin();
        model.addAttribute("administradores", listadoAdmin);
        return "administradores/listar";
    }
    
    
}
