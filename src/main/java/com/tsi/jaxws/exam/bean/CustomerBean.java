package com.tsi.jaxws.exam.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB class, defining structure of the response.
 * 
 * @author Даниил
 */
@XmlRootElement(name = "customer", namespace = "http://com.tsi.jaxws.exam")
public class CustomerBean {

    /**
     * First name
     */
    private String firstnameElement;

    /** 
     * Last name
     */
    private String lastnameElement;

    /**
     * Age
     */
    private int ageElement;

    @XmlElement(name = "firstname")
    public String getFirstname() {
        return firstnameElement;
    }

    public void setFirstname(String firstname) {
        this.firstnameElement = firstname;
    }

    @XmlElement(name = "lastname")
    public String getLastname() {
        return lastnameElement;
    }

    public void setLastname(String lastname) {
        this.lastnameElement = lastname;
    }

    @XmlElement(name = "age")
    public int getAge() {
        return ageElement;
    }

    public void setAge(int age) {
        this.ageElement = age;
    }

}
