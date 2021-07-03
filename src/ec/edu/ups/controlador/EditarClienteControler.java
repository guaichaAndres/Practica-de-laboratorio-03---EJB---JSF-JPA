package ec.edu.ups.controlador;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ec.edu.ups.ejb.PersonaFacade;
import ec.edu.ups.modelo.Persona;

@Named
@RequestScoped
public class EditarClienteControler implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PersonaFacade ejbPersonaFacade;
	
	private String nombre="";
	private String apellido="";
	private String cedula="";
	private String cedulaBuscar="";
    private String direccion="";
    private String telefono="";
    private String correo="";
    private String contrasena="";
    private String visibilidad="display: none";
    
    
    public void buscarPersona() {
    	try {
			
    		Persona persona = ejbPersonaFacade.buscarCliente(cedulaBuscar);
    		this.setNombre(persona.getNombre());
    		this.setApellido(persona.getApellido());
    		this.setCedula(persona.getCedula());
    		this.setDireccion(persona.getDireccion());
    		this.setTelefono(persona.getTelefono());
    		this.setCorreo(persona.getCorreo());
    		this.setContrasena(persona.getContrasena());
    		this.setVisibilidad("display: inline");
		} catch (Exception e) {
			System.out.println("ERROR AL BUSCAR");
		}
    }
    
    public String editarCliente() {
    	try {
    		
    		
    		Persona persona = ejbPersonaFacade.buscarCliente(cedula);
    		
			persona.setApellido(apellido);
			persona.setNombre(nombre);
			persona.setDireccion(direccion);
			persona.setTelefono(telefono);
			persona.setCorreo(correo);
			
			ejbPersonaFacade.edit(persona);
			
			visibilidad="display: none";
			
			return "inicioe";
			
		} catch (Exception e) {
			System.out.println("se dano");
			return "editarc";
		}
    }
    
    
    public String redirigir(String pagina) {
    	return pagina;
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
	public String getCedulaBuscar() {
		return cedulaBuscar;
	}
	public void setCedulaBuscar(String cedulaBuscar) {
		this.cedulaBuscar = cedulaBuscar;
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

	public PersonaFacade getEjbPersonaFacade() {
		return ejbPersonaFacade;
	}

	public void setEjbPersonaFacade(PersonaFacade ejbPersonaFacade) {
		this.ejbPersonaFacade = ejbPersonaFacade;
	}

	public String getVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(String visibilidad) {
		this.visibilidad = visibilidad;
	}

    
    
}
