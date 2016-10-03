package it.sidigroup.predictive;

import it.sidigroup.constants.Constants;
import it.sidigroup.constants.Constants.MeasurementType;
import it.sidigroup.constants.Constants.SensorStatusAnalysis;
import it.sidigroup.persistence.SingleMeasurement;
import it.sidigroup.utils.SingleDataHelper;

import java.util.List;

public class SidiPredictiveAnalysis {

	public static SensorStatusAnalysis performDataAnalysis(SingleDataHelper sdh, MeasurementType type, String inventoryID) {
		
		SensorStatusAnalysis sensorStatus = SensorStatusAnalysis.GOOD;
		List<SingleMeasurement> result = (List<SingleMeasurement>) sdh.getSensorByInvetoryID(inventoryID).getMeasurements();
		double avgDouble = new Double(0);
		SingleMeasurement refValue = result.get(result.size()); //take last value
		int cnt = 0;
		
		for(int i=result.size(); i > 0; i--){
			avgDouble = avgDouble+result.get(i).getValue();
			if(refValue.getValue() > result.get(i).getValue()){
				cnt = cnt++;
			}else{
				//stop
			}
		}
		if(cnt>Constants.getThreshold()){
			sensorStatus = SensorStatusAnalysis.NEEDS_MAINTENANCE;
		}
		return sensorStatus;
	}
}
