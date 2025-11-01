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
}
