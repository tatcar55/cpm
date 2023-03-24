package com.oracle.webservices.api.message;

import java.io.IOException;
import java.io.OutputStream;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public interface MessageContext extends DistributedPropertySet {
  SOAPMessage getAsSOAPMessage() throws SOAPException;
  
  SOAPMessage getSOAPMessage() throws SOAPException;
  
  ContentType writeTo(OutputStream paramOutputStream) throws IOException;
  
  ContentType getContentType();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\message\MessageContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */