package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;


@Entity

public class PedidoCabecera implements Serializable {

	
	@JsonbTransient
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonbProperty(nillable = true)
    private int id;

    @JsonbProperty(nillable = true)
    private String estadoActual;
    @JsonbTransient
    private String estadoSiguiente;

    @Transient
    @JsonbProperty(nillable = true)
    private Map<String, Integer> productos;

    @Transient
    @JsonbProperty(nillable = true)
    private String cedula;


    @ManyToOne
    @JoinColumn
    @JsonbTransient
    private Persona persona;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedidoCabecera")
    @JsonbTransient
    private List<PedidoDetalle> pedidoDetalle = new ArrayList<PedidoDetalle>();
	public PedidoCabecera() {
		super();
	}
	
	
	public PedidoCabecera(int id, String estadoActual, String estadoSiguiente, Persona persona) {
		this.setId(id);
		this.setEstadoActual(estadoActual);
		this.setEstadoSiguiente(estadoSiguiente);
		this.setPersona(persona);
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEstadoActual() {
		return estadoActual;
	}

	public void setEstadoActual(String estadoActual) {
		this.estadoActual = estadoActual;
	}

	public String getEstadoSiguiente() {
		return estadoSiguiente;
	}

	public void setEstadoSiguiente(String estadoSiguiente) {
		this.estadoSiguiente = estadoSiguiente;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<PedidoDetalle> getPedidoDetalle() {
		return pedidoDetalle;
	}

	public void setPedidoDetalle(List<PedidoDetalle> pedidoDetalle) {
		this.pedidoDetalle = pedidoDetalle;
	}
	
	public void addPedidoDetalle(PedidoDetalle pedidoDetalle) {
		this.pedidoDetalle.add(pedidoDetalle);
	}


	public Map<String, Integer> getProductos() {
		return productos;
	}


	public void setProductos(Map<String, Integer> productos) {
		this.productos = productos;
	}


	public String getCedula() {
		return cedula;
	}


	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
   
}
