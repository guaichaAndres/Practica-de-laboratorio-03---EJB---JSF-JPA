package ec.edu.ups.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ejb.EJB;

import ec.edu.ups.modelo.Categoria;
import ec.edu.ups.modelo.PedidoCabecera;
import ec.edu.ups.modelo.PedidoDetalle;
import ec.edu.ups.modelo.Persona;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.CategoriaFacade;
import ec.edu.ups.ejb.CiudadFacade;
import ec.edu.ups.ejb.FacturaCabeceraFacade;
import ec.edu.ups.ejb.FacturaDetalleFacade;
import ec.edu.ups.ejb.PedidoCabeceraFacade;
import ec.edu.ups.ejb.PersonaFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.ejb.ProvinciaFacade;

@Path("/pedidos/")
public class GestionPedidos {
	
	@EJB
    PersonaFacade personaFacade;
	
	@EJB
	CategoriaFacade categoriaFacade;
	
	@EJB
	FacturaCabeceraFacade cabeceraFacade;
	
	@EJB
	FacturaDetalleFacade detalleFacade;
	
	@EJB
	ProductoFacade productoFacade;
	
	@EJB
	BodegaFacade bodegaFacade;
	
	@EJB
	CiudadFacade ciudadFacadw;
	
	@EJB
	ProvinciaFacade provinciaFacade;
	
	@EJB
	PedidoCabeceraFacade pedidoCabeceraFacade;
	
	private PedidoCabecera pedidoCabecera;
	
	

	
	@GET
    @Path("/productos/{bodega}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProductos(@PathParam("bodega") String bodega) {
        System.out.println(bodega);
        try {
            List<Categoria> cat = categoriaFacade.categoriasOrdenadas();
            List<Producto> pro = new ArrayList<Producto>();

            Jsonb jsonb = JsonbBuilder.create();

            for(int i=0; i<cat.size(); i++) {
                for(int j=0; j<cat.get(i).getProductos().size(); j++) {
                    if(cat.get(i).getProductos().get(j).getBodegas().get(0).getNombre().equals(bodega)) {
                        Producto prod = cat.get(i).getProductos().get(j);
                        prod.setBodegaNombre(prod.getBodegas().get(0).getNombre());
                        prod.setCategoriaNombre(prod.getCategoria().getNombre());
                        pro.add(prod);
                    }
                }
            }

            if(pro.size()!=0) {
                return Response.ok(jsonb.toJson(pro))
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
            }else {
                return Response.status(404).entity("La bodega mensionada no cuenta con productos").build();
            }


        } catch (Exception e) {
            return Response.status(404).entity("Bodega no encontrada").build();
        }
	
	}
	
	
	
	@GET
    @Path("/listaPedidos/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response estadoPedidos(@PathParam("cedula") String cedula) {
        try {
            List<PedidoCabecera> pc;
            Persona cli = personaFacade.buscarCliente(cedula);

            Jsonb jsonb = JsonbBuilder.create();

            pc = cli.getPedidosCab();

            if(pc.size()!=0) {

                for(int i=0; i<pc.size(); i++) {
                    Map<String, Integer> productos = new HashMap<String, Integer>();
                    for(int j=0; j<pc.get(i).getPedidoDetalle().size(); j++) {
                        String producto = pc.get(i).getPedidoDetalle().get(j).getProducto().getNombre();
                        int cantidad = pc.get(i).getPedidoDetalle().get(j).getCantidad();
                        productos.put(producto, cantidad);
                    }
                    pc.get(i).setProductos(productos);
                    pc.get(i).setCedula(cedula);
                }

                return Response.ok(jsonb.toJson(pc)).build();
            }else {
                return Response.status(404).entity("El cliente no ha realizado pedidos").build();
            }


        } catch (Exception e) {
            return Response.status(404).entity("Cliente no encontrado").build();
        }


    }
	
	
	
	
	@PUT
    @Path("/pedir")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pedir(String jsonPedido){
        try {
            Jsonb jsonb = JsonbBuilder.create();
            PedidoCabecera pc = jsonb.fromJson(jsonPedido, PedidoCabecera.class);
            List<PedidoDetalle> pd = new ArrayList<PedidoDetalle>();
            Iterator it = pc.getProductos().keySet().iterator();
            Persona persona = personaFacade.buscarCliente(pc.getCedula());
            if(persona==null) {
                return Response.status(404).entity("No se encontro una persona con esta cedula: " + pc.getCedula()).build();
            }
            pc.setEstadoSiguiente("Receptado");
            pc.setPersona(persona);
            while(it.hasNext()) {
                String nombre = (String) it.next();
                int cantidad = pc.getProductos().get(nombre);
                List<Producto> prods = productoFacade.buscarProductoPorNombre(nombre);
                if(prods.size()!=0) {
                    PedidoDetalle detalle = new PedidoDetalle(0, cantidad, prods.get(0), pc);
                    pd.add(detalle);
                }else {
                    return Response.status(404).entity("El producto " + nombre + " no se encuentra en nuestra base de datos").build();
                }

            }
            pc.setPedidoDetalle(pd);
            pedidoCabeceraFacade.create(pc);
            return Response.status(404).entity("Se ha registrado el pedido").build();
        } catch (Exception e) {
            return Response.status(404).entity("No se pudo encontrar un producto").build();
        }
    }
	
	
	
	
	
	
	
	
}
