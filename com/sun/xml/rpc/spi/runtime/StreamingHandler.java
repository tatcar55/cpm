package com.sun.xml.rpc.spi.runtime;

import java.lang.reflect.Method;
import javax.xml.soap.SOAPMessage;

public interface StreamingHandler {
  int getOpcodeForRequestMessage(SOAPMessage paramSOAPMessage);
  
  Method getMethodForOpcode(int paramInt) throws ClassNotFoundException, NoSuchMethodException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\runtime\StreamingHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */