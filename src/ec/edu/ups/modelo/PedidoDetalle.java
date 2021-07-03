package ec.edu.ups.modelo;

import java.io.Serializable;
import javax.persistence.*;


@Entity

public class PedidoDetalle implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int cantidad;
	
	@ManyToOne
	@JoinColumn
	private Producto producto;
	
	@ManyToOne
	@JoinColumn
	private PedidoCabecera pedidoCabecera;
	
	public PedidoDetalle() {
		super();
	}
	
	public PedidoDetalle(int id, int cantidad, Producto producto, PedidoCabecera pedidoCabecera) {
		this.setId(id);
		this.setCantidad(cantidad);
		this.setProducto(producto);
		this.setPedidoCabecera(pedidoCabecera);
	}
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public PedidoCabecera getPedidoCabecera() {
		return pedidoCabecera;
	}

	public void setPedidoCabecera(PedidoCabecera pedidoCabecera) {
		this.pedidoCabecera = pedidoCabecera;
	}
	
	
   
}
