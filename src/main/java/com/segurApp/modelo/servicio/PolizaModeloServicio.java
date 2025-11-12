
package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.Administrador;
import com.segurApp.modelo.entidad.PolizaModelo;
import com.segurApp.modelo.entidad.Seguro;
import com.segurApp.modelo.repositorio.PolizaModeloRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolizaModeloServicio {
    
    @Autowired
    PolizaModeloRepositorio polizaModRep;
    
    public void guardarPolizaMod(PolizaModelo polizaMod){
        polizaModRep.save(polizaMod);
    }
    
    public List<PolizaModelo> listarPolizasMod(){
        return polizaModRep.findAll();
    }
    
    public PolizaModelo buscarPorId(Integer id) {
        return polizaModRep.findById(id).orElse(null);
}
    
    public void eliminarPoliza(Integer id){
        polizaModRep.deleteById(id);
    }
   
    public List<PolizaModelo> buscarPorNombrePlan(String nombrePlan) {
        return polizaModRep.findByFiltros(nombrePlan);
    }
    
    public void actualizarPoliza(PolizaModelo polizaMod, Seguro seguro, Administrador admin){
        Optional<PolizaModelo> existente = polizaModRep.findById(polizaMod.getId_modelos());
        if(existente.isPresent()){
            PolizaModelo polizaModExist = existente.get();
            
            polizaModExist.setSeguro(seguro);
            polizaModExist.setDocumento_admin(admin);
            polizaModExist.setNombre_plan(polizaMod.getNombre_plan());
            polizaModExist.setDescripcion(polizaMod.getDescripcion());
            polizaModExist.setCondiciones_generales(polizaMod.getCondiciones_generales());
            
            polizaModRep.save(polizaModExist);
        }
    }
    
    
}
