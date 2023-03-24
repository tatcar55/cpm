package org.jvnet.staxex;

import java.io.OutputStream;
import javax.activation.DataHandler;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface XMLStreamWriterEx extends XMLStreamWriter {
  void writeBinary(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, String paramString) throws XMLStreamException;
  
  void writeBinary(DataHandler paramDataHandler) throws XMLStreamException;
  
  OutputStream writeBinary(String paramString) throws XMLStreamException;
  
  void writePCDATA(CharSequence paramCharSequence) throws XMLStreamException;
  
  NamespaceContextEx getNamespaceContext();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\staxex\XMLStreamWriterEx.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */