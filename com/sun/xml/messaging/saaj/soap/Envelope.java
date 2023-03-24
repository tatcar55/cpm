package com.sun.xml.messaging.saaj.soap;

import java.io.IOException;
import java.io.OutputStream;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.transform.Source;

public interface Envelope extends SOAPEnvelope {
  Source getContent();
  
  void output(OutputStream paramOutputStream) throws IOException;
  
  void output(OutputStream paramOutputStream, boolean paramBoolean) throws IOException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\Envelope.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */