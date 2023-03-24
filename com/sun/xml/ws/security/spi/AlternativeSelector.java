package com.sun.xml.ws.security.spi;

import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.impl.policy.SecurityPolicy;
import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
import java.util.List;

public interface AlternativeSelector {
  MessagePolicy selectAlternative(ProcessingContext paramProcessingContext, List<MessagePolicy> paramList, SecurityPolicy paramSecurityPolicy);
  
  boolean supportsAlternatives(List<MessagePolicy> paramList);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\spi\AlternativeSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */