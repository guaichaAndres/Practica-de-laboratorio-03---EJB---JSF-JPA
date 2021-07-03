package ec.edu.ups.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import ec.edu.ups.modelo.Persona;
import ec.edu.ups.ejb.PersonaFacade;

@Path("/cliente/")
public class PersonResource {
	
	@EJB
    PersonaFacade personaFacade;

	
	private Persona persona;
	
	
	@POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(@FormParam("correo") String correo, @FormParam("contraseña") String contrasena) throws IOException {
		persona = new Persona();
		persona = personaFacade.verificarUsuario(correo, contrasena);
		
		if(persona != null) {
			System.out.println("usuario encontrado");
			return Response.ok("Inicio de Sesion Correcto").build();
			
		}else {
			return Response.status(404).entity("Usuario no encontrado").build();
			
		}
		
    }
	
	
	@POST
    @Path("/registrar/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response register(@FormParam("cedula") String cedula, @FormParam("correo") String correo, @FormParam("contraseña") String contrasena){
		persona = new Persona();
        persona = personaFacade.buscarCliente(cedula);
        
        if(persona != null){
            persona.setCorreo(correo);
            persona.setContrasena(contrasena);
            persona.setEstado('H');
            try{
                personaFacade.edit(persona);
                return Response.ok("Usuario Registrado").build();
                
            }catch (Exception e){
                return Response.status(500).entity("Error al registrar usuario " + e).build();
            }
        }else{
            return Response.status(404).entity("Usuario no encontrado").build();
        }
    }
	
	
	@PUT
    @Path("/modificar/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(@FormParam("cedula") String cedula, @FormParam("nombre") String nombre,
                           @FormParam("apellido") String apellido, @FormParam("direccion") String direccion, @FormParam("telefono") String telefono,
                           @FormParam("correo") String correo, @FormParam("contraseña") String contrasena){

		persona = new Persona();
        persona = personaFacade.buscarCliente(cedula);
        
		if(persona != null) {
			Persona persona2 = new Persona();
	        persona2 = personaFacade.buscarCliente(cedula);
	        persona2.setNombre(nombre);
	        persona2.setApellido(apellido);
	        persona2.setTelefono(telefono);
	        persona2.setDireccion(direccion);
	        persona2.setCorreo(correo);
	        persona2.setContrasena(contrasena);
	        
			try{
                personaFacade.edit(persona2);
                return Response.ok("Usuario Modificado").build();
                
            }catch (Exception e){
                return Response.status(500).entity("Error al modificar usuario " + e).build();
            }
			
		}else{
            return Response.status(404).entity("Usuario no encontrado").build();
        }
    }
	
	
	
	@PUT
    @Path("/anular/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response anular(@FormParam("cedula") String cedula){
		
		persona = new Persona();
        persona = personaFacade.buscarCliente(cedula);
        
        if(persona != null){
            try{
            	Persona per = new Persona();
            	per = personaFacade.buscarCliente(cedula);
            	per.setEstado('D');
                personaFacade.edit(per);
                return Response.ok("Usuario Anulado").build();
                
            }catch (Exception e){
                return Response.status(500).entity("Error al anular usuario " + e).build();
            }
        }else{
            return Response.status(404).entity("Usuario no encontrado").build();
        }
		
    }
	
	
	
	
	
	
	
	
    // Ejemplo con JSON

    @GET
    @Path("/login/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Integer id) {
    	
    	System.out.println("ver id + " + id);
	Jsonb jsonb = JsonbBuilder.create();
	Persona person = new Persona(id, "Juan", "Mendez", "098654654", "Av Loja", "0980644260", "tes@email.com", "1234", 'A', 'B');
	return Response.ok(jsonb.toJson(person)).build();
    }

    
    
    
    
    
    
    /*
    

    @PUT
    @Path("/put")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJSON(String jsonPerson) {
	Jsonb jsonb = JsonbBuilder.create();
	Person person = jsonb.fromJson(jsonPerson, Person.class);

	System.out.println("REST/client:putJSON-->" + person);
	return Response.status(204).entity("Usuario actualizaado..." + person).build();
    }

    @DELETE
    @Path("delete/{id}")
    public Response delete(@PathParam("id") Integer id) {
	System.out.println("REST/client:delete-->" + id);
	return Response.status(204).entity("Usuario borrado..." + id).build();
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPersons() {
	
	Jsonb jsonb = JsonbBuilder.create();
	List<Person> list = new ArrayList<Person>();
	Person person1= new Person(1, "Pepito", "pepito@test.com", 20, LocalDate.of(2020, 6, 30),
		BigDecimal.valueOf(123.7));
	Person person2 = new Person(2, "Juanito", "juanito@test.com", 21, LocalDate.of(2020, 6, 30),
		BigDecimal.valueOf(223.1));
	Person person3 = new Person(3, "Jaimito", "jaimito@test.com", 22, LocalDate.of(2020, 6, 30),
		BigDecimal.valueOf(323.4));
	list.add(person1);
	list.add(person2);
	list.add(person3);
	
	// para evitar el error del CORS se agregan los headers
	return Response.ok(jsonb.toJson(list))
		.header("Access-Control-Allow-Origin", "*")
		.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
    }
    */
}