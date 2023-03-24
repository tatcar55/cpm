package com.sun.xml.ws.transport.http.servlet;

import org.glassfish.external.probe.provider.annotations.Probe;
import org.glassfish.external.probe.provider.annotations.ProbeParam;
import org.glassfish.external.probe.provider.annotations.ProbeProvider;

@ProbeProvider(moduleProviderName = "glassfish", moduleName = "webservices", probeProviderName = "servlet-ri")
public class JAXWSRIServletProbeProvider {
  @Probe(name = "startedEvent")
  public void startedEvent(@ProbeParam("endpointAddress") String endpointAddress) {}
  
  @Probe(name = "endedEvent")
  public void endedEvent(@ProbeParam("endpointAddress") String endpointAddress) {}
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\JAXWSRIServletProbeProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */