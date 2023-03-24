package com.sun.xml.rpc.encoding;

import com.sun.xml.rpc.streaming.XMLReader;
import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.rpc.encoding.Deserializer;

public interface JAXRPCDeserializer extends Deserializer {
  Object deserialize(QName paramQName, XMLReader paramXMLReader, SOAPDeserializationContext paramSOAPDeserializationContext);
  
  Object deserialize(DataHandler paramDataHandler, SOAPDeserializationContext paramSOAPDeserializationContext);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\JAXRPCDeserializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */