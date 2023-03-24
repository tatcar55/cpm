package com.sun.xml.ws.tx.coord.v11.types;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "RegistrationRequesterPortType", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ObjectFactory.class})
public interface RegistrationRequesterPortType {
  @WebMethod(operationName = "RegisterResponse", action = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06/RegisterResponse")
  @Oneway
  void registerResponse(@WebParam(name = "RegisterResponseOperation", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", partName = "parameters") RegisterResponseType paramRegisterResponseType);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\types\RegistrationRequesterPortType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */