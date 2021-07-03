package ec.edu.ups.controlador;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import ec.edu.ups.ejb.PersonaFacade;
import ec.edu.ups.modelo.Persona;

@Named
@RequestScoped
public class IniciarSesionControler implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PersonaFacade ejbPersonaFacade;
	
	private String correo = "";
	private String contrasena = "";
	private String paginaRol;
	private Persona persona = new Persona();
	
	public String iniciarSesion() {	
		
		SessionUtils sU = new SessionUtils();
		
		try {
			persona = ejbPersonaFacade.verificarUsuario(correo, contrasena);
			
			if (persona.getRol() == 'A') {
				//HttpSession session = request.getSession(true);
				//session.setAttribute("accesos", 1);
				sU.login();
				
				paginaRol = "inicioAdministrador";
			} else if (persona.getRol() == 'E') {
				//HttpSession session = request.getSession(true);
				//session.setAttribute("accesos", 1);
				sU.login();
				
				paginaRol = "inicioEmpleado";
			}
			
			return paginaRol;
		} catch(Exception e) {
			System.out.println("Error IniciarSesion: " + e);
			
			return "paginaError";
		}
		
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



	public String getPaginaRol() {
		return paginaRol;
	}



	public void setPaginaRol(String paginaRol) {
		this.paginaRol = paginaRol;
	}



	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	
}
