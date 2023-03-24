package com.sun.xml.rpc.client.dii;

import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
import com.sun.xml.rpc.encoding.JAXRPCSerializer;
import com.sun.xml.rpc.encoding.soap.SOAPResponseStructure;

public interface CallInvoker {
  SOAPResponseStructure doInvoke(CallRequest paramCallRequest, JAXRPCSerializer paramJAXRPCSerializer, JAXRPCDeserializer paramJAXRPCDeserializer1, JAXRPCDeserializer paramJAXRPCDeserializer2) throws Exception;
  
  void doInvokeOneWay(CallRequest paramCallRequest, JAXRPCSerializer paramJAXRPCSerializer) throws Exception;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\CallInvoker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */