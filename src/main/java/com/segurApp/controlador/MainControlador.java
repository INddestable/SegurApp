
package com.segurApp.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControlador {

    @GetMapping({"/", "/index"})
    public String index() {
        return "index"; // templates/index.html
    }

    @GetMapping("/clientes/loginUsuarios")
    public String loginCliente() {
        return "clientes/loginUsuarios"; // templates/clientes/loginUsuarios.html
    }

    @GetMapping("/clientes/registroUsuario")
    public String registroCliente() {
        return "clientes/registroUsuario"; // templates/clientes/registroUsuario.html
    }

    @GetMapping("/administradores/loginAdministrador")
    public String loginAdmin() {
        return "administradores/loginAdministrador"; // templates/administradores/loginAdministrador.html
    }
    
    @GetMapping("/clientes/ayuda")
    public String ayuda(){
        return "clientes/ayuda";
    }
    
    @GetMapping("/clientes/dashboard")
    public String dashboardClientes(){
        return "clientes/dashboard";
    }
    
    @GetMapping("/clientes/compraSeguros")
    public String compraSeguros(){
        return "clientes/compraSeguros";
    }
    
    @GetMapping("/clientes/contacto")
    public String contacto(){
        return "clientes/contacto";
    }
    
    @GetMapping("/clientes/pagosUsuarios")
    public String pagosUsuarios(){
        return "clientes/pagosUsuarios";
    }
    
    @GetMapping("/clientes/polizasUsuarios")
    public String polizasUsuarios(){
        return "clientes/polizasUsuarios";
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
