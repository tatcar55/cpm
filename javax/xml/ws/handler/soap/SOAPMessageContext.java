package javax.xml.ws.handler.soap;

import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;

public interface SOAPMessageContext extends MessageContext {
  SOAPMessage getMessage();
  
  void setMessage(SOAPMessage paramSOAPMessage);
  
  Object[] getHeaders(QName paramQName, JAXBContext paramJAXBContext, boolean paramBoolean);
  
  Set<String> getRoles();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\handler\soap\SOAPMessageContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */