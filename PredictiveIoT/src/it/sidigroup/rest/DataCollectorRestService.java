package it.sidigroup.rest;

import it.sidigroup.utils.SingleDataHelper;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.eclipse.persistence.config.PersistenceUnitProperties;

@Path("/service")
public class DataCollectorRestService {
	private DataSource ds;
	private EntityManagerFactory emf;
	private SingleDataHelper sdh;
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@PostConstruct
	public void init() throws ServletException {

		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");

			Map properties = new HashMap();
			properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
			emf = Persistence.createEntityManagerFactory("HydroserviceData", properties);		
			sdh = new SingleDataHelper();
		} catch (NamingException e) {
			throw new ServletException(e);
		}
	}
	
	@GET
	@Path("/insertData")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public String doGetInsertMeasurement(String requestData) {
		String response = "Success";
		
		return response;
	}

	
	@POST
	@Path("/insertData")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public String doPostInsertMeasurement(String requestData) {
		String response = "Success";
		
		return response;
	}
	
}
