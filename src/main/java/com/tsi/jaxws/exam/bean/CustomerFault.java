package com.tsi.jaxws.exam.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * JAXB class, defining structure of the fault.
 * 
 * @author Даниил
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerFault", propOrder = {
    "message"
})
public class CustomerFault {

    /** 
     * Fault message
     */
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }
}
