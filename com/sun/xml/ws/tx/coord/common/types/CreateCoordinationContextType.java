package com.sun.xml.ws.tx.coord.common.types;

import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public interface CreateCoordinationContextType {
  BaseExpires getExpires();
  
  void setExpires(BaseExpires paramBaseExpires);
  
  CurrentContextIF getCurrentContext();
  
  void setCurrentContext(CurrentContextIF paramCurrentContextIF);
  
  String getCoordinationType();
  
  void setCoordinationType(String paramString);
  
  List<Object> getAny();
  
  Map<QName, String> getOtherAttributes();
  
  public static interface CurrentContextIF<T extends javax.xml.ws.EndpointReference, E, I, C> extends CoordinationContextTypeIF<T, E, I, C> {
    List<Object> getAny();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\types\CreateCoordinationContextType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */