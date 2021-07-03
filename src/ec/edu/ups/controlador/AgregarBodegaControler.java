package ec.edu.ups.controlador;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.CiudadFacade;
import ec.edu.ups.ejb.ProvinciaFacade;
import ec.edu.ups.modelo.Bodega;
import ec.edu.ups.modelo.Ciudad;
import ec.edu.ups.modelo.Provincia;


@Named
@RequestScoped
public class AgregarBodegaControler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProvinciaFacade ejbProvinciaFacade;
	
	@EJB
	private CiudadFacade ejbCiudadFacade;
	
	@EJB
	private BodegaFacade ejbBodegaFacade;
	
	
	private String provincian="";
	private String ciudadn="";
	private String bodegan="";
    private String direccion="";
	
    
    public String agregarBodega() {
    	System.out.println("entra 1");
    	Bodega bodegaComprobar = null;
    	Provincia provinciaComprobar = null;
    	Ciudad ciudadComprobar = null;
    	
    	try {
    		
    		bodegaComprobar = ejbBodegaFacade.buscarBodega(bodegan);
    		if(bodegaComprobar==null) {
    			
    			provinciaComprobar = ejbProvinciaFacade.buscarProvincia(provincian);
    			ciudadComprobar = ejbCiudadFacade.buscarCiudad(ciudadn);
    			
    			if(ciudadComprobar!=null && provinciaComprobar!=null) {
        			Bodega bod = new Bodega(0, bodegan, direccion, ciudadComprobar);
        			ejbBodegaFacade.create(bod);
        			return "inicioa";
        			
        		}
    			
    		if(ciudadComprobar==null && provinciaComprobar!=null ) {
    			Ciudad ciu = new Ciudad(0, ciudadn, provinciaComprobar);
    			ejbCiudadFacade.create(ciu);
    			Bodega bod = new Bodega(0, bodegan, direccion, ciu);
    			ejbBodegaFacade.create(bod);
    			return "inicioa";
    			
    		}
    		
    		if(ciudadComprobar==null && provinciaComprobar==null) {
    			Provincia prov = new Provincia(0, provincian);
    			ejbProvinciaFacade.create(prov);
    			Ciudad ciu = new Ciudad(0, ciudadn, prov);
    			ejbCiudadFacade.create(ciu);
    			Bodega bod = new Bodega(0, bodegan, direccion, ciu);
    			ejbBodegaFacade.create(bod);
    			return "inicioa";
    		}
    		
    		
    		
    		}else {
    			return "agregarb";
    		}
    		
    	} catch (Exception e) {
			return "agregarb";
		}
		return "agregarb";
    	
    }
    
    
    public String salir() {
    	System.out.println("entra salir");
    	return "inicioa";
    }

	public ProvinciaFacade getEjbProvinciaFacade() {
		return ejbProvinciaFacade;
	}

	public void setEjbProvinciaFacade(ProvinciaFacade ejbProvinciaFacade) {
		this.ejbProvinciaFacade = ejbProvinciaFacade;
	}



	public BodegaFacade getEjbBodegaFacade() {
		return ejbBodegaFacade;
	}

	public void setEjbBodegaFacade(BodegaFacade ejbBodegaFacade) {
		this.ejbBodegaFacade = ejbBodegaFacade;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getProvincian() {
		return provincian;
	}

	public void setProvincian(String provincian) {
		this.provincian = provincian;
	}

	public String getCiudadn() {
		return ciudadn;
	}

	public void setCiudadn(String ciudadn) {
		this.ciudadn = ciudadn;
	}

	public String getBodegan() {
		return bodegan;
	}

	public void setBodegan(String bodegan) {
		this.bodegan = bodegan;
	}

	public CiudadFacade getEjbCiudadFacade() {
		return ejbCiudadFacade;
	}

	public void setEjbCiudadFacade(CiudadFacade ejbCiudadFacade) {
		this.ejbCiudadFacade = ejbCiudadFacade;
	}
	
	
	
}
