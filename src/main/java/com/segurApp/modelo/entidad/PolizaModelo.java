
package com.segurApp.modelo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Polizas_Modelos")
public class PolizaModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_modelos")
    private Integer id_modelos;
   
    @ManyToOne
    @JoinColumn(name = "seguro_id", nullable = false)
    private Seguro seguro;
    
    
    @ManyToOne 
    @JoinColumn(name = "documento_admin", nullable = false)
    private Administrador documento_admin;
    
    @Column(name = "nombre_plan")
    private String nombre_plan; 
    
    private String descripcion;
    
    @Column(name = "condiciones_generales")
    private String condiciones_generales;
    
    @OneToMany(mappedBy = "poliza_modelo")
    @Column(name = "polizas_cliente")
    private List<PolizaCliente> polizas_cliente;
    
    //constructor con parametros (revisar polizas cliente)

    public PolizaModelo(Integer id_modelos, Seguro seguro, Administrador documento_admin, String nombre_plan, String descripcion, String condiciones_generales) {
        this.id_modelos = id_modelos;
        this.seguro = seguro;
        this.documento_admin = documento_admin;
        this.nombre_plan = nombre_plan;
        this.descripcion = descripcion;
        this.condiciones_generales = condiciones_generales;
        this.polizas_cliente = null;
    }
    
    //constructor vacio

    public PolizaModelo() {
    }
    
    //metodos get y set  

    public Integer getId_modelos() {
        return id_modelos;
    }

    public void setId_modelos(Integer id_modelos) {
        this.id_modelos = id_modelos;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

    public Administrador getDocumento_admin() {
        return documento_admin;
    }

    public void setDocumento_admin(Administrador documento_admin) {
        this.documento_admin = documento_admin;
    }

    public String getNombre_plan() {
        return nombre_plan;
    }

    public void setNombre_plan(String nombre_plan) {
        this.nombre_plan = nombre_plan;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCondiciones_generales() {
        return condiciones_generales;
    }

    public void setCondiciones_generales(String condiciones_generales) {
        this.condiciones_generales = condiciones_generales;
    }

    public List<PolizaCliente> getPolizas_cliente() {
        return polizas_cliente;
    }

    public void setPolizas_cliente(List<PolizaCliente> polizas_cliente) {
        this.polizas_cliente = polizas_cliente;
    }
    
    
}
