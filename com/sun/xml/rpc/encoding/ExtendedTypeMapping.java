package com.sun.xml.rpc.encoding;

import javax.xml.namespace.QName;
import javax.xml.rpc.encoding.TypeMapping;

public interface ExtendedTypeMapping extends TypeMapping {
  Class getJavaType(QName paramQName);
  
  QName getXmlType(Class paramClass);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\ExtendedTypeMapping.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */