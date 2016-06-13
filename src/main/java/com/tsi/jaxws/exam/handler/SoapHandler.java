package com.tsi.jaxws.exam.handler;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web service SOAP handler, which performs basic authentication check.
 *
 * It extracts from HTTP request headers ones names "Username" and "Password"
 * and checks, whether username and password corresponds to "Mabel" and
 * "Waddles".
 *
 * If not, a SOAPFaultException will be thrown.
 *
 * @author Даниил
 */
public class SoapHandler implements SOAPHandler<SOAPMessageContext> {

    /**
     * Internal logger
     */
    private final Logger logger = LoggerFactory.getLogger(SoapHandler.class);

    /**
     * Handles message on both request and response routes.
     *
     * @param context SOAPMessageContext instance
     *
     * @return true / false
     */
    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        // check message direction
        Boolean isRequest = !(Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        // log execution
        logger.info((isRequest ? "Request route. " : "Response route. ") + "Calling SOAP handler " + this.getClass().getSimpleName());

        try {

            if (isRequest) {

                // retrieve HTTP headers from context
                Map httpHeaders = (Map) context.get(MessageContext.HTTP_REQUEST_HEADERS);

                // get username and password
                List userList = (List) httpHeaders.get("Username");
                List passList = (List) httpHeaders.get("Password");

                String username = userList != null
                        ? userList.get(0).toString()
                        : null;
                String password = passList != null
                        ? passList.get(0).toString()
                        : null;

                // when username and password don't match, throw SOAPFaultException
                if (!Objects.equals(username, "Mabel") || !Objects.equals(password, "Waddles")) {
                    SOAPMessage msg = context.getMessage();
                    SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
                    SOAPFault soapFault = soapBody.addFault();
                    soapFault.setFaultString("Not authorized");

                    /*
                     Error message will look like this:
                    
                     <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
                     <S:Body>
                     <S:Fault xmlns:ns4="http://www.w3.org/2003/05/soap-envelope">
                     <faultcode>S:Server</faultcode>
                     <faultstring>Not authorized</faultstring>
                     </S:Fault>
                     </S:Body>
                     </S:Envelope>
                     */
                    throw new SOAPFaultException(soapFault);
                }
            }

        } catch (SOAPException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public Set<QName> getHeaders() {
        return new HashSet<>();
    }

    @Override
    public void close(MessageContext context) {

    }

}
