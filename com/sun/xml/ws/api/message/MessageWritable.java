package com.sun.xml.ws.api.message;

import com.oracle.webservices.api.message.ContentType;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.ws.soap.MTOMFeature;

public interface MessageWritable {
  ContentType getContentType();
  
  ContentType writeTo(OutputStream paramOutputStream) throws IOException;
  
  void setMTOMConfiguration(MTOMFeature paramMTOMFeature);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\MessageWritable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */