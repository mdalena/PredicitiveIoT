package it.sidigroup.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManagerFactory;

public class Utils {

	
	/* ENTITY MANAGER */
	private static EntityManagerFactory emf;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (emf == null) {
			throw new IllegalArgumentException("EntityManagerfactory is not initialized!!!");
		}
		return emf;
	}

	public static void setEntityManagerFactory(EntityManagerFactory emf) {
		Utils.emf = emf;
	}
	
	
	/* DATE CONVERSION */
	
	/**
	 *	Convert string date to timestamp 
	 */
	public static Timestamp stringToTimestamp(String str_date) {
		final String FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat(FORMAT);
			Date date = formatter.parse(str_date);
			Timestamp timeStampDate = new Timestamp(date.getTime());

			return timeStampDate;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}
	}
	
	/**
	 *	Convert milliseconds since 1-1-1970 to timestamp 
	 */
	public static Date millisToDate(long millis) {
		DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
		Date date = new Date(millis);
		df.format(date);
		return date;
	}
	
	/**
	 * Convert date to timestamp
	 */
	public static Timestamp dateToTimestamp(Date date){
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
	
	/**
	 *	Convert milliseconds since 1-1-1970 to timestamp 
	 */
	public static Timestamp millisToTimestamp(long millis) {
		Date date = new Date(millis);
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
	
	/* DATA CHECKING */
}
