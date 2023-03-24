package com.sun.xml.rpc.processor.generator.writer;

import com.sun.xml.rpc.encoding.InternalEncodingConstants;
import com.sun.xml.rpc.processor.model.AbstractType;

public interface SerializerWriterFactory extends InternalEncodingConstants {
  SerializerWriter createWriter(String paramString, AbstractType paramAbstractType);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\SerializerWriterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */