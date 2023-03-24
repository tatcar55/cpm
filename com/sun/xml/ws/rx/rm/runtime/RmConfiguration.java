package com.sun.xml.ws.rx.rm.runtime;

import com.sun.xml.ws.rx.RxConfiguration;
import com.sun.xml.ws.rx.rm.api.ReliableMessagingFeature;

public interface RmConfiguration extends RxConfiguration {
  ReliableMessagingFeature getRmFeature();
  
  RmRuntimeVersion getRuntimeVersion();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\RmConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */