
package com.segurApp.controlador;

import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.servicio.ClienteServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class ClienteControlador {
    
    @Autowired
    ClienteServicio clienteServ;
    
    @GetMapping("/clientes/loginUsuarios")
    public String loginCliente() {
        return "clientes/loginUsuarios"; // templates/clientes/loginUsuarios.html
    }

    @GetMapping("/clientes/registroUsuario")
    public String registroCliente(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute(cliente);
        return "clientes/registroUsuario"; // templates/clientes/registroUsuario.html
    }
    
    @PostMapping("clientes/guardar")
    public String guardar(@ModelAttribute Cliente cliente, Model model) {
        model.addAttribute("cliente", cliente);
        clienteServ.guardar(cliente);
        return "redirect:/clientes/registroUsuario";
    }
    
    @GetMapping("clientes/listar")
    public String listarClientes(Model model){
        List<Cliente> listadoClientes = clienteServ.listarTodos();
        model.addAttribute("clientes", listadoClientes);
        return "/clientes/listar";
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
}
