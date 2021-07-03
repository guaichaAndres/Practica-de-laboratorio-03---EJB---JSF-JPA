package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.PedidoDetalle;

@Stateless
public class PedidoDetalleFacade extends AbstractFacade<PedidoDetalle> {
	
	public PedidoDetalleFacade() {
		super(PedidoDetalle.class);
	}
	
	@PersistenceContext(unitName = "FacturacionProyecto")
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	
	
}
