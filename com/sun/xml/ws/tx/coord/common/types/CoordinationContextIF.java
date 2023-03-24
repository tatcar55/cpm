package com.sun.xml.ws.tx.coord.common.types;

import com.sun.xml.bind.api.JAXBRIContext;
import java.util.List;

public interface CoordinationContextIF<T extends javax.xml.ws.EndpointReference, E, I, C> extends CoordinationContextTypeIF<T, E, I, C> {
  List<Object> getAny();
  
  JAXBRIContext getJAXBRIContext();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\types\CoordinationContextIF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */