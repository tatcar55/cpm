package com.sun.xml.ws.rx.mc.runtime;

import com.sun.xml.ws.rx.RxConfiguration;
import com.sun.xml.ws.rx.mc.api.MakeConnectionSupportedFeature;

public interface McConfiguration extends RxConfiguration {
  MakeConnectionSupportedFeature getFeature();
  
  McRuntimeVersion getRuntimeVersion();
  
  String getUniqueEndpointId();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\McConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */