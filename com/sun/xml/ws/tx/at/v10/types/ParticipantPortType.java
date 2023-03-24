package com.sun.xml.ws.tx.at.v10.types;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "ParticipantPortType", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ObjectFactory.class})
public interface ParticipantPortType {
  @WebMethod(operationName = "PrepareOperation", action = "http://schemas.xmlsoap.org/ws/2004/10/wsat/Prepare")
  @Oneway
  void prepare(@WebParam(name = "Prepare", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", partName = "parameters") Notification paramNotification);
  
  @WebMethod(operationName = "CommitOperation", action = "http://schemas.xmlsoap.org/ws/2004/10/wsat/Commit")
  @Oneway
  void commit(@WebParam(name = "Commit", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", partName = "parameters") Notification paramNotification);
  
  @WebMethod(operationName = "RollbackOperation", action = "http://schemas.xmlsoap.org/ws/2004/10/wsat/Rollback")
  @Oneway
  void rollback(@WebParam(name = "Rollback", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", partName = "parameters") Notification paramNotification);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v10\types\ParticipantPortType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */