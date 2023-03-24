package com.sun.xml.rpc.encoding;

import com.sun.xml.rpc.streaming.XMLWriter;
import javax.xml.namespace.QName;
import javax.xml.rpc.encoding.Serializer;

public interface JAXRPCSerializer extends Serializer {
  void serialize(Object paramObject, QName paramQName, SerializerCallback paramSerializerCallback, XMLWriter paramXMLWriter, SOAPSerializationContext paramSOAPSerializationContext);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\JAXRPCSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */