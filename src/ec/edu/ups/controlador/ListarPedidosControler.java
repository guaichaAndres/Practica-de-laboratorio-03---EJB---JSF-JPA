package ec.edu.ups.controlador;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ec.edu.ups.ejb.FacturaCabeceraFacade;
import ec.edu.ups.ejb.PedidoCabeceraFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.modelo.FacturaCabecera;
import ec.edu.ups.modelo.FacturaDetalle;
import ec.edu.ups.modelo.PedidoCabecera;
import ec.edu.ups.modelo.PedidoDetalle;
import ec.edu.ups.modelo.Producto;

@Named
@RequestScoped
public class ListarPedidosControler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PedidoCabeceraFacade ejbPedidoCabeceraFacade;
	
	@EJB
	private ProductoFacade ejbProductoFavade;
	
	@EJB
	private FacturaCabeceraFacade ejbFacturaCabeceraFacade;
	
	@EJB
	private ProductoFacade ejbProductoFacade;
	
	private String cedula;
	private List<PedidoCabecera> pedidoCabecera = new ArrayList<PedidoCabecera>();
	private List<PedidoDetalle> pedidoDetalle = new ArrayList<PedidoDetalle>();
	
	@PostConstruct
	public void constructor() {
		pedidoCabecera = ejbPedidoCabeceraFacade.findAll();
	}
	
	public void buscarPorCedula() {
		pedidoCabecera = new ArrayList<PedidoCabecera>();
		try {
			pedidoCabecera = ejbPedidoCabeceraFacade.buscarPorClienteCedula(cedula);
			
			
		} catch (Exception e) {
			pedidoCabecera = ejbPedidoCabeceraFacade.findAll();
			System.out.println("no hay cabeceras");
		}
		
	}
	
	public void mostrarDetalles(PedidoCabecera pedidoCabecera) {
		pedidoDetalle = new ArrayList<PedidoDetalle>();
		this.setPedidoDetalle(pedidoCabecera.getPedidoDetalle());
	}
	
	public String cambiarEstado(PedidoCabecera pedido) {
		System.out.println("llega: " + pedido.getId());
		try {
			switch (pedido.getEstadoActual()) {
			case "Enviado":
				generarFactura(pedido);
				pedido.setEstadoActual(pedido.getEstadoSiguiente());
				pedido.setEstadoSiguiente("Receptado");
				break;
			case "Receptado":
				pedido.setEstadoActual(pedido.getEstadoSiguiente());
				pedido.setEstadoSiguiente("En proceso");
				break;
			case "En proceso":
				pedido.setEstadoActual(pedido.getEstadoSiguiente());
				pedido.setEstadoSiguiente("En camino");
				break;
			case "En camino":
				pedido.setEstadoActual(pedido.getEstadoSiguiente());
				pedido.setEstadoSiguiente("Finalizado");
				break;
			case "Finalizado":
				break;
			default:
				break;
			}
			
			ejbPedidoCabeceraFacade.edit(pedido);
			
			pedidoCabecera = ejbPedidoCabeceraFacade.findAll();
			
			return "listarped";
		} catch (Exception e) {
			pedidoCabecera = new ArrayList<PedidoCabecera>();
			pedidoDetalle = new ArrayList<PedidoDetalle>();
			return "inicio";
		}
	}
	
	
	
	private void generarFactura(PedidoCabecera pedido) {
		FacturaCabecera fcabecera = new FacturaCabecera();
		
		List<FacturaDetalle> fdetalles = new ArrayList<FacturaDetalle>();
		
		fcabecera.setId(0);
		fcabecera.setFecha(new Date());
		fcabecera.setEstado('H');
		fcabecera.setPersona(pedido.getPersona());
		fcabecera.setSubtotal(0);
		fcabecera.setId(0);
		fcabecera.setTotal(0);
		
		DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
		separadoresPersonalizados.setDecimalSeparator('.');
		DecimalFormat formato1 = new DecimalFormat("#.00", separadoresPersonalizados);

		for(int i=0; i<pedido.getPedidoDetalle().size(); i++) {
			FacturaDetalle det = new FacturaDetalle(0, pedido.getPedidoDetalle().get(i).getCantidad(), 
					pedido.getPedidoDetalle().get(i).getCantidad()*pedido.getPedidoDetalle().get(i).getProducto().getPrecio(), 
					fcabecera, pedido.getPedidoDetalle().get(i).getProducto());

			fcabecera.setSubtotal(fcabecera.getSubtotal()+pedido.getPedidoDetalle().get(i).getCantidad()*pedido.getPedidoDetalle().get(i).getProducto().getPrecio());
			fcabecera.setIva(Float.parseFloat(formato1.format(fcabecera.getSubtotal()*(float)0.12)));
			fcabecera.setTotal(Float.parseFloat(formato1.format(fcabecera.getIva()+fcabecera.getSubtotal())));
			fdetalles.add(det);
			//Actualizar stock
			
			Producto produActualizar = ejbProductoFacade.find(pedido.getPedidoDetalle().get(i).getProducto().getId());
			produActualizar.setStock(produActualizar.getStock()-pedido.getPedidoDetalle().get(i).getCantidad());
			ejbProductoFacade.edit(produActualizar);
		}

		fcabecera.setFacturasDetalle(fdetalles);
		
		ejbFacturaCabeceraFacade.create(fcabecera);
		
	}
	
	
	
	public String salir() {
		pedidoCabecera = new ArrayList<PedidoCabecera>();
		pedidoDetalle = new ArrayList<PedidoDetalle>();
		return "inicio";
	}
	
	
	public PedidoCabeceraFacade getEjbPedidoCabeceraFacade() {
		return ejbPedidoCabeceraFacade;
	}
	public void setEjbPedidoCabeceraFacade(PedidoCabeceraFacade ejbPedidoCabeceraFacade) {
		this.ejbPedidoCabeceraFacade = ejbPedidoCabeceraFacade;
	}
	public ProductoFacade getEjbProductoFavade() {
		return ejbProductoFavade;
	}
	public void setEjbProductoFavade(ProductoFacade ejbProductoFavade) {
		this.ejbProductoFavade = ejbProductoFavade;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public List<PedidoCabecera> getPedidoCabecera() {
		return pedidoCabecera;
	}
	public void setPedidoCabecera(List<PedidoCabecera> pedidoCabecera) {
		this.pedidoCabecera = pedidoCabecera;
	}
	public List<PedidoDetalle> getPedidoDetalle() {
		return pedidoDetalle;
	}
	public void setPedidoDetalle(List<PedidoDetalle> pedidoDetalle) {
		this.pedidoDetalle = pedidoDetalle;
	}
	
	
}
