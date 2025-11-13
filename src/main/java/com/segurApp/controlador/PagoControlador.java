package com.segurApp.controlador;

import com.segurApp.modelo.entidad.*;
import com.segurApp.modelo.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PagoControlador {
    
    @Autowired
    private PagoServicio pagoServicio;
    
    @Autowired
    private PolizaClienteServicio polizaClienteServicio;
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @Autowired
    private FacturaServicio facturaServicio;
    
    // Nueva ruta para gestión de pagos
    @GetMapping("/clientes/gestionPagos")
    public String mostrarPagos(
        @RequestParam(required = false) Integer polizaId,
        Model model) {
        
        Cliente cliente = clienteServicio.obtenerClienteActual();
        
        // Obtener polizas del cliente con estado "pago pendiente"
        List<PolizaCliente> polizasPendientes = polizaClienteServicio.listarPorCliente(cliente)
            .stream()
            .filter(p -> "pago pendiente".equals(p.getEstado()))
            .toList();
        
        model.addAttribute("polizasPendientes", polizasPendientes);
        model.addAttribute("cliente", cliente);
        model.addAttribute("fechaHoy", LocalDate.now().toString());
        
        // Si viene de "Enviar a Pagos", cargar la poliza específica
        if (polizaId != null) {
            PolizaCliente polizaSeleccionada = polizaClienteServicio.buscarPorId(polizaId);
            if (polizaSeleccionada != null && polizaSeleccionada.getCliente().getDocumento().equals(cliente.getDocumento())) {
                model.addAttribute("polizaSeleccionada", polizaSeleccionada);
                // Calcular monto (usando el costo del seguro)
                Float monto = polizaSeleccionada.getPoliza_modelo().getSeguro().getCosto();
                model.addAttribute("monto", monto);
                
                // Cargar historial de pagos de esta poliza
                List<Pago> historialPagos = pagoServicio.listarPagosPorPoliza(polizaId);
                model.addAttribute("historialPagos", historialPagos);
            }
        }
        
        return "clientes/pagosUsuarios"; // Mantiene la misma vista
    }
    
    // ✅ CAMBIADO: Nueva ruta para procesar pago
    @PostMapping("/clientes/procesarPago")
    public String procesarPago(
        @RequestParam Integer polizaId,
        @RequestParam Float monto,
        @RequestParam String fechaPago,
        @RequestParam String formaPago,
        Model model) {
        
        try {
            Cliente cliente = clienteServicio.obtenerClienteActual();
            PolizaCliente poliza = polizaClienteServicio.buscarPorId(polizaId);
            
            // Validar que la poliza pertenece al cliente
            if (poliza == null || !poliza.getCliente().getDocumento().equals(cliente.getDocumento())) {
                model.addAttribute("error", "Poliza no válida");
                return "redirect:/clientes/gestionPagos"; // ✅ Ruta actualizada
            }
            
            // Crear factura
            Factura factura = new Factura();
            factura.setCliente(cliente);
            factura.setFecha_emision(LocalDate.now().toString());
            factura.setTotal(monto);
            factura.setMetodo_principal(formaPago);
            factura.setDescripcion("Pago de póliza #" + polizaId);
            
            factura = facturaServicio.guardar(factura);
            
            // Registrar pago
            Pago pago = pagoServicio.registrarPago(poliza, monto, factura);
            
            // Actualizar estado de la poliza a "vigente"
            poliza.setEstado("vigente");
            polizaClienteServicio.guardar(poliza);
            
            model.addAttribute("exito", "Pago realizado exitosamente");
            model.addAttribute("pago", pago);
            model.addAttribute("factura", factura);
            
            return "clientes/comprobante"; // Vista de comprobante
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar el pago: " + e.getMessage());
            return "redirect:/clientes/gestionPagos"; // ✅ Ruta actualizada
        }
    }
}