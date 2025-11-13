package com.segurApp.controlador;

import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.entidad.PolizaCliente;
import com.segurApp.modelo.entidad.PolizaModelo;
import com.segurApp.modelo.servicio.ClienteServicio;
import com.segurApp.modelo.servicio.CompraServicio;
import com.segurApp.modelo.servicio.PolizaModeloServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("carrito")
public class ClienteControlador {
    
    @Autowired
    ClienteServicio clienteServ;
    
    @Autowired
    PolizaModeloServicio polizaModeloServicio;
    
    @Autowired
    CompraServicio compraServicio;
    
    @GetMapping("/clientes/loginUsuarios")
    public String loginCliente() {
        return "clientes/loginUsuarios"; // templates/clientes/loginUsuarios.html
    }
    
    @GetMapping("/clientes/prueba")
    public String pruebaCliente(){
        return "clientes/prueba";
    }

    @GetMapping("/clientes/testPolizasClientes/{id}")
    @ResponseBody
    public String testPolizasCliente(@PathVariable Integer id) {
        Cliente cliente = clienteServ.buscarPorDocumento(id);
        if (cliente != null) {
            System.out.println("PolizasCliente asociadas al cliente: " + cliente.getNombre());
            for (PolizaCliente p : cliente.getPolizas()) {
                System.out.println("- " + p.getEstado());
            }
            return "PolizasCliente mostradas en consola para el cliente ID: " + id;
        } else {
            return " No se encontró el cliente con ID: " + id;
        }
        
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
    
    @GetMapping("/clientes/compraSeguros")
    public String mostrarSeguros(Model model, @ModelAttribute("carrito") List<PolizaModelo> carrito) {
        Cliente cliente = clienteServ.obtenerClienteActual();
        List<PolizaModelo> modelos = polizaModeloServicio.listarPolizasMod();
        List<PolizaCliente> polizasCliente = compraServicio.listarPorCliente(cliente);

        // Filtramos las pólizas que el cliente ya compró
        modelos.removeIf(m -> polizasCliente.stream()
            .anyMatch(pc -> pc.getPoliza_modelo().getId_modelos().equals(m.getId_modelos())));

        // CALCULAR EL TOTAL DEL CARRITO
        double total = carrito.stream()
            .mapToDouble(pm -> pm.getSeguro().getCosto())
            .sum();

        model.addAttribute("modelos", modelos);
        model.addAttribute("carrito", carrito);
        model.addAttribute("totalCarrito", total); // Enviamos el total calculado

        return "clientes/compraSeguros";
    }

    @ModelAttribute("carrito")
    public List<PolizaModelo> inicializarCarrito() {
        return new ArrayList<>();
    }

    /*
    @PostMapping("/clientes/compraSeguros/{id}")
    public String agregarAlCarrito(@PathVariable Integer id,
                                   @ModelAttribute("carrito") List<PolizaModelo> carrito,
                                   Model model) {
        PolizaModelo modelo = polizaModeloServicio.buscarPorId(id);
        if (modelo != null && !carrito.contains(modelo)) {
            carrito.add(modelo);
        }
        return "redirect:/clientes/compraSeguros";
    }
*/
    
    @PostMapping("/clientes/compraSeguros/{id}")
    public String agregarAlCarrito(@PathVariable Integer id,
                                   @ModelAttribute("carrito") List<PolizaModelo> carrito,
                                   Model model) {
        PolizaModelo modelo = polizaModeloServicio.buscarPorId(id);

        // ✅ VALIDACIÓN MEJORADA - Verificar por ID en lugar de contains
        boolean yaExiste = carrito.stream()
            .anyMatch(item -> item.getId_modelos().equals(id));

        if (modelo != null && !yaExiste) {
            carrito.add(modelo);
        } else if (modelo != null) {
            // Opcional: agregar un mensaje de que ya está en el carrito
            model.addAttribute("mensaje", "Este seguro ya está en tu carrito");
        }

        // Recalcular total
        double total = carrito.stream()
            .mapToDouble(pm -> pm.getSeguro().getCosto())
            .sum();
        model.addAttribute("totalCarrito", total);

        return "redirect:/clientes/compraSeguros";
    }
    
    @PostMapping("/clientes/polizasUsuarios")
    public String finalizarCompra(@ModelAttribute("carrito") List<PolizaModelo> carrito,
                                  SessionStatus status,
                                  Model model) {

        Cliente cliente = clienteServ.obtenerClienteActual();
        compraServicio.finalizarCompra(cliente, carrito);

        //CARGAR LAS PÓLIZAS ACTUALIZADAS DEL CLIENTE
        List<PolizaCliente> polizasActualizadas = compraServicio.listarPorCliente(cliente);

        model.addAttribute("cliente", cliente);
        model.addAttribute("polizas", polizasActualizadas); // NO BORRAR
        model.addAttribute("mensaje", "Compra realizada exitosamente U//w//U");

        status.setComplete(); // Limpia el carrito
        return "clientes/polizasUsuarios";
    }
}