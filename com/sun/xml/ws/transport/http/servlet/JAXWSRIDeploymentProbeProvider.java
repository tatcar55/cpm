package com.sun.xml.ws.transport.http.servlet;

import org.glassfish.external.probe.provider.annotations.Probe;
import org.glassfish.external.probe.provider.annotations.ProbeParam;
import org.glassfish.external.probe.provider.annotations.ProbeProvider;

@ProbeProvider(moduleProviderName = "glassfish", moduleName = "webservices", probeProviderName = "deployment-ri")
public class JAXWSRIDeploymentProbeProvider {
  @Probe(name = "deploy", hidden = true)
  public void deploy(@ProbeParam("adapter") ServletAdapter adpater) {}
  
  @Probe(name = "undeploy", hidden = true)
  public void undeploy(@ProbeParam("adapter") ServletAdapter adapter) {}
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\JAXWSRIDeploymentProbeProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */