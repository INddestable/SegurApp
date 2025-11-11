
package com.segurApp.controlador;

import com.segurApp.modelo.entidad.Administrador;
import com.segurApp.modelo.entidad.PolizaModelo;
import com.segurApp.modelo.entidad.Seguro;
import com.segurApp.modelo.servicio.AdministradorServicio;
import com.segurApp.modelo.servicio.PolizaModeloServicio;
import com.segurApp.modelo.servicio.SeguroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PolizaModeloControlador {
    
    @Autowired
    PolizaModeloServicio polizaModServ;
    
    @Autowired
    SeguroServicio seguroServ;
    
    @Autowired
    AdministradorServicio adminServ;
    
    
    @GetMapping("/administradores/gestionPolizas")
    public String guardarPolizaMod(Model modelo){
        modelo.addAttribute("polizaModelo", new PolizaModelo());
        modelo.addAttribute("seguros", seguroServ.listarSeguros());
        modelo.addAttribute("administradores", adminServ.listarAdmin());
        List<PolizaModelo> listaPolizaMod = polizaModServ.listarPolizasMod();
        modelo.addAttribute("polizasModelos", listaPolizaMod);
        return "administradores/gestionPolizas";
    }
    
    @PostMapping("/administradores/guardarPolizaMod")
    public String guardarPolizaModelo(@ModelAttribute PolizaModelo polizaMod, @RequestParam("seguro_id") Integer seguroId, 
            @RequestParam("documento_admin") Integer adminId){
        
        Seguro seguro = seguroServ.buscarPorId(seguroId);
        Administrador admin = adminServ.buscarPorId(adminId);
        
        polizaMod.setSeguro(seguro);
        polizaMod.setDocumento_admin(admin);
        
        polizaModServ.guardarPolizaMod(polizaMod);
        
        seguro.getPolizas_Modelos().add(polizaMod);
        seguroServ.guardarSeguro(seguro);
        
        return "redirect:/administradores/gestionPolizas";
    }
    
}
