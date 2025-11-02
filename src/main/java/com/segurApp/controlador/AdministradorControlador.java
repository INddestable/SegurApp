/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.segurApp.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author User
 */
@Controller
public class AdministradorControlador {
    @GetMapping("/administradores/loginAdministrador")
    public String loginAdmin() {
        return "administradores/loginAdministrador"; // templates/administradores/loginAdministrador.html
    }
    
    @GetMapping("/administradores/dashboard")
    public String dashboardAdmi(){
        return "administradores/dashboard";
    }
    
    @GetMapping("/administradores/gestionClientes")
    public String gestionClientes(){
        return "administradores/gestionClientes";
    }
    
    @GetMapping("/administradores/gestionPolizas")
    public String gestionPolizas(){
        return "administradores/gestionPolizas";
    }
    
    @GetMapping("/administradores/informes")
    public String informes(){
        return "administradores/informes";
    }
    
    @GetMapping("/administradores/registroAdministrador")
    public String registroAdministrador(){
        return "administradores/registroAdministrador";
    }
    
    @GetMapping("/administradores/registroSeguros")
    public String registroSeguros(){
        return "administradores/registroSeguros";
    }
}
