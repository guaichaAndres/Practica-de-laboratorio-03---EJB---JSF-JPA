package ec.edu.ups.controlador;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter({"/AgregarBodega.jsf","/AgregarCliente.jsf","/AgregarProductoPorBodega.jsf","/AnularFactura.jsf","/BuscarFactura.jsf","/EditarBodega",
	"/EditarCliente.jsf","/EliminarCliente.jsf","/GenerarFactura.jsf","/GestionProductoPorBodega.jsf","/InicioAdministrador.jsf","/InicioEmpleado.jsf",
	"/InventarioGeneral.jsf","/ListadoBodegas.jsf"})
public class Filtro implements Filter {

    public Filtro() {
    	
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*
		HttpSession session = ((HttpServletRequest) request).getSession();
				
		if (session.getAttribute("accesos") == null) {
			((HttpServletResponse) response).sendRedirect("/PracticaDeLaboratorio03EJB-JSF-JPA/login.jsf");
		} else {
			chain.doFilter(request, response);
		}
		*/
		try {

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();
			if (ses.getAttribute("accesos") != null)
				chain.doFilter(request, response);
			else
				resp.sendRedirect(reqt.getContextPath() + "/inicio.jsf");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
