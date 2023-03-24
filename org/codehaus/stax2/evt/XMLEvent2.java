package org.codehaus.stax2.evt;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.codehaus.stax2.XMLStreamWriter2;

public interface XMLEvent2 extends XMLEvent {
  void writeUsing(XMLStreamWriter2 paramXMLStreamWriter2) throws XMLStreamException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\codehaus\stax2\evt\XMLEvent2.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */