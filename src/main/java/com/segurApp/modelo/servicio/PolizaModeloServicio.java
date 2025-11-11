
package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.PolizaModelo;
import com.segurApp.modelo.repositorio.PolizaModeloRepositorio;
import java.util.List;
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
    
    public void eliminarPoliza(Integer id){
        polizaModRep.deleteById(id);
    }
    
    
}
