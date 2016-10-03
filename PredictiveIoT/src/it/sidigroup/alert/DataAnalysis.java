package it.sidigroup.alert;

import it.sidigroup.constants.Constants.AlertStatus;
import it.sidigroup.constants.Constants.MeasurementType;
import it.sidigroup.persistence.SingleMeasurement;
import it.sidigroup.utils.SingleDataHelper;

public class DataAnalysis {

	private static SendAlertServiceFacade service;

	public static void performDataAnalysis(SingleDataHelper sdh, SingleMeasurement measurement, MeasurementType type) {
		//service = new SendAlertService();

		// Build AlertStatusMap
		AlertStatus alertStatus = CheckSensorStatus.checkSensorValue(measurement, type);

		if (alertStatus.equals(AlertStatus.ALERT)) {
			//service.sendAlert(entry.getKey(), ServletUtils.getDoubleValue(dataHelper, entry.getKey()));
		}
	}
}
