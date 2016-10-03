package it.sidigroup.alert;

import it.sidigroup.constants.Constants;
import it.sidigroup.constants.Constants.AlertStatus;
import it.sidigroup.constants.Constants.MeasurementType;
import it.sidigroup.persistence.SingleMeasurement;

public class CheckSensorStatus {

	public static AlertStatus checkSensorValue(SingleMeasurement measurement, MeasurementType type){
		AlertStatus alert = AlertStatus.NONE;
		Double value = measurement.getValue();	
		
		if(MeasurementType.TEMPERATURE_01.equals(type)){
			if(value >= Constants.getAlertMinTemp() || value <= Constants.getAlertMaxTemp()){
				alert = AlertStatus.ALERT;
			}else if(value > Constants.getFaultTemp()){
				alert = AlertStatus.MALFUNCTION;
			}
		}else if(MeasurementType.PRESSURE_01.equals(type)){
			if(value >= Constants.getAlertMinPressure1() || value <= Constants.getAlertMaxPressure1()){
				alert = AlertStatus.ALERT;
			}else if(value > Constants.getFaultPressure1()){
				alert = AlertStatus.MALFUNCTION;
			}
		}else if(MeasurementType.PRESSURE_02.equals(type)){
			if(value >= Constants.getAlertMinPressure2() || value <= Constants.getAlertMaxPressure2()){
				alert = AlertStatus.ALERT;
			}else if(value > Constants.getFaultPressure2()){
				alert = AlertStatus.MALFUNCTION;
			}
		}else if(MeasurementType.VIBRATION_01.equals(type)){
			if(value >= Constants.getAlertMinVibration() || value <= Constants.getAlertMaxVibration()){
				alert = AlertStatus.ALERT;
			}else if(value > Constants.getFaultVibration()){
				alert = AlertStatus.MALFUNCTION;
			}
		}else if(MeasurementType.HUMIDITY_01.equals(type)){
			if(value >= Constants.getAlertMinHumidity() || value <= Constants.getAlertMaxHumidty()){
				alert = AlertStatus.ALERT;
			}else if(value > Constants.getFaultHumidty()){
				alert = AlertStatus.MALFUNCTION;
			}
		}else if(MeasurementType.CLOGGED_01.equals(type)){
			if(value >= Constants.getAlertClogged()){
				alert = AlertStatus.ALERT;
			}
		}
		
		return alert;
	}
}