package com.sun.xml.rpc.processor;

import com.sun.xml.rpc.util.localization.Localizable;

public interface ProcessorNotificationListener {
  void onError(Localizable paramLocalizable);
  
  void onWarning(Localizable paramLocalizable);
  
  void onInfo(Localizable paramLocalizable);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\ProcessorNotificationListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */