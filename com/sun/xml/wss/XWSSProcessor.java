package com.sun.xml.wss;

import javax.xml.soap.SOAPMessage;

public interface XWSSProcessor {
  SOAPMessage secureOutboundMessage(ProcessingContext paramProcessingContext) throws XWSSecurityException;
  
  SOAPMessage verifyInboundMessage(ProcessingContext paramProcessingContext) throws XWSSecurityException;
  
  ProcessingContext createProcessingContext(SOAPMessage paramSOAPMessage) throws XWSSecurityException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\XWSSProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */