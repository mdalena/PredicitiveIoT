package it.sidigroup.utils;

import it.sidigroup.constants.Constants.MeasurementType;
import it.sidigroup.persistence.ClientRequestDTO;
import it.sidigroup.persistence.MeasurementDTO;
import it.sidigroup.persistence.Sensor;
import it.sidigroup.persistence.SingleMeasurement;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sap.security.core.server.csi.IXSSEncoder;
import com.sap.security.core.server.csi.XSSEncoder;

public class ServletUtils {
	/* ****************** JSON CONVERSION ****************** */

	public static ClientRequestDTO dejsonizeRequestData(String dto) {
		Gson gson = new Gson();
		ClientRequestDTO clientRequestDto = gson.fromJson(dto, ClientRequestDTO.class);
		return clientRequestDto;
	}
	
	public static String jsonSerializer(List<SingleMeasurement> sensorMeasurements){
		Gson gson = new Gson();
		String json = "{";
		if(sensorMeasurements != null){
			for(SingleMeasurement measurement : sensorMeasurements){
				json = json+gson.toJson(measurement)+",";
			}
		}
		json = json+"}";
		return json;	
	}
	
	public static MeasurementDTO jsonDeserializer(JsonObject jsonObj){
		MeasurementDTO measurementDTO = new MeasurementDTO();
		
		String valueString = jsonObj.get("id").getAsString();
		measurementDTO.setId(valueString);
		
		valueString = jsonObj.get("description").getAsString();
		measurementDTO.setDescription(valueString);
		
		valueString = jsonObj.get("inventoryID").getAsString();
		measurementDTO.setInventoryID(valueString);		
		
		valueString = jsonObj.get("name").getAsString();
		measurementDTO.setName(valueString);
		
		valueString = jsonObj.get("state").getAsString();
		measurementDTO.setState(valueString);
		
		valueString = jsonObj.get("type").getAsString();
		measurementDTO.setType(valueString);
		
		valueString = jsonObj.get("unit").getAsString();
		measurementDTO.setUnit(valueString);		

		Double valueDouble = jsonObj.get("value").getAsDouble();
		measurementDTO.setValue(valueDouble);
		
		int valueInt = jsonObj.get("writeable").getAsInt();
		measurementDTO.setWriteable(valueInt);
		
		long valueLong = jsonObj.get("lastGoodUpdate").getAsLong();
		measurementDTO.setLastGoodUpdate(Utils.millisToDate(valueLong));
		
		valueLong = jsonObj.get("lastUpdate").getAsLong();
		measurementDTO.setLastUpdate(Utils.millisToDate(valueLong));
		
		return measurementDTO;
	}
	
	/**
	 * Extracts a Measurement object (sensor values) out of the parameters
	 * provided in the HTTP-request
	 * 
	 * @param request
	 *            The HTTP-request object
	 * 
	 * @return The derived Measurement object
	 */
	public static Sensor extractRequestData(HttpServletRequest request, SingleDataHelper sdh) {
		String sensorName = request.getParameter("name");
		
		Sensor sensor = sdh.getSensorByName(sensorName);
		if(sensor == null){
			sensor = new Sensor();
			sensor.setName(encodeText(sensorName));
			sensor.setInventoryID(encodeText(request.getParameter("inventoryID")));
			sensor.setDescription(encodeText(request.getParameter("description")));
			sensor.setLastGoodUpdate(Utils.stringToTimestamp(encodeText(request.getParameter("lastGoodUpdate"))));
			List<SingleMeasurement> measurements = new ArrayList<SingleMeasurement>();
			sensor.setMeasurements(measurements);
		}
		SingleMeasurement measurement = new SingleMeasurement();
		measurement.setStoredAt(Utils.stringToTimestamp(encodeText(request.getParameter("lastUpdate"))));
		measurement.setState(Integer.parseInt(encodeText(request.getParameter("state"))));
		measurement.setValue(Double.parseDouble(encodeText(request.getParameter("value"))));
		measurement.setUnit(encodeText(request.getParameter("unit")));
		
		sensor.getMeasurements().add(measurement);
			
		return sensor;
	}
	
	/**
	 * Extract data from json body request converted into DTO
	 * 
	 * @param dto Body request POJO
	 * @param sdh data helper for single measurement
	 * @return
	 */
	public static Sensor extractData(MeasurementDTO dto, SingleDataHelper sdh) {
		Sensor sensor = sdh.getSensorByName(dto.getName());
		SingleMeasurement sm = new SingleMeasurement();
		List<SingleMeasurement> measurements;
		if(sensor == null){
			sensor = new Sensor();
			sensor.setName(dto.getName());
			sensor.setInventoryID(dto.getInventoryID());
			sensor.setDescription(dto.getDescription());
			sensor.setLastGoodUpdate(dto.getLastGoodUpdate());
			measurements = new ArrayList<SingleMeasurement>();
			sensor.setMeasurements(measurements);
		}

		sm.setSensorID(sensor.getId());
		sm.setInventoryID(dto.getInventoryID());
		sm.setLastGoodUpdate(dto.getLastGoodUpdate());
		sm.setState(Integer.parseInt(dto.getState()));
		sm.setStoredAt(dto.getLastUpdate());
		sm.setUnit(dto.getUnit());
		sm.setValue(dto.getValue());
		
		measurements = (List<SingleMeasurement>) sensor.getMeasurements();
		measurements.add(sm);

		return sensor;
	}
	
	public static MeasurementType getMeasurementType(MeasurementDTO measurement){
		MeasurementType type = null;
		String inventoryID = "";
		
		inventoryID = measurement.getInventoryID();
		type = MeasurementType.valueOf(inventoryID);
		
		return type;
	}
	
	/* ****************** CRUD ****************** */

	/**
	 * Delete all data from database through ServletUtils layer
	 * 
	 * @param dataHelper
	 */
	public static void deleteAllData(SingleDataHelper sdh) {
		sdh.deleteAllData();
	}


	public static boolean insertSingleMeasurement(SingleDataHelper sdh, SingleMeasurement measurement) {
		boolean insertOK = sdh.insertMeasurement(measurement);
		return insertOK;
	}
	
	public static boolean insertSensor(SingleDataHelper sdh, Sensor sensor) {
		boolean insertOK = sdh.insertSensor(sensor);
		return insertOK;
	}

	/* ****************** ENCODING ****************** */
	/*
	 * Encodes a text to avoid cross-site-scripting vulnerability
	 * 
	 * @param request The text to be encoded
	 * 
	 * @return The encoded String
	 */
	public static String encodeText(String text) {
		String result = null;
		if (text != null && text.length() > 0) {
			IXSSEncoder xssEncoder = XSSEncoder.getInstance();

			try {
				result = (String) xssEncoder.encodeURL(text).toString();
				result = URLDecoder.decode(result, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
