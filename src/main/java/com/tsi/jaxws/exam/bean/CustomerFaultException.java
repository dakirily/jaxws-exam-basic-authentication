package com.tsi.jaxws.exam.bean;

import javax.xml.ws.WebFault;

/**
 * Web service exception.
 * 
 * Contains JAXB structure CustomerFault, will be mapped to 
 * /Envelope/Body/Fault/detail/CustomerFault
 * 
 * @author Даниил
 */
@WebFault(name = "CustomerFault", targetNamespace = "http://com.tsi.jaxws.exam")
public class CustomerFaultException extends Exception {

    /**
     * CustomerFault JAXB structure
     */
    private final CustomerFault faultInfo;

    public CustomerFaultException(String message) {
        faultInfo = new CustomerFault();
        faultInfo.setMessage(message);
    }
    
    public CustomerFaultException(String message, CustomerFault faultInfo) {
        this.faultInfo = faultInfo;
        this.faultInfo.setMessage(message);
    }

    public CustomerFaultException(String message, CustomerFault faultInfo, Throwable cause) {
        this.faultInfo = faultInfo;
        this.faultInfo.setMessage(message);
    }

    public CustomerFault getFaultInfo() {
        return faultInfo;
    }
}
