package com.sun.xml.ws.security.opt.api;

import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSecurityException;

public interface TokenValidator {
  void validate(ProcessingContext paramProcessingContext) throws XWSSecurityException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\api\TokenValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */