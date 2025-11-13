package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.entidad.PolizaCliente;
import com.segurApp.modelo.entidad.PolizaModelo;
import com.segurApp.modelo.repositorio.PolizaClienteRepositorio;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraServicio {

    @Autowired
    private PolizaClienteRepositorio polizaClienteRepo;

    public List<PolizaCliente> listarPorCliente(Cliente cliente) {
        return polizaClienteRepo.findByCliente(cliente);
    }
    
    public void finalizarCompra(Cliente cliente, List<PolizaModelo> carrito) {
        LocalDate hoy = LocalDate.now();

        for (PolizaModelo modelo : carrito) {
            // Evitar comprar la misma póliza dos veces
            if (polizaClienteRepo.existsByClienteAndPoliza_modelo(cliente, modelo)) {
                continue;
            }

            PolizaCliente poliza = new PolizaCliente();
            poliza.setEstado("pago pendiente");
            poliza.setFecha_inicio(hoy.toString());
            poliza.setFecha_fin(hoy.plusYears(1).toString());
            poliza.setRazon_cancelacion(null);
            poliza.setCliente(cliente);
            poliza.setPoliza_modelo(modelo);

            polizaClienteRepo.save(poliza);
        }
    }
}