package org.glassfish.external.probe.provider;

public interface StatsProviderManagerDelegate {
  void register(StatsProviderInfo paramStatsProviderInfo);
  
  void unregister(Object paramObject);
  
  boolean hasListeners(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\probe\provider\StatsProviderManagerDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */