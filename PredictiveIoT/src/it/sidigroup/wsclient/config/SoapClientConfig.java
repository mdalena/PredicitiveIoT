package it.sidigroup.wsclient.config;

import it.sidigroup.alert.SendAlertService;
import it.sidigroup.wsclient.SoapClient;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.transaction.SystemException;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;

import sun.misc.BASE64Encoder;

import com.sap.ecm.api.ServiceException;

@Configuration
public class SoapClientConfig extends HttpUrlConnectionMessageSender {

	private WebServiceTemplate webServiceTemplate;
	private String defaultUri = "http://hydrodev.hydroservice.it:8011/sap/bc/srt/sap/zalert_iot_ws/100/zalert_iot_ws/zalert_iot_ws_b";
	private String  username = "svil01";
	private String password = "Sidi2015!";
	
	@Override
	protected void prepareConnection(HttpURLConnection connection)
	        throws IOException {

	    BASE64Encoder enc = new sun.misc.BASE64Encoder();
	    String userpassword = username+":"+password;
	    String encodedAuthorization = enc.encode( userpassword.getBytes() );
	    connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

	    super.prepareConnection(connection);
	}

	@Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("it.sidigroup./*");
        return marshaller;
    }

    @Bean
    public SoapClient wsClient(Jaxb2Marshaller marshaller) {
    	SoapClient client = new SoapClient();
    	
    	webServiceTemplate = client.getWebServiceTemplate();
    	webServiceTemplate.setMessageSender(new SoapClientConfig());
    	
    	
        client.setDefaultUri(defaultUri);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        
        return client;
    }

    @Bean(name = "sendAlertSOAPWebService")
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public SendAlertService sendAlertSOAPWebService() throws ServiceException, SystemException {
		
    	System.out.println("Defining WS");
    	
    	JaxWsProxyFactoryBean clientFactoryBean = new JaxWsProxyFactoryBean();
		System.out.println("Setting service");
		clientFactoryBean.setAddress(defaultUri);
		clientFactoryBean.setServiceClass(SendAlertService.class);
		clientFactoryBean.setUsername(username);
		clientFactoryBean.setPassword(password);
		return (SendAlertService) clientFactoryBean.create();
	}
    
	public WebServiceTemplate getWebServiceTemplate() {
		return webServiceTemplate;
	}

	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}
}