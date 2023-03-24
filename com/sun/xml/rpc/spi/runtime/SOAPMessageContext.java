package com.sun.xml.rpc.spi.runtime;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;

public interface SOAPMessageContext extends SOAPMessageContext {
  SOAPMessage createMessage(MimeHeaders paramMimeHeaders, InputStream paramInputStream) throws IOException;
  
  void writeInternalServerErrorResponse();
  
  void writeSimpleErrorResponse(QName paramQName, String paramString);
  
  boolean isFailure();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\runtime\SOAPMessageContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */