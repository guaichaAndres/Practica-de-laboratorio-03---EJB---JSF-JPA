package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.PersonaFacade;
import ec.edu.ups.modelo.Persona;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@RequestScoped
public class EliminarClienteControler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PersonaFacade ejbPersonaFacade;
	
	private List<Persona> listCliente;
	private Persona personaSeleccionada = null;
	private String cedula;
	
	
	@PostConstruct
	public void cargarLista() {
		listCliente = new ArrayList<Persona>();
		listCliente = ejbPersonaFacade.findCliente();
		System.out.println(listCliente.size());
	}

	
	public String salir() {
		return "inicioe";
	}
	
	public String eliminar(Persona persona) {
		try {
			personaSeleccionada = persona;
			personaSeleccionada.setEstado('D');
			ejbPersonaFacade.edit(personaSeleccionada);
			
			cargarLista();
			
			return "eliminarc";
		} catch (Exception e) {
			cargarLista();
			return "eliminarc";
		}
		
	}
	
	
	public PersonaFacade getEjbPersonaFacade() {
		return ejbPersonaFacade;
	}

	public void setEjbPersonaFacade(PersonaFacade ejbPersonaFacade) {
		this.ejbPersonaFacade = ejbPersonaFacade;
	}

	public List<Persona> getListCliente() {
		return listCliente;
	}

	public void setListCliente(List<Persona> listCliente) {
		this.listCliente = listCliente;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	
	
}
