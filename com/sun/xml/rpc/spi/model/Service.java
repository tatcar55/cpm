package com.sun.xml.rpc.spi.model;

import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;

public interface Service extends ModelObject {
  Iterator getPorts();
  
  QName getName();
  
  List getPortsList();
  
  JavaInterface getJavaIntf();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\model\Service.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */