package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.CategoriaFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.modelo.Bodega;
import ec.edu.ups.modelo.Categoria;
import ec.edu.ups.modelo.Persona;
import ec.edu.ups.modelo.Producto;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@RequestScoped
public class AgregarProductoPorBodegaControler implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProductoFacade ejbProductoFacade;
	
	@EJB
	private CategoriaFacade ejbCategoriaFacade;
	
	@EJB
	private BodegaFacade ejbBodegaFacade;
	
	public static List<Producto> productos = new ArrayList<Producto>();
	private List<String> opciones = new ArrayList<String>();
	
//	private List<Categoria> Categorias = new ArrayList<Categoria>();
	private List<String> opcionesCategorias = new ArrayList<String>();
	private List<String> opcionesBodegas = new ArrayList<String>();
	
//	private List<String> test = new ArrayList<String>();
	
	private String opcion = "";
	private String opcionCategoria = "";
	private String opcionBodega = "";
	
	private Producto producto = null;
	
	private String nombreA="";
	private String precioA="";
	private String stockA="";
	
	private Bodega bode = null;
	
	@PostConstruct
	public void constructor() {
		
		for(int i=0; i<ejbCategoriaFacade.findAll().size(); i++) {
			opcionesCategorias.add(ejbCategoriaFacade.findAll().get(i).getNombre());
		}
		
		for(int i=0; i<ejbBodegaFacade.findAll().size(); i++) {
			opcionesBodegas.add(ejbBodegaFacade.findAll().get(i).getNombre());
		}
		
	}
	
	
	public String agregar() {
		
		System.out.println("NOMBRE : " + nombreA);
		System.out.println("PRECIO : " + precioA);
		System.out.println("STOCK  : " + stockA);
		
		Producto comprobar = null;
		try {
				System.out.println("VER CATEGORIA ELEGIDA : " + opcionCategoria);
				
				Categoria cat = ejbCategoriaFacade.obtenerCategoria(opcionCategoria);
				int stockP  = Integer.parseInt(stockA);   
				float precioP = Float.parseFloat( precioA);  
				
				Producto pro = new Producto(0, nombreA, precioP, stockP, 'H', cat);
				ejbProductoFacade.create(pro);
				
				System.out.println("VER BODEGA ELEGIDA : " + opcionBodega);
				Bodega bode = new Bodega();
				bode = ejbBodegaFacade.buscarBodega(opcionBodega);
				bode.addProductos(pro);
				
				ejbBodegaFacade.edit(bode);
				
				return "inicioa";
				
			
		} catch (Exception e) {
			System.out.println("error en agregar producto");
			return "agregarP";
		}
		
	}
	
	
	
	public String salir() {
		return "inicioa";
	}
	
	
	public List<Producto> getProductos() {
		return productos;
	}


	public void setProductos(List<Producto> productos) {
		InicioPrincipalControler.productos = productos;
	}


	public List<String> getOpciones() {
		return opciones;
	}


	public void setOpciones(List<String> opciones) {
		this.opciones = opciones;
	}


	public String getOpcion() {
		return opcion;
	}


	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}


	public ProductoFacade getEjbProductoFacade() {
		return ejbProductoFacade;
	}


	public void setEjbProductoFacade(ProductoFacade ejbProductoFacade) {
		this.ejbProductoFacade = ejbProductoFacade;
	}


	public CategoriaFacade getEjbCategoriaFacade() {
		return ejbCategoriaFacade;
	}


	public void setEjbCategoriaFacade(CategoriaFacade ejbCategoriaFacade) {
		this.ejbCategoriaFacade = ejbCategoriaFacade;
	}


	public BodegaFacade getEjbBodegaFacade() {
		return ejbBodegaFacade;
	}


	public void setEjbBodegaFacade(BodegaFacade ejbBodegaFacade) {
		this.ejbBodegaFacade = ejbBodegaFacade;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String getOpcionCategoria() {
		return opcionCategoria;
	}


	public void setOpcionCategoria(String opcionCategoria) {
		this.opcionCategoria = opcionCategoria;
	}


	public String getNombreA() {
		return nombreA;
	}


	public void setNombreA(String nombreA) {
		this.nombreA = nombreA;
	}


	public String getPrecioA() {
		return precioA;
	}


	public void setPrecioA(String precioA) {
		this.precioA = precioA;
	}


	public String getStockA() {
		return stockA;
	}


	public void setStockA(String stockA) {
		this.stockA = stockA;
	}


	public List<String> getOpcionesCategorias() {
		return opcionesCategorias;
	}


	public void setOpcionesCategorias(List<String> opcionesCategorias) {
		this.opcionesCategorias = opcionesCategorias;
	}


	public Bodega getBode() {
		return bode;
	}


	public void setBode(Bodega bode) {
		this.bode = bode;
	}


	public List<String> getOpcionesBodegas() {
		return opcionesBodegas;
	}


	public void setOpcionesBodegas(List<String> opcionesBodegas) {
		this.opcionesBodegas = opcionesBodegas;
	}


	public String getOpcionBodega() {
		return opcionBodega;
	}


	public void setOpcionBodega(String opcionBodega) {
		this.opcionBodega = opcionBodega;
	}

	
	
}

