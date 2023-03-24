package com.sun.xml.ws.api.databinding;

import com.oracle.webservices.api.databinding.JavaCallInfo;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.JavaMethod;
import java.lang.reflect.Method;

public interface ClientCallBridge {
  Packet createRequestPacket(JavaCallInfo paramJavaCallInfo);
  
  JavaCallInfo readResponse(Packet paramPacket, JavaCallInfo paramJavaCallInfo) throws Throwable;
  
  Method getMethod();
  
  JavaMethod getOperationModel();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\databinding\ClientCallBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */