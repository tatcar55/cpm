package com.sun.xml.ws.api.server;

import com.sun.istack.NotNull;

public interface ServiceDefinition extends Iterable<SDDocument> {
  @NotNull
  SDDocument getPrimary();
  
  void addFilter(@NotNull SDDocumentFilter paramSDDocumentFilter);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\ServiceDefinition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */