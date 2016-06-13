package com.tsi.jaxws.exam.service;

import com.tsi.jaxws.exam.bean.CustomerBean;
import com.tsi.jaxws.exam.bean.CustomerFaultException;
import javax.jws.HandlerChain;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web service WSDL will be available using URL
 * http://localhost:8080/WebServiceExam03/CustomerService01?wsdl
 * 
 * This web service uses basic authentication check, implemented is SOAPHandler,
 * which is configured is handler-chain configuration file. When authentication fails,
 * web service method is not invoked.
 * 
 * @author Даниил
 */
@WebService(endpointInterface = "com.tsi.jaxws.exam.service.CustomerService", serviceName = "CustomerService01", targetNamespace = "http://com.tsi.jaxws.exam")
@HandlerChain(file = "handler-chain.xml")
public class CustomerService01 implements CustomerService {

    /**
     * Internal logger
     */
    private final Logger logger = LoggerFactory.getLogger(CustomerService01.class);

    /**
     * Handles web service invocation.
     *
     *
     * @return CustomerBean as web service result
     * @throws com.tsi.jaxws.exam.bean.CustomerFaultException
     */
    @RequestWrapper(localName = "get", targetNamespace = "http://com.tsi.jaxws.exam")
    @ResponseWrapper(
            localName = "customer-response",
            targetNamespace = "http://com.tsi.jaxws.exam",
            className = "com.tsi.jaxws.exam.bean.CustomerResponse"
    )
    public @WebResult(
            name = "customer",
            targetNamespace = "http://com.tsi.jaxws.exam"
    )
    @Override
    CustomerBean get() throws CustomerFaultException {

        // create customer bean
        CustomerBean customer = new CustomerBean();
        customer.setFirstname("Bill");
        customer.setLastname("Cypher");
        customer.setAge(Integer.MAX_VALUE);

        // return result
        return customer;
    }
}
