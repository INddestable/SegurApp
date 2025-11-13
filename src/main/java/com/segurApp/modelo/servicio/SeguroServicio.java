
package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.PolizaCliente;
import com.segurApp.modelo.entidad.PolizaModelo;
import com.segurApp.modelo.entidad.Seguro;
import com.segurApp.modelo.repositorio.PolizaClienteRepositorio;
import com.segurApp.modelo.repositorio.PolizaModeloRepositorio;
import com.segurApp.modelo.repositorio.SeguroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeguroServicio {
    
    @Autowired
    SeguroRepositorio seguroRep;
    
    @Autowired
    PolizaModeloRepositorio polizaModRep;
    
    @Autowired
    PolizaClienteRepositorio polizaClienteRep;
    
    public void guardarSeguro(Seguro segu){
        seguroRep.save(segu);
    }
    
    public List<Seguro> listarSeguros(){
        return seguroRep.findAll();
    }
    
    public List<Seguro> buscarSeguros(String tipo, String cobertura, Double costo, String duracion, String aseguradora) {
        return seguroRep.findByFiltros(tipo, cobertura, costo, duracion, aseguradora);
    }
    
    public Seguro buscarPorId(Integer id){
        return seguroRep.findById(id).orElse(null);
    }
    
    public void eliminarSeguro(Integer id){
        Seguro seguro = seguroRep.findById(id).orElse(null);
        
        if(seguro.getPolizas_Modelos() != null){
            
            
            
            List<PolizaModelo> polizasModelos = seguro.getPolizas_Modelos();
            
            for( PolizaModelo pm : polizasModelos){
                if(pm.getPolizas_cliente() !=null){
                    List<PolizaCliente> polizasClientes = pm.getPolizas_cliente();
                    for(PolizaCliente pc : polizasClientes){
                        polizaClienteRep.deleteById(pc.getId_poliza());
                    }
                }else{
                    polizaModRep.deleteById(pm.getId_modelos());
                }
                
                polizaModRep.deleteById(pm.getId_modelos());
            }
            
            seguroRep.deleteById(id);
            
        }else{
            seguroRep.deleteById(id);
        }
        
    }
    
    public void actualizarSeguro(Seguro seguro){
        Optional<Seguro> existente = seguroRep.findById(seguro.getSeguro_id());
        
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
            
            seguroRep.save(seguroExistente);
        }
        
    }
    
}
