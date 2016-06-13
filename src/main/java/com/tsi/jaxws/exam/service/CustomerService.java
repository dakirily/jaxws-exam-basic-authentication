package com.tsi.jaxws.exam.service;

import com.tsi.jaxws.exam.bean.CustomerBean;
import com.tsi.jaxws.exam.bean.CustomerFaultException;
import javax.jws.WebService;

/**
 *
 * @author Даниил
 */
@WebService
public interface CustomerService {
    
    public CustomerBean get() throws CustomerFaultException;
    
}
