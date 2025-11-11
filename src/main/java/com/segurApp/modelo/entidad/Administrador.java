
package com.segurApp.modelo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table (name = "Administradores")
public class Administrador {
    
    @Id
    @Column (name = "documento_admin")
    private Integer documento_admin;
    
    private String nombre; 
    private String email;
    private String telefono;
    private String password;
    
    @OneToMany (mappedBy = "documento_admin")
    private List<PolizaModelo> polizas_Modelos;
    
    private String rol;

    //constructor con parametros
    public Administrador(Integer documento_admin, String nombre, String email, String telefono, String password) {
        this.documento_admin = documento_admin;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.polizas_Modelos = null;
        this.rol = "ADMIN";
    }
    
    
    //constructor sin parametros (ojalá se acabe el semestre rapido)

    public Administrador() {
        this.rol = "ADMIN";
    }
    
    //metodos get y set

    public Integer getDocumento_admin() {
        return documento_admin;
    }

    public void setDocumento_admin(Integer documento_admin) {
        this.documento_admin = documento_admin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PolizaModelo> getPolizas_Modelos() {
        return polizas_Modelos;
    }

    public void setPolizas_Modelos(List<PolizaModelo> polizas_Modelos) {
        this.polizas_Modelos = polizas_Modelos;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
}
