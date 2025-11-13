package com.segurApp.modelo.entidad;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Polizas_cliente")
public class PolizaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_poliza")
    private Integer id_poliza;

    @ManyToOne
    @JoinColumn(name = "id_modelos")
    private PolizaModelo poliza_modelo;

    // ✅ Esta es la relación correcta con Cliente
    @ManyToOne
    @JoinColumn(name = "documento", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_inicio")
    private String fecha_inicio;

    @Column(name = "fecha_fin")
    private String fecha_fin;

    private String estado;

    @OneToMany(mappedBy = "poliza")
    private List<Pago> pagos;

    @Column(name = "razon_cancelacion")
    private String razon_cancelacion;

    // Constructor con parámetros
    public PolizaCliente(Integer id_poliza, PolizaModelo poliza_modelo, Cliente cliente,
                         String fecha_inicio, String fecha_fin, String estado, List<Pago> pagos) {
        this.id_poliza = id_poliza;
        this.poliza_modelo = poliza_modelo;
        this.cliente = cliente;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estado = estado;
        this.pagos = pagos;
        this.razon_cancelacion = null;
    }

    public PolizaCliente() {}

    // Getters y setters
    public Integer getId_poliza() { return id_poliza; }
    public void setId_poliza(Integer id_poliza) { this.id_poliza = id_poliza; }

    public PolizaModelo getPoliza_modelo() { return poliza_modelo; }
    public void setPoliza_modelo(PolizaModelo poliza_modelo) { this.poliza_modelo = poliza_modelo; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getFecha_inicio() { return fecha_inicio; }
    public void setFecha_inicio(String fecha_inicio) { this.fecha_inicio = fecha_inicio; }

    public String getFecha_fin() { return fecha_fin; }
    public void setFecha_fin(String fecha_fin) { this.fecha_fin = fecha_fin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<Pago> getPagos() { return pagos; }
    public void setPagos(List<Pago> pagos) { this.pagos = pagos; }

    public String getRazon_cancelacion() { return razon_cancelacion; }
    public void setRazon_cancelacion(String razon_cancelacion) { this.razon_cancelacion = razon_cancelacion; }

    public Integer getIdPoliza() {
        return id_poliza;
    }
    
    
    @Override
    public String toString() {
        return "PolizaCliente{" + "fecha_inicio=" + fecha_inicio + ", fecha_fin=" + fecha_fin + '}';
    }
}
