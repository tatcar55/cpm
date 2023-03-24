package com.sun.xml.ws.tx.coord.v10.endpoint;

import com.sun.xml.ws.developer.MemberSubmissionAddressing;
import com.sun.xml.ws.tx.coord.v10.types.RegisterType;
import com.sun.xml.ws.tx.coord.v10.types.RegistrationCoordinatorPortType;
import javax.jws.WebService;
import javax.xml.ws.BindingType;

@WebService(portName = "RegistrationCoordinatorPortTypePort", serviceName = "RegistrationService_V10", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wscoor", wsdlLocation = "/wsdls/wsc10/wscoor.wsdl", endpointInterface = "com.sun.xml.ws.tx.coord.v10.types.RegistrationCoordinatorPortType")
@BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
@MemberSubmissionAddressing
public class RegistrationCoordinatorPortTypePortImpl implements RegistrationCoordinatorPortType {
  public void registerOperation(RegisterType parameters) {}
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\endpoint\RegistrationCoordinatorPortTypePortImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */