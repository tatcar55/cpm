package com.sun.xml.ws.security.policy;

import com.sun.xml.ws.policy.PolicyAssertion;
import java.util.Iterator;

public interface CallbackHandlerConfiguration {
  Iterator<? extends PolicyAssertion> getCallbackHandlers();
  
  String getTimestampTimeout();
  
  String getUseXWSSCallbacks();
  
  String getiterationsForPDK();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\CallbackHandlerConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */