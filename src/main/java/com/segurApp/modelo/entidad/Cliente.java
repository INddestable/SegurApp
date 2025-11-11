
package com.segurApp.modelo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Clientes")

public class Cliente {
    
    @Id
    private Integer documento; 
    
    private String nombre; 
    
    @Column(name = "tipo_documento")
    private String tipo_documento;
    
    @Column(name = "puntaje_crediticio")
    private Integer puntaje_crediticio;
    
    private Integer edad;
    private String telefono;
    private String email;
    private String password;
    
    @OneToMany(mappedBy = "cliente_id")
    private List<PolizaCliente> polizas;
    
    @OneToMany(mappedBy = "cliente_id")
    private List<Factura> facturas;
    
    private String rol;
    
    
    //constructor con parametros (revisar facturas)
    public Cliente(Integer documento, String nombre, String tipo_documento, Integer edad, String telefono, String email, String password ) {
        this.documento = documento;
        this.nombre = nombre;
        this.tipo_documento = tipo_documento;
        this.puntaje_crediticio = null;
        this.edad = edad;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.polizas = null;
        this.facturas = null;
        this.rol = "USER";
    }

    //constructor vacio (si al fentanilo)

    public Cliente() {
        this.rol = "USER";
    }
    
    //metodos get y set

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public Integer getPuntaje_crediticio() {
        return puntaje_crediticio;
    }

    public void setPuntaje_crediticio(Integer puntaje_crediticio) {
        this.puntaje_crediticio = puntaje_crediticio;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PolizaCliente> getPolizas() {
        return polizas;
    }

    public void setPolizas(List<PolizaCliente> polizas) {
        this.polizas = polizas;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
}
