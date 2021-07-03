package ec.edu.ups.controlador;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.modelo.Bodega;

@Named
@RequestScoped
public class EditarBodegaControler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private BodegaFacade ejbBodegaFacade;
	
	private String nombre="";
	public static  String nombreBuscar="";
	private String direccion="";
    private String visibilidad="display: none";
    private String nombreCiudad="";
    private String nonbreProvincia="";


    public void buscarBodega() {
    	try {
    		
    		Bodega bodega = ejbBodegaFacade.buscarBodega(nombreBuscar);
    		
    		this.setNombre(bodega.getNombre());
    		this.setDireccion(bodega.getDireccion());
    		
    		this.setNombreCiudad(bodega.getCiudad().getNombre());
    		this.setNonbreProvincia(bodega.getCiudad().getProvincia().getNombre());
    		this.setVisibilidad("display: inline");
		} catch (Exception e) {
			System.out.println("bodega no encontrada");
		}
    }

   
    public String editarBodega() {
    	try { 
    		System.out.println("nombre BODEGA B : "+ nombreBuscar);
    		System.out.println("nombre BODEGA : "+ nombre);
    		
    		Bodega bodega = ejbBodegaFacade.buscarBodega(nombreBuscar);
    		bodega.setNombre(nombre);
    		bodega.setDireccion(direccion);
			ejbBodegaFacade.edit(bodega);
			visibilidad="display: none";
			System.out.println("exito editar");
			nombreBuscar="";
			return "inicioa";
			
		} catch (Exception e) {
			System.out.println("error al editar");
			return "editarb";
		}
    }
    
    public String salir() {
    	System.out.println("entra salir");
    	return "inicioa";
    }
    
    

	public BodegaFacade getEjbBodegaFacade() {
		return ejbBodegaFacade;
	}


	public void setEjbBodegaFacade(BodegaFacade ejbBodegaFacade) {
		this.ejbBodegaFacade = ejbBodegaFacade;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getNombreBuscar() {
		return nombreBuscar;
	}


	public void setNombreBuscar(String nombreBuscar) {
		this.nombreBuscar = nombreBuscar;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getVisibilidad() {
		return visibilidad;
	}


	public void setVisibilidad(String visibilidad) {
		this.visibilidad = visibilidad;
	}


	public String getNombreCiudad() {
		return nombreCiudad;
	}


	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}


	public String getNonbreProvincia() {
		return nonbreProvincia;
	}


	public void setNonbreProvincia(String nonbreProvincia) {
		this.nonbreProvincia = nonbreProvincia;
	}    
    
    
    
  
}
