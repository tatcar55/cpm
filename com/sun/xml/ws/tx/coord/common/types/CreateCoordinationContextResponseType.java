package com.sun.xml.ws.tx.coord.common.types;

import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public interface CreateCoordinationContextResponseType {
  CoordinationContextIF getCoordinationContext();
  
  void setCoordinationContext(CoordinationContextIF paramCoordinationContextIF);
  
  List<Object> getAny();
  
  Map<QName, String> getOtherAttributes();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\types\CreateCoordinationContextResponseType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */