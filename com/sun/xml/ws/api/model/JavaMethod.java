package com.sun.xml.ws.api.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.model.soap.SOAPBinding;
import java.lang.reflect.Method;
import javax.xml.namespace.QName;

public interface JavaMethod {
  SEIModel getOwner();
  
  @NotNull
  Method getMethod();
  
  @NotNull
  Method getSEIMethod();
  
  MEP getMEP();
  
  SOAPBinding getBinding();
  
  @NotNull
  String getOperationName();
  
  @NotNull
  String getRequestMessageName();
  
  @Nullable
  String getResponseMessageName();
  
  @Nullable
  QName getRequestPayloadName();
  
  @Nullable
  QName getResponsePayloadName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\JavaMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */