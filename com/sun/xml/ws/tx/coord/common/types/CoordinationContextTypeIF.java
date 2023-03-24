package com.sun.xml.ws.tx.coord.common.types;

import java.util.Map;
import javax.xml.namespace.QName;

public interface CoordinationContextTypeIF<T extends javax.xml.ws.EndpointReference, E, I, C> {
  BaseIdentifier<I> getIdentifier();
  
  void setIdentifier(BaseIdentifier<I> paramBaseIdentifier);
  
  BaseExpires<E> getExpires();
  
  void setExpires(BaseExpires<E> paramBaseExpires);
  
  String getCoordinationType();
  
  void setCoordinationType(String paramString);
  
  T getRegistrationService();
  
  void setRegistrationService(T paramT);
  
  Map<QName, String> getOtherAttributes();
  
  C getDelegate();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\types\CoordinationContextTypeIF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */