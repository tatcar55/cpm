package org.codehaus.stax2;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;

public interface XMLEventReader2 extends XMLEventReader {
  boolean hasNextEvent() throws XMLStreamException;
  
  boolean isPropertySupported(String paramString);
  
  boolean setProperty(String paramString, Object paramObject);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\codehaus\stax2\XMLEventReader2.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */