package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.modelo.Bodega;
import ec.edu.ups.modelo.Categoria;
import ec.edu.ups.modelo.Producto;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@RequestScoped
public class TablasBodegasControler implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private BodegaFacade ejbBodegaFacade;
	
	@EJB
	private ProductoFacade ejbProductoFacade;
	
	private List<Bodega> bodegas = new ArrayList<Bodega>(); 
	private List<Producto> productos = new ArrayList<Producto>();

	@PostConstruct
	public void listaBodegas() {
		bodegas = ejbBodegaFacade.findAll();
	}
	
	public void listaProductosBodega1() {
		
		for(int i = 0; i < bodegas.size(); i++) {
			
		}	
	}
	
}
