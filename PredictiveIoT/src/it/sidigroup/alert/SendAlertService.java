package it.sidigroup.alert;

import it.sidigroup.constants.Constants.MeasurementType;
import it.sidigroup.wsclient.SoapClient;
import it.sidigroup.wsclient.config.SoapClientConfig;

import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
@Configuration
public class SendAlertService extends HttpUrlConnectionMessageSender implements SendAlertServiceFacade {

	private WebServiceTemplate webServiceTemplate;
	private String defaultUri = "http://hydrodev.hydroservice.it:8011/sap/bc/srt/sap/zalert_iot_ws/100/zalert_iot_ws/zalert_iot_ws_b";
	private String  username = "svil01";
	private String password = "Sidi2015!";
	
	@Override
	protected void prepareConnection(HttpURLConnection connection) throws IOException {

	    BASE64Encoder enc = new sun.misc.BASE64Encoder();
	    String userpassword = username+":"+password;
	    String encodedAuthorization = enc.encode( userpassword.getBytes() );
	    connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

	    super.prepareConnection(connection);
	}

	private Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("it.sidigroup./*");
        return marshaller;
    }

    private SoapClient wsClient(Jaxb2Marshaller marshaller) {
    	SoapClient client = new SoapClient();
    	
    	webServiceTemplate = client.getWebServiceTemplate();
    	webServiceTemplate.setMessageSender(new SoapClientConfig());
    	
    	
        client.setDefaultUri(defaultUri);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        
        return client;
    }
	
    public void sendAlert(MeasurementType type, Double value){
    	
    	
    	//get temperature and humidity as string
		String valueToBeSent = value.toString();
		StreamSource source = new StreamSource(new StringReader(valueToBeSent));
		
        StreamResult result = new StreamResult(System.out);       
        
        //send data through client
        this.wsClient(this.marshaller()).getWebServiceTemplate().sendSourceAndReceiveToResult(source, result);
	}
    
    public void sendAlert(MeasurementType type, int value){
		//get temperature and humidity as string
    	String valueToBeSent = Integer.toString(value);
		StreamSource source = new StreamSource(new StringReader(valueToBeSent));
		
        StreamResult result = new StreamResult(System.out);       
        
        //send data through client
        this.wsClient(this.marshaller()).getWebServiceTemplate().sendSourceAndReceiveToResult(source, result);
	}
}