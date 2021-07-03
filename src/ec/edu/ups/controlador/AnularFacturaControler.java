package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ec.edu.ups.ejb.FacturaCabeceraFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.modelo.FacturaCabecera;
import ec.edu.ups.modelo.FacturaDetalle;
import ec.edu.ups.modelo.Producto;

@Named
@RequestScoped
public class AnularFacturaControler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private FacturaCabeceraFacade ejbFacturaCabeceraFacade;
	
	@EJB
	private ProductoFacade ejbProductoFavade;
	
	private String cedula;
	private String id;
	public static List<FacturaCabecera> cabeceras;
	public static List<FacturaDetalle> detalles;
	
	
	public void buscarPorCedula() {
		cabeceras = new ArrayList<FacturaCabecera>();
		try {
			cabeceras = ejbFacturaCabeceraFacade.buscarPorClienteCedula(cedula);
		} catch (Exception e) {
			System.out.println("no hay cabeceras");
		}
		
	}
	
	public void buscarPorId() {
		cabeceras = new ArrayList<FacturaCabecera>();
		try {
			cabeceras.add(ejbFacturaCabeceraFacade.buscarPorCodigo(Integer.parseInt(id)));
			detalles = new ArrayList<FacturaDetalle>();
			this.setDetalles(cabeceras.get(0).getFacturasDetalle());
		} catch (Exception e) {
			System.out.println("no hay cabeceras");
		}
		
	}
	
	public void mostrarDetalles(FacturaCabecera cabe) {
		detalles = new ArrayList<FacturaDetalle>();
		this.setDetalles(cabe.getFacturasDetalle());
	}
	
	public String anularFactura(FacturaCabecera cabe) {
		try {
			cabe.setEstado('A');
			ejbFacturaCabeceraFacade.edit(cabe);
			cabeceras = new ArrayList<FacturaCabecera>();
			detalles = new ArrayList<FacturaDetalle>();
			return "inicioe";
		} catch (Exception e) {
			cabeceras = new ArrayList<FacturaCabecera>();
			detalles = new ArrayList<FacturaDetalle>();
			return "anularf";
		}
	}
	
	public String salir() {
		cabeceras = new ArrayList<FacturaCabecera>();
		detalles = new ArrayList<FacturaDetalle>();
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

	public List<FacturaCabecera> getCabeceras() {
		return cabeceras;
	}

	public void setCabeceras(List<FacturaCabecera> cabeceras) {
		AnularFacturaControler.cabeceras = cabeceras;
	}

	public List<FacturaDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<FacturaDetalle> detalles) {
		AnularFacturaControler.detalles = detalles;
	}

	

	
	
	
}
