package com.sun.xml.rpc.encoding;

import com.sun.xml.rpc.streaming.XMLWriter;
import javax.xml.namespace.QName;

public interface SerializerCallback {
  void onStartTag(Object paramObject, QName paramQName, XMLWriter paramXMLWriter, SOAPSerializationContext paramSOAPSerializationContext) throws SerializationException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SerializerCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */