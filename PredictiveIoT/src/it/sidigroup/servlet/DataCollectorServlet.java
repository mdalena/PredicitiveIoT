package it.sidigroup.servlet;

import it.sidigroup.alert.DataAnalysis;
import it.sidigroup.cache.InMemoryCache;
import it.sidigroup.constants.Constants.MeasurementType;
import it.sidigroup.persistence.MeasurementDTO;
import it.sidigroup.persistence.Sensor;
import it.sidigroup.persistence.SingleMeasurement;
import it.sidigroup.predictive.SidiPredictiveAnalysis;
import it.sidigroup.utils.ServletUtils;
import it.sidigroup.utils.SingleDataHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class SensorsServlet
 */
public class DataCollectorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_NUMBER_READINGS = 100;

	private DataSource ds;
	private EntityManagerFactory emf;
	private SingleDataHelper sdh;
	private InMemoryCache<Integer, Sensor> memoryCache;
	
	private static Logger logger;

	public DataCollectorServlet() {
		super();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getMethod();
		String action = ServletUtils.encodeText(request.getParameter("action"));
		if(("post").equalsIgnoreCase(method)){
			logger.debug("POST request caught into doPost");
			useRequestBody(request, response);
		}else if(("get").equalsIgnoreCase(method)){
			logger.debug("GET request caught into doPost");
			try {
				useStringQuery(request, response, action, sdh);
			} catch (Exception e) {
				logger.error("Failed to execute task: "+e.getMessage()+"\n"+e.getCause());
			}
		}else{
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Bad request.");
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getMethod();
		String action = ServletUtils.encodeText(request.getParameter("action"));
		if(("post").equalsIgnoreCase(method)){
			logger.debug("POST request caught into doGet");
			useRequestBody(request, response);
		}else if(("get").equalsIgnoreCase(method)){
			logger.debug("GET request caught into doGet");
			try {
				useStringQuery(request, response, action, sdh);
			} catch (Exception e) {
				logger.error("Failed to execute task: "+e.getMessage()+"\n"+e.getCause());
			}
		}else{
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Bad request.");
		}
	}

	
	private void useStringQuery(HttpServletRequest request, HttpServletResponse response, String action, SingleDataHelper sdh) throws Exception{
		// Remove all sensor
		if (action != null && action.equalsIgnoreCase("deleteData")) {
			queryStringDeleteData();
		}

		SingleMeasurement measurement = null;
		Sensor sensor = null;
		boolean insertOK = false;
		
		// Add a sensor value
		if (action != null && action.equalsIgnoreCase("insertValue")) {
			queryStringInsertData(request, response, sensor, measurement, insertOK);
		}

		// Provide a JSON output of all sensor values (measurements)
		if (action != null && action.equalsIgnoreCase("getAllData")) {
			queryStringPrintAllData(response);
		}
		
		if(action != null && action.equalsIgnoreCase("getappdata")){
			queryStringGetData(response);
		}

		// If no action parameter is provided simply print out the sensor data
		// as JSON
		if (action == null) {	
			queryStringPrintLastData(response);
		}
	}
	
	private void queryStringInsertData(HttpServletRequest request, HttpServletResponse response, Sensor sensor, SingleMeasurement measurement, boolean insertOK) throws IOException{
		if(request.getParameter("inventoryID")!=null || request.getParameter("inventoryID").length() > 0){
			sensor = ServletUtils.extractRequestData(request, sdh);
			insertOK = ServletUtils.insertSensor(sdh, sensor);
		}else{
			sensor = ServletUtils.extractRequestData(request, sdh);
			insertOK = ServletUtils.insertSingleMeasurement(sdh, measurement);
		}
		DataAnalysis.performDataAnalysis(sdh, measurement, null);

		// send response back
		if(insertOK){
			response.sendError(HttpServletResponse.SC_OK, "Data insert succesfully");
		} else {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Server error.");
		}
	}
	
	private void queryStringGetData(HttpServletResponse response) throws IOException{
		//print data divided by measure type		
		Gson gson = new Gson();
		List<Sensor> sensors = sdh.getSensorList();
		String jsonString = "";
		
		for(int i=0; i<sensors.size(); i++){
			jsonString = jsonString + gson.toJson(sensors.get(i));
		}
		
		response.getWriter().println(jsonString);
	}
	
	private void queryStringPrintAllData(HttpServletResponse response) throws Exception{	
		List<SingleMeasurement> sensorMeasurements = sdh.getAllSingleMeasurements();
		String json = ServletUtils.jsonSerializer(sensorMeasurements);
		response.getWriter().print(json);
	}
	
	private void queryStringPrintLastData(HttpServletResponse response) throws Exception{
		List<SingleMeasurement> sensorMeasurements = sdh.getLastReadings(MAX_NUMBER_READINGS);
		String json = ServletUtils.jsonSerializer(sensorMeasurements);
		response.getWriter().print(json);
	}
	
	private void queryStringDeleteData(){
		ServletUtils.deleteAllData(sdh);
	}
	
	private void useRequestBody(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// this.doGet(request, response);
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG).create();

		//read request
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonString = new String();
		logger.info("reading body request...");
		try {
			String s = "";
			while ((s = br.readLine()) != null)
				jsonString += s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//get json from body request
		JsonArray jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();
	   
	      
		//JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
		//JsonArray jsonArray = jsonElement.getAsJsonArray();
		

		logger.info("Parsing json array with "+jsonArray.size()+" elements");
		logger.info("Json data: "+jsonString);
		//convert json to list of dto measurements
		List<MeasurementDTO> dtoList = new ArrayList<MeasurementDTO>();	
		for(int i=0; i<jsonArray.size();i++){
			JsonObject jobj = jsonArray.get(i).getAsJsonObject();
							
			MeasurementDTO dto = ServletUtils.jsonDeserializer(jobj);
			MeasurementType type = ServletUtils.getMeasurementType(dto);
			
			dtoList.add(dto);
			logger.info("Extracting data for sensor "+dto.getInventoryID());
			Sensor sensor = ServletUtils.extractData(dto, sdh);
			
			try{
				logger.info("Insert sensor "+sensor.getInventoryID()+" into Hana DB");
				ServletUtils.insertSensor(sdh, sensor);
				
				List<SingleMeasurement> measurements = (List<SingleMeasurement>) sensor.getMeasurements();
				for(SingleMeasurement measurement : measurements){
					ServletUtils.insertSingleMeasurement(sdh, measurement);
					logger.info("Performing predictive analysis on sensor's "+sensor.getInventoryID()+" data");
					DataAnalysis.performDataAnalysis(sdh, measurement, type);
				}
				
				//TODO check if database is down
				//TODO check if no data received for XX hours				
				
			}catch(Exception e){
				logger.info("Exception caught: "+e.getMessage()+e.getCause());
				response.sendError(HttpServletResponse.SC_NO_CONTENT, "Empty request or error reading data: "+sensor.getInventoryID()+" "+dto.getLastUpdate());
			}				
		}
		logger.info("All data successufully inserted");
		response.sendError(HttpServletResponse.SC_OK, "Data insert succesfully");						
	}
	
	@Override
	public void init() throws ServletException {
		logger = LogManager.getLogger(DataCollectorServlet.class);
		logger.info("Servlet inizialitation");
		try {
			//InitialContext ctx = new InitialContext();
			//ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");

			//Map properties = new HashMap();
			//properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
			logger.info("Getting JPA entity manager factory");
			emf = JPAEntityManagerFactory.getEntityManagerFactory();
			
			logger.info("Setting data helper");
			sdh = new SingleDataHelper();
			
			//logger.info("Setting in-memory cache");
			//memoryCache = new InMemoryCache<Integer, Sensor>(Constants.DAY_IN_MILLIS, 20*Constants.MINUTE_IN_MILLIS, 3*24);
		} catch (Exception e) {
			logger.error("Initialization failed: "+e);
			throw new ServletException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void destroy() {
		emf.close();
	}

	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public SingleDataHelper getSdh() {
		return sdh;
	}

	public void setSdh(SingleDataHelper sdh) {
		this.sdh = sdh;
	}

	public static int getMaxNumberReadings() {
		return MAX_NUMBER_READINGS;
	}

	public InMemoryCache<Integer, Sensor> getMemoryCache() {
		return memoryCache;
	}

	public void setMemoryCache(InMemoryCache<Integer, Sensor> memoryCache) {
		this.memoryCache = memoryCache;
	}

	public Logger getLogger() {
		return logger;
	}
}
