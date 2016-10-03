package it.sidigroup.utils;

import it.sidigroup.persistence.Sensor;
import it.sidigroup.persistence.SingleMeasurement;
import it.sidigroup.servlet.DataCollectorServlet;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleDataHelper {
	//private EntityManagerFactory emf;

	/*public SingleDataHelper(EntityManagerFactory emf) {
		this.emf = emf;
	}*/

	private static Logger logger = LoggerFactory.getLogger(DataCollectorServlet.class);
	
	/**
	 * Delete all data from Hana Cloud Database
	 */
	public void deleteAllData(){
		EntityManager em = Utils.getEntityManagerFactory().createEntityManager();
		try {
			Query q = em.createNamedQuery("deleteAllData");
			em.getTransaction().begin();			
			q.executeUpdate();
			//em.persist(q);
			em.getTransaction().commit();
		} catch (Exception e) {

		}
	}
	
	/**
	 * Insert sensor into Hana Cloud Database
	 */
	public boolean insertSensor(Sensor sensor) {
		boolean result = false;

		EntityManager em = Utils.getEntityManagerFactory().createEntityManager();
		// System.out.println("Trying to commit sensor data for sensor " +
		// measurement.getSensorDescription());
		logger.info("Persisting sensor's "+sensor.getInventoryID()+" data into Hana DB");
		try {
				em.getTransaction().begin();
				em.merge(sensor);
				em.getTransaction().commit();
				result = true;
		} catch (Exception e) {
			logger.error("ERROR: persisting data didn't work " + e.getMessage());		
		} finally {
			em.close();
		}

		return result;
	}

	/**
	 * Insert measurement into Hana Cloud Database
	 */
	public boolean insertMeasurement(SingleMeasurement measurement) {
		boolean result = false;

		EntityManager em = Utils.getEntityManagerFactory().createEntityManager();
		// System.out.println("Trying to commit sensor data for sensor " +
		// measurement.getSensorDescription());
		try {
				em.getTransaction().begin();
				em.merge(measurement);
				em.getTransaction().commit();
				result = true;
		} catch (Exception e) {
			System.out.println("ERROR: persisting measurement didn't work " + e.getMessage());
		} finally {
			em.close();
		}

		return result;
	}
	
	/**
	 * Get last n readings from Hana Cloud Database
	 */
	@SuppressWarnings("unchecked")
	public List<SingleMeasurement> getLastReadings(int numberOfReadings) {
		List<SingleMeasurement> result = null;

		EntityManager em = Utils.getEntityManagerFactory().createEntityManager();
		try {

			Query q = em.createNamedQuery("lastReadingFromSensors");
			q.setMaxResults(numberOfReadings);
			result = q.getResultList();

			/*Collections.sort(result, new Comparator<SingleMeasurement>() {
				public int compare(SingleMeasurement m1, SingleMeasurement m2) {
					return m1.getStoredAt().compareTo(m2.getStoredAt());
				}
			});*/

		} catch (Exception e) {

		}

		em.close();
		return result;
	}

	/**
	 * Get all data from Hana Cloud Database
	 */
	@SuppressWarnings("unchecked")
	public List<SingleMeasurement> getAllSingleMeasurements() {
		List<SingleMeasurement> result = null;

		EntityManager em = Utils.getEntityManagerFactory().createEntityManager();
		try {
			Query q = em.createNamedQuery("getAllSingleMeasurements");
			q.setMaxResults(500);
			result = q.getResultList();

		} catch (Exception e) {

		}

		em.close();
		return result;
	}

	/**
	 * Get last reading from Hana Cloud Database
	 * 
	 * @param sensorId The sensor id of the sensor that you wish to get the
	 * measured value from
	 */
	public SingleMeasurement getLastReading() {
		SingleMeasurement result = null;

		EntityManager em = Utils.getEntityManagerFactory().createEntityManager();
		try {
			Query q = em.createNamedQuery("lastReading");
			result = (SingleMeasurement) q.getSingleResult();

		} catch (Exception e) {

		}

		em.close();
		return result;
	}
	
	public Sensor getSensorByName(String name){
		Sensor result = null;
		EntityManager em = Utils.getEntityManagerFactory().createEntityManager();
		try {
			Query q = em.createNamedQuery("searchByName");
			q.setParameter("nameSensor",name);
			result = (Sensor) q.getSingleResult();

		} catch (Exception e) {

		}

		em.close();
		return result;
	}
	
	public Sensor getSensorByInvetoryID(String inventoryID){
		Sensor result = null;
		EntityManager em = Utils.getEntityManagerFactory().createEntityManager();
		try {
			Query q = em.createNamedQuery("searchByID");
			q.setParameter("inventoryID",inventoryID);
			result = (Sensor) q.getSingleResult();

		} catch (Exception e) {

		}

		em.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Sensor> getSensorList(){
		List<Sensor> sensors = null;
		EntityManager em = Utils.getEntityManagerFactory().createEntityManager();
		try {
			Query q = em.createNamedQuery("getListOfSensors");
			sensors = (List<Sensor>) q.getSingleResult();

		} catch (Exception e) {

		}

		em.close();
		return sensors;
	}

	public static Logger getLogger() {
		return logger;
	}
}