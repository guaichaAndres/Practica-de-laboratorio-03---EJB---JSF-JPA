package ec.edu.ups.controlador;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ec.edu.ups.ejb.PersonaFacade;
import ec.edu.ups.modelo.Persona;

@Named
@RequestScoped
public class AgregarClienteControler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PersonaFacade ejbPersonaFacade;
	
	private String nombre="";
	private String apellido="";
	private String cedula="";
    private String direccion="";
    private String telefono="";
    private String correo="";
    private String contrasena="";
    
    public String agregarUsuario() {
    	Persona personaComprobar = null;
    	try {
    		personaComprobar = ejbPersonaFacade.buscarCliente(cedula);
    		if(personaComprobar==null) {
    			Persona persona = new Persona(0, nombre, apellido, cedula, direccion, telefono, correo, "nohay", 'C', 'H');
    			ejbPersonaFacade.create(persona);
    			return "inicioe";
    		}else {
    			return "agregarc";
    		}
		} catch (Exception e) {
			return "agregarc";
		}
    }
    
    public String salir() {
    	return "inicioe";
    }
    
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
    
    
	
}