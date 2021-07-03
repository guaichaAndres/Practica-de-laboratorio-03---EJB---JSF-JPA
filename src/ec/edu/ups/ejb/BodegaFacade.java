package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.Bodega;
import ec.edu.ups.modelo.Ciudad;
import ec.edu.ups.modelo.Producto;

@Stateless
public class BodegaFacade extends AbstractFacade<Bodega>{

	public BodegaFacade() {
		super(Bodega.class);
	}
	
	@PersistenceContext(unitName = "FacturacionProyecto")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public Bodega buscarBodega (String nombre) {
		try {
			String jpql = "SELECT b FROM Bodega b WHERE b.nombre='" + nombre + "'";
			Bodega bod = (Bodega) em.createQuery(jpql).getSingleResult();
			return bod;
			
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public List<Bodega> buscarBodegaPorCiudad (Ciudad ciudad) {
		String jpql = "SELECT b FROM Bodega b WHERE b.ciudad.id=" + ciudad.getId();
		List<Bodega> bodegas = em.createQuery(jpql).getResultList();
		return bodegas;
	}
	
	public List<Bodega> buscarBodegaPorProducto (String producto) {
		String jpql = "SELECT b FROM Bodega b WHERE b.producto.nombre='" + producto +"'";
		List<Bodega> bodegas = em.createQuery(jpql).getResultList();
		return bodegas;
	}
	
}
