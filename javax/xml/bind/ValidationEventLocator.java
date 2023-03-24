package javax.xml.bind;

import java.net.URL;
import org.w3c.dom.Node;

public interface ValidationEventLocator {
  URL getURL();
  
  int getOffset();
  
  int getLineNumber();
  
  int getColumnNumber();
  
  Object getObject();
  
  Node getNode();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\ValidationEventLocator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */