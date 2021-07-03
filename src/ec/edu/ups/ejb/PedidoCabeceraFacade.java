package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.PedidoCabecera;
import ec.edu.ups.modelo.Persona;

@Stateless
public class PedidoCabeceraFacade extends AbstractFacade<PedidoCabecera> {
	
	public PedidoCabeceraFacade() {
		super(PedidoCabecera.class);
	}
	
	@PersistenceContext(unitName = "FacturacionProyecto")
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public List<PedidoCabecera> buscarPorClienteCedula(String cedula) {
		try {
			String jpql = "SELECT pc FROM PedidoCabecera pc WHERE pc.persona.cedula='" + cedula + "'";
			List<PedidoCabecera> pedidoCabecera = em.createQuery(jpql).getResultList();
			return pedidoCabecera;
		} catch (Exception e) {
			return null;
		}
			
	}

}
