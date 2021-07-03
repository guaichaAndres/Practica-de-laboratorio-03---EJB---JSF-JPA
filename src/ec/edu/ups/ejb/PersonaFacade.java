package ec.edu.ups.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.Persona;

@Stateless
public class PersonaFacade extends AbstractFacade<Persona>{

	public PersonaFacade() {
		super(Persona.class);
	}

	@PersistenceContext(unitName = "FacturacionProyecto")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public Persona verificarUsuario(String correo, String contrasena) {
		try {
			System.out.println("It is what it is");
			System.out.println("Correo: " + correo + " ,Contrasena: " + contrasena);
			
			String jpql = "SELECT per FROM Persona per WHERE per.correo ='" + correo + "' AND per.contrasena='" + contrasena + "' AND per.estado != 'D'";
			Persona per = (Persona) em.createQuery(jpql).getSingleResult();
			
			System.out.println("Yeah boooy");
			return per;
			
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public Persona buscarCliente(String cedula) {
		try {
			String jpql = "SELECT per FROM Persona per WHERE per.cedula='" + cedula + "' AND per.estado != 'D' AND per.rol='C'";
			Persona per = (Persona) em.createQuery(jpql).getSingleResult();
			return per;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Persona> findCliente(){
		List<Persona> lper = new ArrayList<Persona>();
		try {
			
			String jpql = "SELECT per FROM Persona per WHERE per.rol='C' AND per.estado='H'";

			lper = em.createQuery(jpql).getResultList();
			return lper;
		} catch (Exception e) {
			System.out.println("sale error");
			return lper;
		}
		
	}

	
}
