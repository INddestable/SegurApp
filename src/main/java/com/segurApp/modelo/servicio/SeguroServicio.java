package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.Pago;
import com.segurApp.modelo.entidad.PolizaCliente;
import com.segurApp.modelo.entidad.PolizaModelo;
import com.segurApp.modelo.entidad.Seguro;
import com.segurApp.modelo.repositorio.SeguroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeguroServicio {
    
    @Autowired
    private SeguroRepositorio seguroRepo;
    
    @Autowired
    private PolizaModeloServicio polizaModeloServicio;
    
    @Autowired
    private PolizaClienteServicio polizaClienteServicio;
    
    @Autowired
    private PagoServicio pagoServicio;
    
    @Autowired
    private FacturaServicio facturaServicio;
    
    @Autowired
    private DetallePagoServicio detallePagoServicio;
    
    public void guardarSeguro(Seguro seguro){
        seguroRepo.save(seguro);
    }
    
    public List<Seguro> listarSeguros(){
        return seguroRepo.findAll();
    }
    
    public List<Seguro> buscarSeguros(String tipo, String cobertura, Double costo, String duracion, String aseguradora) {
        return seguroRepo.findByFiltros(tipo, cobertura, costo, duracion, aseguradora);
    }
    
    public Seguro buscarPorId(Integer id){
        return seguroRepo.findById(id).orElse(null);
    }
    
    // ✅ **ÚNICO MÉTODO DE ELIMINACIÓN - EL QUE SÍ FUNCIONA**
    @Transactional
    public void eliminarSeguro(Integer seguroId) {
        Seguro seguro = seguroRepo.findById(seguroId)
            .orElseThrow(() -> new RuntimeException("Seguro no encontrado"));
        
        System.out.println("🚀 Iniciando eliminación en cascada para seguro: " + seguroId);
        
        // ✅ ORDEN ABSOLUTAMENTE CORRECTO:
        for (PolizaModelo modelo : seguro.getPolizas_Modelos()) {
            
            System.out.println("📋 Procesando modelo: " + modelo.getId_modelos());
            
            // 1. Primero obtener TODAS las polizas_cliente de este modelo
            List<PolizaCliente> polizasCliente = polizaClienteServicio.buscarPorPolizaModelo(modelo);
            System.out.println("👥 Encontradas " + polizasCliente.size() + " polizas_cliente");
            
            for (PolizaCliente polizaCliente : polizasCliente) {
                System.out.println("🎯 Procesando poliza_cliente: " + polizaCliente.getId_poliza());
                
                // 2. Obtener TODOS los pagos de esta poliza_cliente
                List<Pago> pagos = pagoServicio.buscarPorPolizaCliente(polizaCliente);
                System.out.println("💰 Encontrados " + pagos.size() + " pagos");
                
                for (Pago pago : pagos) {
                    System.out.println("🧾 Procesando pago: " + pago.getPago_id());
                    
                    // 3. PRIMERO eliminar detalles_pago relacionados con este pago
                    try {
                        detallePagoServicio.eliminarPorPago(pago.getPago_id());
                        System.out.println("✅ Detalles_pago eliminados para pago: " + pago.getPago_id());
                    } catch (Exception e) {
                        System.out.println("ℹ️ No hay detalles_pago o ya fueron eliminados");
                    }
                    
                    // 4. LUEGO eliminar la factura relacionada (si existe)
                    if (pago.getFactura() != null) {
                        try {
                            facturaServicio.eliminar(pago.getFactura().getFactura_id());
                            System.out.println("✅ Factura eliminada: " + pago.getFactura().getFactura_id());
                        } catch (Exception e) {
                            System.out.println("❌ Error eliminando factura: " + e.getMessage());
                        }
                    }
                    
                    // 5. FINALMENTE eliminar el pago
                    try {
                        pagoServicio.eliminar(pago.getPago_id());
                        System.out.println("✅ Pago eliminado: " + pago.getPago_id());
                    } catch (Exception e) {
                        System.out.println("❌ Error eliminando pago: " + e.getMessage());
                        throw e;
                    }
                }
                
                // 6. AHORA SÍ podemos eliminar la poliza_cliente
                try {
                    polizaClienteServicio.eliminar(polizaCliente.getId_poliza());
                    System.out.println("✅ Poliza_cliente eliminada: " + polizaCliente.getId_poliza());
                } catch (Exception e) {
                    System.out.println("❌ Error eliminando poliza_cliente: " + e.getMessage());
                    throw e;
                }
            }
            
            // 7. Eliminar el poliza_modelo
            try {
                polizaModeloServicio.eliminarPoliza(modelo.getId_modelos());
                System.out.println("✅ Poliza_modelo eliminada: " + modelo.getId_modelos());
            } catch (Exception e) {
                System.out.println("❌ Error eliminando poliza_modelo: " + e.getMessage());
                throw e;
            }
        }
        
        // 8. ÚLTIMO PASO: eliminar el seguro
        seguroRepo.delete(seguro);
        System.out.println("🎉 Seguro eliminado exitosamente: " + seguroId);
    }
    
    public void actualizarSeguro(Seguro seguro){
        Optional<Seguro> existente = seguroRepo.findById(seguro.getSeguro_id());
        
        if(existente.isPresent()){
            Seguro seguroExistente = existente.get();
            
            seguroExistente.setTipo(seguro.getTipo());
            seguroExistente.setCobertura(seguro.getCobertura());
            seguroExistente.setCosto(seguro.getCosto());
            seguroExistente.setDuracion(seguro.getDuracion());
            seguroExistente.setAseguradora(seguro.getAseguradora());
            seguroExistente.setBeneficios(seguro.getBeneficios());
            seguroExistente.setExclusiones(seguro.getExclusiones());
            seguroExistente.setCondiciones(seguro.getCondiciones());
            seguroExistente.setRegion(seguro.getRegion());
            
            seguroRepo.save(seguroExistente);
        }
    }
}