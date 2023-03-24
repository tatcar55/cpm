package com.sun.xml.ws.rx.rm.policy;

import com.sun.istack.NotNull;
import com.sun.xml.ws.rx.rm.api.ReliableMessagingFeatureBuilder;
import com.sun.xml.ws.rx.rm.api.RmProtocolVersion;
import javax.xml.namespace.QName;

public interface RmConfigurator {
  @NotNull
  QName getName();
  
  boolean isCompatibleWith(RmProtocolVersion paramRmProtocolVersion);
  
  @NotNull
  ReliableMessagingFeatureBuilder update(@NotNull ReliableMessagingFeatureBuilder paramReliableMessagingFeatureBuilder);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\RmConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */