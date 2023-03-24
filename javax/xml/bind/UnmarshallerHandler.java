package javax.xml.bind;

import org.xml.sax.ContentHandler;

public interface UnmarshallerHandler extends ContentHandler {
  Object getResult() throws JAXBException, IllegalStateException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\UnmarshallerHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */