package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.Provincia;

@Stateless
public class ProvinciaFacade extends AbstractFacade<Provincia>{

	public ProvinciaFacade() {
		super(Provincia.class);
	}

	@PersistenceContext(unitName = "FacturacionProyecto")
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public Provincia buscarProvincia (String nombre) {
		try {
			String jpql = "SELECT prov FROM Provincia prov WHERE prov.nombre='" + nombre + "'";
			Provincia prov = (Provincia) em.createQuery(jpql).getSingleResult();
			return prov;
			
		} catch (Exception e) {
			return null;
		}
	}
	
}
