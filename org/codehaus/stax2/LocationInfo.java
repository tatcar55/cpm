package org.codehaus.stax2;

import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;

public interface LocationInfo {
  long getStartingByteOffset();
  
  long getStartingCharOffset();
  
  long getEndingByteOffset() throws XMLStreamException;
  
  long getEndingCharOffset() throws XMLStreamException;
  
  Location getLocation();
  
  XMLStreamLocation2 getStartLocation();
  
  XMLStreamLocation2 getCurrentLocation();
  
  XMLStreamLocation2 getEndLocation() throws XMLStreamException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\codehaus\stax2\LocationInfo.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */