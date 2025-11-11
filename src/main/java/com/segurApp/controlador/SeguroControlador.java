
package com.segurApp.controlador;

import com.segurApp.modelo.entidad.PolizaModelo;
import com.segurApp.modelo.entidad.Seguro;
import com.segurApp.modelo.servicio.SeguroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SeguroControlador {
    
    @Autowired
    SeguroServicio seguroServ;
    
    @GetMapping("/administradores/registroSeguros")
    public String registroSeguros(Model modelo){
        Seguro seguro = new Seguro();
        modelo.addAttribute(seguro);
        List<Seguro> listaSeguros = seguroServ.listarSeguros();
        modelo.addAttribute("seguros", listaSeguros);
        return "administradores/registroSeguros";
    }
    
    @PostMapping ("/administradores/guardarSeguro")
    public String guardarSeguro(@ModelAttribute Seguro seguro, Model modelo){
        modelo.addAttribute("seguro", seguro);
        seguroServ.guardarSeguro(seguro);
        return "redirect:/administradores/registroSeguros";
    }
    
    @GetMapping("/administradores/testPolizas/{id}")
    @ResponseBody
    public String testPolizas(@PathVariable Integer id) {
        Seguro seguro = seguroServ.buscarPorId(id);
        if (seguro != null) {
            System.out.println("Polizas asociadas al seguro: " + seguro.getTipo());
            for (PolizaModelo p : seguro.getPolizas_Modelos()) {
                System.out.println("- " + p.getNombre_plan());
            }
            return "Polizas mostradas en consola para el seguro ID: " + id;
        } else {
            return " No se encontró el seguro con ID: " + id;
        }
}
    
    /*
    
    @GetMapping("/administradores/listarSeguros")
    public String listarSeguros(Model modelo){
        List<Seguro> listaSeguros = seguroServ.listarSeguros();
        modelo.addAttribute("seguro", new Seguro());
        modelo.addAttribute("seguros", listaSeguros);
        return "administradores/registroSeguros";
    }*/
    
}
