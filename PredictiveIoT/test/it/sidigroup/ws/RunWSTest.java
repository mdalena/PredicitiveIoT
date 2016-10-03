package it.sidigroup.ws;

import it.sidigroup.alert.SendAlertService;
import it.sidigroup.alert.SendAlertServiceFacade;
import it.sidigroup.constants.Constants.MeasurementType;

public class RunWSTest {	
	private static SendAlertServiceFacade service;
	
	public static void main(String[] args){
		System.out.println("Calling service");
		service = new SendAlertService();
		
		service.sendAlert(MeasurementType.HUMIDITY_01, 15);
		
		System.out.println("service instatiated");
	}

}
