package com.sun.xml.rpc.wsdl.framework;

import javax.xml.namespace.QName;

public interface ParserListener {
  void ignoringExtension(QName paramQName1, QName paramQName2);
  
  void doneParsingEntity(QName paramQName, Entity paramEntity);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\ParserListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */