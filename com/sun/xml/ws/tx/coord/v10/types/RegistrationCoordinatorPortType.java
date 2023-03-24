package com.sun.xml.ws.tx.coord.v10.types;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "RegistrationCoordinatorPortType", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wscoor")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ObjectFactory.class})
public interface RegistrationCoordinatorPortType {
  @WebMethod(operationName = "RegisterOperation", action = "http://schemas.xmlsoap.org/ws/2004/10/wscoor/Register")
  @Oneway
  void registerOperation(@WebParam(name = "Register", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wscoor", partName = "parameters") RegisterType paramRegisterType);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\types\RegistrationCoordinatorPortType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */