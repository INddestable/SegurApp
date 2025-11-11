
package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.Seguro;
import com.segurApp.modelo.repositorio.SeguroRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeguroServicio {
    
    @Autowired
    SeguroRepositorio seguroRep;
    
    public void guardarSeguro(Seguro segu){
        seguroRep.save(segu);
    }
    
    public List<Seguro> listarSeguros(){
        return seguroRep.findAll();
    }
    
    public Seguro buscarPorId(Integer id){
        return seguroRep.findById(id).orElse(null);
    }
    
}
