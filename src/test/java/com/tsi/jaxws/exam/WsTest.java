package com.tsi.jaxws.exam;

import com.sun.xml.ws.fault.ServerSOAPFaultException;
import com.tsi.jaxws.exam.bean.CustomerBean;
import com.tsi.jaxws.exam.bean.CustomerFaultException;
import com.tsi.jaxws.exam.service.CustomerService;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Даниил
 */
public class WsTest {

    public WsTest() {
    }

    @Test
    public void basicAuthenticationUsingSOAPHandler() throws Exception {

        // initialize web service client
        URL url = new URL("http://localhost:8080/WebServiceExam03/CustomerService01?wsdl");
        QName qname = new QName("http://com.tsi.jaxws.exam", "CustomerService01");
        Service service = Service.create(url, qname);
        CustomerService port = service.getPort(CustomerService.class);

        // add username and password 
        Map<String, Object> ctx = ((BindingProvider) port).getRequestContext();
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Username", Collections.singletonList("Mabel"));
        headers.put("Password", Collections.singletonList("Waddles"));
        ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        // call web service
        CustomerBean result = port.get();

        // check result
        assertEquals("Bill", result.getFirstname());
        assertEquals("Cypher", result.getLastname());
        assertEquals(Integer.MAX_VALUE, result.getAge());
    }

    @Test(expected = ServerSOAPFaultException.class)
    public void basicAuthenticationUsingSOAPHandlerFails() throws Exception {

        // initialize web service client
        URL url = new URL("http://localhost:8080/WebServiceExam03/CustomerService01?wsdl");
        QName qname = new QName("http://com.tsi.jaxws.exam", "CustomerService01");
        Service service = Service.create(url, qname);
        CustomerService port = service.getPort(CustomerService.class);

        // add username and password
        Map<String, Object> ctx = ((BindingProvider) port).getRequestContext();
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Username", Collections.singletonList("Deeper"));
        headers.put("Password", Collections.singletonList("Waddles"));
        ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        // call web service
        CustomerBean result = port.get();

    }

    @Test
    public void basicAuthenticationInWebServiceMethod() throws Exception {

        // initialize web service client
        URL url = new URL("http://localhost:8080/WebServiceExam03/CustomerService02?wsdl");
        QName qname = new QName("http://com.tsi.jaxws.exam", "CustomerService02");
        Service service = Service.create(url, qname);
        CustomerService port = service.getPort(CustomerService.class);

        // add username and password
        Map<String, Object> ctx = ((BindingProvider) port).getRequestContext();
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Username", Collections.singletonList("Mabel"));
        headers.put("Password", Collections.singletonList("Waddles"));
        ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        // call web service
        CustomerBean result = port.get();

        // check result
        assertEquals("Bill", result.getFirstname());
        assertEquals("Cypher", result.getLastname());
        assertEquals(Integer.MAX_VALUE, result.getAge());
    }

    @Test(expected = CustomerFaultException.class)
    public void basicAuthenticationInWebServiceMethodFails() throws Exception {

        // initialize web service client
        URL url = new URL("http://localhost:8080/WebServiceExam03/CustomerService02?wsdl");
        QName qname = new QName("http://com.tsi.jaxws.exam", "CustomerService02");
        Service service = Service.create(url, qname);
        CustomerService port = service.getPort(CustomerService.class);

        // add username and password
        Map<String, Object> ctx = ((BindingProvider) port).getRequestContext();
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Username", Collections.singletonList("Stanley"));
        headers.put("Password", Collections.singletonList("Waddles"));
        ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        // call web service
        CustomerBean result = port.get();

    }

    @Test
    public void basicAuthenticationUsingContainerAuthentication() throws Exception {

        java.net.Authenticator.setDefault(new java.net.Authenticator() {

            @Override
            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                return new java.net.PasswordAuthentication("tomcat", "tomcat".toCharArray());
            }
        });
        
        // initialize web service client
        URL url = new URL("http://localhost:8080/WebServiceExam03/CustomerService03?wsdl");
        QName qname = new QName("http://com.tsi.jaxws.exam", "CustomerService03");
        Service service = Service.create(url, qname);
        CustomerService port = service.getPort(CustomerService.class);

        // add username and password
        BindingProvider prov = (BindingProvider) port;
        prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "tomcat");
        prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "tomcat");
        
        // call web service
        CustomerBean result = port.get();

        // check result
        assertEquals("Bill", result.getFirstname());
        assertEquals("Cypher", result.getLastname());
        assertEquals(Integer.MAX_VALUE, result.getAge());
    }

    @Test(expected = WebServiceException.class)
    public void basicAuthenticationUsingContainerAuthenticationFails() throws Exception {

        // initialize web service client
        URL url = new URL("http://localhost:8080/WebServiceExam03/CustomerService03?wsdl");
        QName qname = new QName("http://com.tsi.jaxws.exam", "CustomerService03");
        Service service = Service.create(url, qname);
        CustomerService port = service.getPort(CustomerService.class);

        // add username and password
        BindingProvider prov = (BindingProvider) port;
        prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "tomcat");
        prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "Start123");

        // call web service
        CustomerBean result = port.get();

    }
}
