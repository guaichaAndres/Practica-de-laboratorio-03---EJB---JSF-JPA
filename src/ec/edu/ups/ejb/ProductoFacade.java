package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.Producto;

@Stateless
public class ProductoFacade extends AbstractFacade<Producto>{
	@PersistenceContext(unitName = "FacturacionProyecto")
    private EntityManager em;
    
    public ProductoFacade() {
	super(Producto.class);
    }    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
