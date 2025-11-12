
package com.segurApp.modelo.servicio;

import com.segurApp.modelo.entidad.Cliente;
import com.segurApp.modelo.repositorio.ClienteRepositorio;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

    //CONSTRUCTOR
    public ClienteRegistro(Integer documento, String nombre, String tipo_documento, Integer edad, String telefono, String email, String password) {
        this.documento = documento;
        this.nombre = nombre;
        this.tipo_documento = tipo_documento;
        this.edad = edad;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.rol = "ROLE_USER";
    }
         
    }//clase
    
    
    
    public void guardar(Cliente c){
        clienteRepo.save(c);
    }
    
    public List<Cliente> listarTodos(){
        return clienteRepo.findAll();
    }
    
    public void actualizarCliente(Cliente c){
        Optional<Cliente> existente = clienteRepo.findById(c.getDocumento());
        if (existente.isPresent()){
            Cliente clienteExistente = existente.get();
            clienteExistente.setNombre(c.getNombre());
            clienteExistente.setTipo_documento(c.getTipo_documento());
            clienteExistente.setPuntaje_crediticio(c.getPuntaje_crediticio());
            clienteExistente.setEdad(c.getEdad());
            clienteExistente.setTelefono(c.getTelefono());
            clienteExistente.setEmail(c.getEmail());
            clienteExistente.setPassword(c.getPassword());
            
            clienteRepo.save(clienteExistente);
        }
    }
    
    public void eliminarCliente(Integer id){
        clienteRepo.deleteById(id);
    }

    
    /*public Optional<ClienteRegistro> buscarPorUsuario(String usuarioNombre){ //otro tipo de collection, retorna lo que sea
        return Optional.ofNullable(clientes.get(usuarioNombre));
    }*/
    
    public Cliente buscarPorDocumento(Integer documento) {
            return clienteRepo.findById(documento).orElse(null);
    }
        
    public Cliente busacarPorCorreo(String email){
            Cliente c = clienteRepo.findByEmail(email);
            System.out.println("Buscando cliente por correo: " + email);
            System.out.println("Resultado: " + (c != null ? c.getNombre() : "NO ENCONTRADO"));
            return c;
    }
    
    public Cliente obtenerClienteActual() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || auth.getName().equals("anonymousUser")) return null;
    return clienteRepo.findByEmail(auth.getName());
    }

}
