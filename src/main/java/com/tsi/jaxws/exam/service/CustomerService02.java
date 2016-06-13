package com.tsi.jaxws.exam.service;

import com.tsi.jaxws.exam.bean.CustomerBean;
import com.tsi.jaxws.exam.bean.CustomerFaultException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web service WSDL will be available using URL
 * http://localhost:8080/WebServiceExam03/CustomerService02?wsdl
 *
 * @author Даниил
 */
@WebService(endpointInterface = "com.tsi.jaxws.exam.service.CustomerService", serviceName = "CustomerService02", targetNamespace = "http://com.tsi.jaxws.exam")
public class CustomerService02 implements CustomerService {

    /**
     * WebServiceContext is injected as resource
     */
    @Resource
    private WebServiceContext ctx;

    /**
     * Internal logger
     */
    private final Logger logger = LoggerFactory.getLogger(CustomerService02.class);

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

        // retrieve message context from web service context
        MessageContext msgCtx = ctx.getMessageContext();

        // retrieve HTTP headers from context
        Map httpHeaders = (Map) msgCtx.get(MessageContext.HTTP_REQUEST_HEADERS);

        // get username and password
        List userList = (List) httpHeaders.get("Username");
        List passList = (List) httpHeaders.get("Password");

        String username = userList != null
                ? userList.get(0).toString()
                : null;
        String password = passList != null
                ? passList.get(0).toString()
                : null;

        // when username and password don't match, throw CustomerFaultException
        if (!Objects.equals(username, "Mabel") || !Objects.equals(password, "Waddles")) {
            /*
             Error message will look like this:
            
             <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
             <S:Body>
             <S:Fault xmlns:ns4="http://www.w3.org/2003/05/soap-envelope">
             <faultcode>S:Server</faultcode>
             <faultstring>com.tsi.jaxws.exam.bean.CustomerFaultException</faultstring>
             <detail>
             <ns2:CustomerFault xmlns:ns2="http://com.tsi.jaxws.exam">
             <message>Not authorized</message>
             </ns2:CustomerFault>
             </detail>
             </S:Fault>
             </S:Body>
             </S:Envelope>
             */
            
            logger.error("User not authorized");
            
            throw new CustomerFaultException("Not authorized");
        }

        // create customer bean
        CustomerBean customer = new CustomerBean();
        customer.setFirstname("Bill");
        customer.setLastname("Cypher");
        customer.setAge(Integer.MAX_VALUE);

        // return result
        return customer;
    }
}
