package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.Bodega;
import ec.edu.ups.modelo.Ciudad;
import ec.edu.ups.modelo.Provincia;

@Stateless
public class CiudadFacade extends AbstractFacade<Ciudad>{

	public CiudadFacade() {
		super(Ciudad.class);
	}
	
	@PersistenceContext(unitName = "FacturacionProyecto")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public Ciudad buscarCiudad(String nombre) {
		try {
			String jpql = "SELECT cd FROM Ciudad cd WHERE cd.nombre='" + nombre + "'";
			Ciudad ciu = (Ciudad) em.createQuery(jpql).getSingleResult();
			return ciu;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Ciudad> buscarCiudadesPorProvincia (Provincia provincia) {
		String jpql = "SELECT cd FROM Ciudad cd WHERE cd.provincia.id=" + provincia.getId();
		List<Ciudad> ciudades = em.createQuery(jpql).getResultList();
		return ciudades;
	}
	
	public Ciudad buscarCiudadesPorBodega (String bodega) {
		try {
			String jpql = "SELECT cd FROM Ciudad cd WHERE cd.bodega.nombre='" + bodega + "'";
			Ciudad ciudad = (Ciudad) em.createQuery(jpql).getSingleResult();
			return ciudad;
		} catch (Exception e) {
			return null;
		}
	}
	
}
