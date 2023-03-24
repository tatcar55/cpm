package javax.xml.ws;

import java.security.Principal;
import javax.xml.ws.handler.MessageContext;
import org.w3c.dom.Element;

public interface WebServiceContext {
  MessageContext getMessageContext();
  
  Principal getUserPrincipal();
  
  boolean isUserInRole(String paramString);
  
  EndpointReference getEndpointReference(Element... paramVarArgs);
  
  <T extends EndpointReference> T getEndpointReference(Class<T> paramClass, Element... paramVarArgs);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\WebServiceContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */