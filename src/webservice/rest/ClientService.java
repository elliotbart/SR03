package webservice.rest;

import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.HttpRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Client;
import beans.Type;
import dao.ClientDao;
import dao.GameDao;

@DenyAll
@Path("/Clients")
public class ClientService {

	@DenyAll
	 @GET
	 @Path("/clients")
	 //public List<Client> getClients(@Context HttpRequest request) {
	public Response getClients(@Context HttpRequest request) {
		List<Client> dataBaseClients = ClientDao.findAllSQL();
		//return dataBaseClients;


		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		String json = "[]";
		try {
			json = mapper.writeValueAsString(dataBaseClients);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		ResponseBuilder rb;
		if (json.isEmpty()) {
			rb = Response.serverError().status(404);
		} else {
			rb = Response.ok(json).status(200);
		}
		return rb.build();
	}

	@PermitAll
	@GET
	@Path("/clients/{email}")
	// @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	//public Client getClient(@PathParam("name") String email) {
	public Response getClient(@PathParam("email") String email) {
		Client client = ClientDao.findSQL(email);
		//return client;


		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		String json = "[]";
		try {
			json = mapper.writeValueAsString(client);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		ResponseBuilder rb;
		if (json.isEmpty()) {
			rb = Response.serverError().status(404);
		} else {
			rb = Response.ok(json).status(200);
		}
		return rb.build();

		
	}

	@PermitAll
	@GET
	@Path("/games/{name}/type")
	// @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getVideoGameType(@PathParam("name") String name) {
		Type type = GameDao.findGameTypeSQL(name);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		String json = "[]";
		try {
			json = mapper.writeValueAsString(type);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		ResponseBuilder rb;
		if (json.isEmpty()) {
			rb = Response.serverError().status(404);
		} else {
			rb = Response.ok(json).status(200);
		}
		return rb.build();
	}

}