package it.sidigroup.constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	/*
	 Temp	-15	: +100	°C		50		60		80
	 P1		1.6	: 160	bar		120		130		140
	 P2		1.6	: 160	bar		45		50		60
	 V		0	: 25	mm/s	5		10		15
	 H		0	: 1000	ppm		30		100		200
	 C		0	: 5		bar		0		5		NA	
	 */
	
	private static final Double ALERT_MIN_TEMP = new Double(60);
	private static final Double ALERT_MAX_TEMP = new Double(79);	
	private static final Double FAULT_TEMP = new Double(80);

	private static final Double ALERT_MIN_PRESSURE1 = new Double(130);
	private static final Double ALERT_MAX_PRESSURE1 = new Double(139);	
	private static final Double FAULT_PRESSURE1 = new Double(140);
	
	private static final Double ALERT_MIN_PRESSURE2 = new Double(50);
	private static final Double ALERT_MAX_PRESSURE2 = new Double(59);	
	private static final Double FAULT_PRESSURE2 = new Double(60);
	
	private static final Double ALERT_MIN_VIBRATION = new Double(50);
	private static final Double ALERT_MAX_VIBRATION = new Double(59);	
	private static final Double FAULT_VIBRATION = new Double(60);
	
	private static final Double ALERT_MIN_HUMIDITY = new Double(100);
	private static final Double ALERT_MAX_HUMIDTY = new Double(199);	
	private static final Double FAULT_HUMIDTY = new Double(200);
	
	private static final Double ALERT_CLOGGED = new Double(5);

	private static final Double THRESHOLD = new Double(10);	//used for measuring tread of latest 10 measured values
	
	private static final String WRONG_DATA_TYPE_INVENTORY = "Wrong data type for the given inventory ID";
	private static final String WRONG_DATA_TYPE = "Data type does not exists";
	
	public static final long SECOND_IN_MILLIS = 1000;
	public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
	public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
	public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
	
	public enum SensorStatusAnalysis {
		GOOD, NEEDS_MAINTENANCE
	};
	
	public enum AlertStatus {
	    NONE(1),
	    ALERT(2),
	    MALFUNCTION(3),
	    IGNORE(4);
	    
	    private int status;

	    private static Map<Integer, AlertStatus> map = new HashMap<Integer, AlertStatus>();

	    static {
	        for (AlertStatus alertEnum : AlertStatus.values()) {
	            map.put(alertEnum.status, alertEnum);
	        }
	    }

	    private AlertStatus(final int statusInt) { status = statusInt; }

	    public static AlertStatus valueOf(int statusInt) {
	        return map.get(statusInt);
	    }
	};
	
	/*public enum MeasurementType {
		TEMPERATURE_01, PRESSURE_01, PRESSURE_02,
		VIBRATION_01, HUMIDITY_01, CLOGGED_01
	};*/
	
	public static interface EnumExtensionInterface<T extends Enum<T>> {
	    String name();

	    String getFriendlyName();

	    Class<T> getDeclaringClass();

	    T getRaw();

	}

	public static enum MeasurementType {
		TEMPERATURE_01("TEMPERATURE_01"),
		PRESSURE_01("PRESSURE_01"),
		PRESSURE_02("PRESSURE_02"),
		VIBRATION_01("VIBRATION_01"),
		HUMIDITY_01("HUMIDITY_01"),
		CLOGGED_01("CLOGGED_01");

		private String type;

	    private static Map<String, MeasurementType> map = new HashMap<String, MeasurementType>();

	    static {
	        for (MeasurementType type : MeasurementType.values()) {
	            map.put(type.type, type);
	        }
	    }

	    private MeasurementType(final String typeS) { type = typeS; }

	    /*
	    public static MeasurementType valueOf(String type) {
	        return map.get(type);
	    }*/
	}
	
	public enum InventoryID {
		TEMPERATURE_01("TEMPERATURE_01"),
		PRESSURE_01("PRESSURE_01"),
		PRESSURE_02("PRESSURE_02"),
		VIBRATION_01("VIBRATION_01"),
		HUMIDITY_01("HUMIDITY_01"),
		CLOGGED_01("CLOGGED_01");

		private String id;

	    private static Map<String, InventoryID> map = new HashMap<String, InventoryID>();

	    static {
	        for (InventoryID idS : InventoryID.values()) {
	            map.put(idS.id, idS);
	        }
	    }

	    private InventoryID(final String idS) { id = idS; }

	    /*
	    public static MeasurementType valueOf(String type) {
	        return map.get(type);
	    }*/
	};
	
//	public static long getHoursFromMillis(long millis){
//		long hours = 0;
//		
//		hours = SECOND_IN_MILLIS * HOUR_IN_MILLIS;
//		
//		return hours;
//	}
//	
//	public static long getDaysFromMillis(long millis){
//		long days = 0;
//		
//		return days;
//	}
	
	public static Double getAlertMinTemp() {
		return ALERT_MIN_TEMP;
	}

	public static Double getAlertMaxTemp() {
		return ALERT_MAX_TEMP;
	}

	public static Double getFaultTemp() {
		return FAULT_TEMP;
	}

	public static Double getAlertMinPressure1() {
		return ALERT_MIN_PRESSURE1;
	}

	public static Double getAlertMaxPressure1() {
		return ALERT_MAX_PRESSURE1;
	}

	public static Double getFaultPressure1() {
		return FAULT_PRESSURE1;
	}

	public static Double getAlertMinPressure2() {
		return ALERT_MIN_PRESSURE2;
	}

	public static Double getAlertMaxPressure2() {
		return ALERT_MAX_PRESSURE2;
	}

	public static Double getFaultPressure2() {
		return FAULT_PRESSURE2;
	}

	public static Double getAlertMinVibration() {
		return ALERT_MIN_VIBRATION;
	}

	public static Double getAlertMaxVibration() {
		return ALERT_MAX_VIBRATION;
	}

	public static Double getFaultVibration() {
		return FAULT_VIBRATION;
	}

	public static Double getAlertMinHumidity() {
		return ALERT_MIN_HUMIDITY;
	}

	public static Double getAlertMaxHumidty() {
		return ALERT_MAX_HUMIDTY;
	}

	public static Double getFaultHumidty() {
		return FAULT_HUMIDTY;
	}

	public static Double getAlertClogged() {
		return ALERT_CLOGGED;
	}

	public static Double getThreshold() {
		return THRESHOLD;
	}

	public static String getWrongDataTypeInventory() {
		return WRONG_DATA_TYPE_INVENTORY;
	}

	public static String getWrongDataType() {
		return WRONG_DATA_TYPE;
	}
}
