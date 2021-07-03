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

import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.CategoriaFacade;
import ec.edu.ups.ejb.CiudadFacade;
import ec.edu.ups.ejb.FacturaCabeceraFacade;
import ec.edu.ups.ejb.PersonaFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.ejb.ProvinciaFacade;
import ec.edu.ups.modelo.Bodega;
import ec.edu.ups.modelo.Categoria;
import ec.edu.ups.modelo.FacturaCabecera;
import ec.edu.ups.modelo.FacturaDetalle;
import ec.edu.ups.modelo.Persona;
import ec.edu.ups.modelo.Producto;

@Named
@RequestScoped
public class GenerarFacturaControler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProductoFacade ejbProductoFacade;
	
	@EJB
	private BodegaFacade ejbBodegaFacade;
	
	@EJB
	private CiudadFacade ejbCiudadFacadw;
	
	@EJB
	private ProvinciaFacade ejbProvinciaFacade;
	
	@EJB
	private PersonaFacade ejbPersonaFacade;
	
	@EJB
	private FacturaCabeceraFacade ejbFacturaCabecera;
	
	
	private List<Bodega> bodegas = new ArrayList<Bodega>();
	
	
	private List<String> listBodega = new ArrayList<String>();
	private String bodega="";
	
	public static List<Producto> productosEscoger = new ArrayList<Producto>();
	public static List<FacturaDetalle> detalle = new ArrayList<FacturaDetalle>();
	private Persona persona = new Persona();
	public static FacturaCabecera cabecera = new FacturaCabecera();
	private String nombre="";
	private String apellido="";
	private String cedula="";
    private String direccion="";
    private String telefono="";
    private String correo="";
    private String productoBuscar="";
	
	@PostConstruct
	public void datos() {
		bodegas = ejbBodegaFacade.findAll();
		
		//productosEscoger = ejbProductoFacade.findAll();
		stringBodega(bodegas);
		
		bodega = listBodega.get(0);
	}
	
	public void filtrar() {
		try {
			productosEscoger = new ArrayList<Producto>();
			if(productoBuscar.equals("")) {
				productosEscoger = ejbBodegaFacade.buscarBodega(bodega).getProductos();
			}else {
				List<Producto> pr = ejbProductoFacade.buscarProductoPorNombre(productoBuscar);
				productosEscoger = new ArrayList<Producto>();
				for(int i=0; i<pr.size(); i++) {
					if(pr.get(i).getBodegas().get(0).getNombre().equals(bodega)) {
						productosEscoger.add(pr.get(i));
					}
				}
				
			}
		} catch (Exception e) {
			System.out.println("No se pudo encontrar el producto por alguna razon ajena a mi entendimiento actual xd");
		}
			
		
	}
	
	public void buscarPersona() {
		persona = ejbPersonaFacade.buscarCliente(cedula);
		this.setCedula(persona.getCedula());
		this.setNombre(persona.getNombre());
		this.setApellido(persona.getApellido());
		this.setDireccion(persona.getDireccion());
		this.setCorreo(persona.getCorreo());
		this.setTelefono(persona.getTelefono());
	}
	
	
	public void agregarDetalle(Producto productoDetalle) {
		try {
			buscarPersona();
			datos();
			
			
			DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
			separadoresPersonalizados.setDecimalSeparator('.');
			DecimalFormat formato1 = new DecimalFormat("#.00", separadoresPersonalizados);
			
			
			Producto producto = ejbProductoFacade.find(productoDetalle.getId());
			if(producto.getStock()>=productoDetalle.getStock()) {
				FacturaDetalle det = new FacturaDetalle(0, productoDetalle.getStock(), producto.getPrecio()*productoDetalle.getStock(), cabecera, producto);
				cabecera.setSubtotal(cabecera.getSubtotal()+producto.getPrecio()*productoDetalle.getStock());
				cabecera.setIva(Float.parseFloat(formato1.format(cabecera.getSubtotal()*(float)0.12)));
				cabecera.setTotal(cabecera.getIva()+cabecera.getSubtotal());
				detalle.add(det);
			}else {
				System.out.println("no entra a la condicion");
			}
		} catch (Exception e) {
			System.out.println("Falta introducir un cliente");
		}
			
		
	}
	
	public String salir() {
		detalle = new ArrayList<FacturaDetalle>();
		cabecera = new FacturaCabecera();
		productosEscoger = new ArrayList<Producto>();
		return "inicioe";
	}
	
	public String enviarFactura() {
		
		try {
			persona = ejbPersonaFacade.buscarCliente(cedula);
			
			cabecera.setId(0);
			cabecera.setFecha(new Date());
			cabecera.setEstado('H');
			cabecera.setPersona(persona);
			cabecera.setFacturasDetalle(detalle);
			
			for(int i=0; i<detalle.size(); i++) {
				Producto produActualizar = ejbProductoFacade.find(detalle.get(i).getProducto().getId());
				produActualizar.setStock(produActualizar.getStock()-detalle.get(i).getCantidad());
				ejbProductoFacade.edit(produActualizar);
			}
			
			ejbFacturaCabecera.create(cabecera);
			

			detalle = new ArrayList<FacturaDetalle>();
			cabecera = new FacturaCabecera();
			productosEscoger = new ArrayList<Producto>();
			
			return "inicioe";
			
		} catch (Exception e) {
			detalle = new ArrayList<FacturaDetalle>();
			cabecera = new FacturaCabecera();
			return "generarf";
		}
		
	}
	
	
	private void stringBodega(List<Bodega> bod) {
		listBodega = new ArrayList<String>();
		for(int i=0; i<bod.size(); i++) {
			listBodega.add(bod.get(i).getNombre());
		}
	}
	
	

	public List<Bodega> getBodegas() {
		return bodegas;
	}

	public void setBodegas(List<Bodega> bodegas) {
		this.bodegas = bodegas;
	}

	public List<String> getListBodega() {
		return listBodega;
	}

	public void setListBodega(List<String> listBodega) {
		this.listBodega = listBodega;
	}

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Producto> getProductosEscoger() {
		return productosEscoger;
	}

	public void setProductosEscoger(List<Producto> productosEscoger) {
		this.productosEscoger = productosEscoger;
	}

	public List<FacturaDetalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<FacturaDetalle> detalle) {
		this.detalle = detalle;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public FacturaCabecera getCabecera() {
		return cabecera;
	}

	public void setCabecera(FacturaCabecera cabecera) {
		this.cabecera = cabecera;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getProductoBuscar() {
		return productoBuscar;
	}

	public void setProductoBuscar(String productoBuscar) {
		this.productoBuscar = productoBuscar;
	}
	
	
	
	
	
	
	
	
	
}
