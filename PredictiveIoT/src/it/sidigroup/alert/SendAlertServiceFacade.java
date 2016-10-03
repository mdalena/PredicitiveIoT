package it.sidigroup.alert;

import it.sidigroup.constants.Constants.MeasurementType;

public interface SendAlertServiceFacade{

	public void sendAlert(MeasurementType type, Double value);
	
	public void sendAlert(MeasurementType type, int value);
}
