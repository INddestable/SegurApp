
package com.segurApp.controlador;

import com.segurApp.modelo.entidad.Administrador;
import com.segurApp.modelo.entidad.PolizaCliente;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PolizaModeloControlador {
    
    @Autowired
    PolizaModeloServicio polizaModServ;
    
    @Autowired
    SeguroServicio seguroServ;
    
    @Autowired
    AdministradorServicio adminServ;
    
    
    @GetMapping("/administradores/gestionPolizas")
    public String guardarPolizaMod(@RequestParam (required = false) String nombre_plan, Model modelo){
        
        List<PolizaModelo> listaPolizaMod;
        
        modelo.addAttribute("polizaModelo", new PolizaModelo());
        modelo.addAttribute("seguros", seguroServ.listarSeguros());
        modelo.addAttribute("administradores", adminServ.listarAdmin());
        
        if(nombre_plan == null || nombre_plan.isEmpty()){
            listaPolizaMod = polizaModServ.listarPolizasMod();
        }else{
            listaPolizaMod = polizaModServ.buscarPorNombrePlan(nombre_plan);
        }
        
        
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
    
    @PostMapping("/administradores/actualizarPM")
    public String actualizarPolizaModelo(@ModelAttribute PolizaModelo polizaMod, @RequestParam("seguro_id") Integer seguroId, 
            @RequestParam("documento_admin") Integer adminId){
        
        Seguro seguro = seguroServ.buscarPorId(seguroId);
        Administrador admin = adminServ.buscarPorId(adminId);
        
        /*polizaMod.setSeguro(seguro);
        polizaMod.setDocumento_admin(admin);*/
        
        polizaModServ.actualizarPoliza(polizaMod, seguro, admin);
        
        seguro.getPolizas_Modelos().add(polizaMod);
        seguroServ.guardarSeguro(seguro);
        
        return "redirect:/administradores/gestionPolizas";
        
    }
    
    @GetMapping("/administradores/testPolizasCliente/{id}")
    @ResponseBody
    public String testPolizasCliente(@PathVariable Integer id) {
        PolizaModelo polizaMod = polizaModServ.buscarPorId(id);
        if (polizaMod != null) {
            System.out.println("PolizasCliente asociadas a la poliza modelo: " + polizaMod.getNombre_plan());
            for (PolizaCliente p : polizaMod.getPolizas_cliente()) {
                System.out.println("- " + p.getEstado());
            }
            return "PolizasCliente mostradas en consola para la polizaModelo ID: " + id;
        } else {
            return " No se encontró la polizaModelo con ID: " + id;
        }
        
    }
}
