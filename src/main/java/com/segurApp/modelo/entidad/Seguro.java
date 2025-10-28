
package com.segurApp.modelo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name="seguro")
public class Seguro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name= "seguro_id")
    private Integer seguro_id;
    
    private String tipo;
    private String cobertura;
    private Float costo;
    private String duracion;
    private String aseguradora;
    private String beneficios; 
    private String exclusiones;
    private String condiciones; 
    private String region; 
    
    @OneToMany (mappedBy = "seguro")
    private List<PolizaModelo> polizas_Modelos;
    
    //constructo con parametros (revisar polizas modelos)

    public Seguro(Integer seguro_id, String tipo, String cobertura, Float costo, String duracion, String aseguradora, String beneficios, String exclusiones, String condiciones, String region, List<PolizaModelo> polizas_Modelos) {
        this.seguro_id = seguro_id;
        this.tipo = tipo;
        this.cobertura = cobertura;
        this.costo = costo;
        this.duracion = duracion;
        this.aseguradora = aseguradora;
        this.beneficios = beneficios;
        this.exclusiones = exclusiones;
        this.condiciones = condiciones;
        this.region = region;
        this.polizas_Modelos = polizas_Modelos;
    }

    //constructor vacio
    public Seguro() {
    }
    
    //metodos get y set

    public Integer getSeguro_id() {
        return seguro_id;
    }

    public void setSeguro_id(Integer seguro_id) {
        this.seguro_id = seguro_id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    public String getExclusiones() {
        return exclusiones;
    }

    public void setExclusiones(String exclusiones) {
        this.exclusiones = exclusiones;
    }

    public String getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<PolizaModelo> getPolizas_Modelos() {
        return polizas_Modelos;
    }

    public void setPolizas_Modelos(List<PolizaModelo> polizas_Modelos) {
        this.polizas_Modelos = polizas_Modelos;
    }
    
    
}
