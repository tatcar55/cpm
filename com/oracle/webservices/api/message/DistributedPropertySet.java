package com.oracle.webservices.api.message;

import com.sun.istack.Nullable;
import java.util.Map;

public interface DistributedPropertySet extends PropertySet {
  @Nullable
  <T extends PropertySet> T getSatellite(Class<T> paramClass);
  
  Map<Class<? extends PropertySet>, PropertySet> getSatellites();
  
  void addSatellite(PropertySet paramPropertySet);
  
  void addSatellite(Class<? extends PropertySet> paramClass, PropertySet paramPropertySet);
  
  void removeSatellite(PropertySet paramPropertySet);
  
  void copySatelliteInto(MessageContext paramMessageContext);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\message\DistributedPropertySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */