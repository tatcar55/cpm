package javax.xml.ws;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.Source;

public interface LogicalMessage {
  Source getPayload();
  
  void setPayload(Source paramSource);
  
  Object getPayload(JAXBContext paramJAXBContext);
  
  void setPayload(Object paramObject, JAXBContext paramJAXBContext);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\LogicalMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */