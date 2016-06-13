package com.tsi.jaxws.exam.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB class, used as a wrapper for response.
 * 
 * @author Даниил
 */
@XmlRootElement(name = "customer-response", namespace = "http://com.tsi.jaxws.exam")
public class CustomerResponse {

    /**
     * Actual response
     */
    private CustomerBean customerElement;

    @XmlElement(name = "customer", namespace = "http://com.tsi.jaxws.exam")
    public CustomerBean getCustomer() {
        return customerElement;
    }

    public void setCustomer(CustomerBean customer) {
        this.customerElement = customer;
    }

}
