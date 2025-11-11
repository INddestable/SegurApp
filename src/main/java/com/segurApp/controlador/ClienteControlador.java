
package com.segurApp.controlador;

import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.entidad.PolizaModelo;
import com.segurApp.modelo.servicio.ClienteServicio;
import com.segurApp.modelo.servicio.PolizaModeloServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("carrito")
public class ClienteControlador {
    
    @Autowired
    ClienteServicio clienteServ;
    
    @Autowired
    PolizaModeloServicio polizaModeloServicio;
    
    @GetMapping("/clientes/loginUsuarios")
    public String loginCliente() {
        return "clientes/loginUsuarios"; // templates/clientes/loginUsuarios.html
    }
    
    @GetMapping("/clientes/prueba")
    public String pruebaCliente(){
        return "clientes/prueba";
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
    
    /*@GetMapping("clientes/listar")
    public String listarClientes(Model model){
        List<Cliente> listadoClientes = clienteServ.listarTodos();
        model.addAttribute("clientes", listadoClientes);
        return "/administradores/gestionClientes";
    }*/


    
    @GetMapping("/clientes/ayuda")
    public String ayuda(){
        return "clientes/ayuda";
    }
    
    @GetMapping("/clientes/dashboard")
    public String dashboardClientes(){
        return "clientes/dashboard";
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
    
    @GetMapping("/clientes/compraSeguros")
    public String mostrarSeguros(Model model) {
        List<PolizaModelo> modelos = polizaModeloServicio.listarPolizasMod();
        model.addAttribute("modelos", modelos);
        return "clientes/compraSeguros";
    }
    
    @ModelAttribute("carrito")
    public List<PolizaModelo> inicializarCarrito() {
        return new ArrayList<>();
    }
    
    @PostMapping("/clientes/compraSeguros/{id}")
    public String agregarAlCarrito(@PathVariable Integer id,
                                   @ModelAttribute("carrito") List<PolizaModelo> carrito,
                                   Model model) {
        PolizaModelo modelo = polizaModeloServicio.buscarPorId(id);
        if (modelo != null && !carrito.contains(modelo)) {
            carrito.add(modelo);
        }

        model.addAttribute("modelos", polizaModeloServicio.listarPolizasMod());
        model.addAttribute("carrito", carrito);
        return "clientes/compraSeguros";
    }
    
    @PostMapping("/clientes/pagosUsuarios")
    public String finalizarCompra(@ModelAttribute("carrito") List<PolizaModelo> carrito,
                                  SessionStatus status,
                                  Model model) {
        
        model.addAttribute("carritoFinal", carrito);

        
        status.setComplete();

        return "clientes/pagosUsuarios"; 
    }
    
    
}
