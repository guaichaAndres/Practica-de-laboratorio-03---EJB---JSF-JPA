package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ec.edu.ups.ejb.FacturaCabeceraFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.modelo.FacturaCabecera;
import ec.edu.ups.modelo.FacturaDetalle;

@Named
@RequestScoped
public class BuscarFacturaController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private FacturaCabeceraFacade ejbFacturaCabeceraFacade;
	
	@EJB
	private ProductoFacade ejbProductoFacade;
	
	private String cedula;
	private String id;
	public static List<FacturaCabecera> cabecerass = new ArrayList<FacturaCabecera>();
	public static List<FacturaDetalle> detalless = new ArrayList<FacturaDetalle>();
	
	
	public void buscarPorCedula() {
		cabecerass = new ArrayList<FacturaCabecera>();
		try {
			cabecerass = ejbFacturaCabeceraFacade.buscarPorClienteCedula(cedula);
		} catch (Exception e) {
			System.out.println("no hay cabeceras");
		}
		
	}
	
	public void buscarPorId() {
		cabecerass = new ArrayList<FacturaCabecera>();
		try {
			cabecerass.add(ejbFacturaCabeceraFacade.buscarPorCodigo(Integer.parseInt(id)));
			detalless = new ArrayList<FacturaDetalle>();
			this.setDetalless(cabecerass.get(0).getFacturasDetalle());
		} catch (Exception e) {
			System.out.println("no hay cabeceras");
		}
		
	}
	
	public void mostrarDetalles(FacturaCabecera cabe) {
		detalless = new ArrayList<FacturaDetalle>();
		this.setDetalless(cabe.getFacturasDetalle());
	}
	
	
	public String salir() {
		cabecerass = new ArrayList<FacturaCabecera>();
		detalless = new ArrayList<FacturaDetalle>();
		return "inicioe";
	}
	
	
	public FacturaCabeceraFacade getEjbFacturaCabeceraFacade() {
		return ejbFacturaCabeceraFacade;
	}
	public void setEjbFacturaCabeceraFacade(FacturaCabeceraFacade ejbFacturaCabeceraFacade) {
		this.ejbFacturaCabeceraFacade = ejbFacturaCabeceraFacade;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<FacturaCabecera> getCabecerass() {
		return cabecerass;
	}

	public void setCabecerass(List<FacturaCabecera> cabecerass) {
		BuscarFacturaController.cabecerass = cabecerass;
	}

	public List<FacturaDetalle> getDetalless() {
		return detalless;
	}

	public void setDetalless(List<FacturaDetalle> detalless) {
		BuscarFacturaController.detalless = detalless;
	}

	
	
}
