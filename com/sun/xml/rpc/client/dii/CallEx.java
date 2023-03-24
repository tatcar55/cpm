package com.sun.xml.rpc.client.dii;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;

public interface CallEx extends Call {
  QName getPortName();
  
  void setPortName(QName paramQName);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\CallEx.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */