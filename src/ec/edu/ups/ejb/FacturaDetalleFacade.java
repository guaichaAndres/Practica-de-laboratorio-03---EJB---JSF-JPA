package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.FacturaDetalle;
import ec.edu.ups.modelo.Producto;

@Stateless
public class FacturaDetalleFacade extends AbstractFacade<FacturaDetalle>{

	public FacturaDetalleFacade() {
		super(FacturaDetalle.class);
	}

	@PersistenceContext(unitName = "FacturacionProyecto")
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public void crearDetalle(FacturaDetalle detalle, int producto_id, int facturaCabecera_id) {
		String jpql = "INSERT FacturaDetalle VALUES (0, '" + detalle.getCantidad() + "'," + detalle.getTotal() +
				"," + facturaCabecera_id +","+ producto_id + ")";
	}

	public List<FacturaDetalle> buscarPorCabecera(int facturaCabecera_id) {
		String jpql = "SELECT fd FROM FacturaDetalle fd WHERE fd.cabecera.id=" + facturaCabecera_id;
		List<FacturaDetalle> detalles = em.createQuery(jpql).getResultList();
		return detalles;
	}
	
}
