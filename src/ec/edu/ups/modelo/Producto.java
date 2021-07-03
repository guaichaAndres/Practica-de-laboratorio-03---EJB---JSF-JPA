package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;


@Entity
public class Producto implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private float precio;
	private int stock;
	private char estado;
	
	@Transient
    @JsonbProperty("bodega")
    private String bodegaNombre;
    @Transient
    @JsonbProperty("categoria")
    private String categoriaNombre;
	
	@ManyToOne
	@JoinColumn
	@JsonbTransient
	private Categoria categoria;
	
	@ManyToMany(mappedBy = "productos")
	@JsonbTransient
	private List<Bodega> bodegas;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
	@JsonbTransient
    private List<FacturaDetalle> facturaDetalles;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
	@JsonbTransient
    private List<PedidoDetalle> pedidoDetalle;
	
	public Producto(int id, String nombre, float precio, int stock, char estado, Categoria categoria) {
		this.setId(id);
		this.setNombre(nombre);
		this.setPrecio(precio);
		this.setStock(stock);
		this.setEstado(estado);
		this.setCategoria(categoria);
		
		bodegas = new ArrayList<Bodega>();
	}
	
	public Producto() {
		super();
	}
   
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public char getEstado() {
		return estado;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Bodega> getBodegas() {
		return bodegas;
	}

	public void setBodegas(List<Bodega> bodegas) {
		this.bodegas = bodegas;
	}
	
	public void addBodegas(Bodega bodegas) {
		this.bodegas.add(bodegas);
	}

	public List<FacturaDetalle> getFacturaDetalles() {
		return facturaDetalles;
	}

	public void setFacturaDetalles(List<FacturaDetalle> facturaDetalles) {
		this.facturaDetalles = facturaDetalles;
	}
	
	public void addFacturaDetalles(FacturaDetalle facturaDetalles) {
		this.facturaDetalles.add(facturaDetalles);
	}
	
	
	
	public List<PedidoDetalle> getPedidoDetalle() {
		return pedidoDetalle;
	}

	public void setPedidoDetalle(List<PedidoDetalle> pedidoDetalle) {
		this.pedidoDetalle = pedidoDetalle;
	}
	
	public void addPedidoDdetalle(PedidoDetalle pedidoDetalle) {
		this.pedidoDetalle.add(pedidoDetalle);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
		    return true;
		if (obj == null)
		    return false;
		if (getClass() != obj.getClass())
		    return false;
		Producto other = (Producto) obj;
		if (id != other.id)
		    return false;
		return true;
	}

	public String getBodegaNombre() {
		return bodegaNombre;
	}

	public void setBodegaNombre(String bodegaNombre) {
		this.bodegaNombre = bodegaNombre;
	}

	public String getCategoriaNombre() {
		return categoriaNombre;
	}

	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
	}
}
