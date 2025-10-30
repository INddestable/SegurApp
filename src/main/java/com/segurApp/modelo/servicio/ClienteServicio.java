
package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.repositorio.ClienteRepositorio;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio { 
    
    @Autowired
    private ClienteRepositorio clienteRepo;
    
    private final Map<String, ClienteRegistro> clientes = new HashMap();
    
    public static class ClienteRegistro{
        public Integer documento;
        public String nombre;
        public String tipo_documento;
        public Integer edad;
        public String telefono;
        public String email;
        public String password;
        public String rol;

        public ClienteRegistro(Integer documento, String nombre, String tipo_documento, Integer edad, String telefono, String email, String password) {
            this.documento = documento;
            this.nombre = nombre;
            this.tipo_documento = tipo_documento;
            this.edad = edad;
            this.telefono = telefono;
            this.email = email;
            this.password = password;
            this.rol = "ROL_USER";
        }
        
        
    }//clase
    
    public Cliente guardar(Cliente c){
        return clienteRepo.save(c);
    }
    
    public Optional<ClienteRegistro> buscarPorUsuario(String usuarioNombre){ //otro tipo de collection, retorna lo que sea
        return Optional.ofNullable(clientes.get(usuarioNombre));
    }
}
