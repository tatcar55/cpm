package com.sun.xml.ws.assembler.dev;

import javax.xml.ws.WebServiceException;

public interface TubelineAssemblyContextUpdater {
  void prepareContext(ClientTubelineAssemblyContext paramClientTubelineAssemblyContext) throws WebServiceException;
  
  void prepareContext(ServerTubelineAssemblyContext paramServerTubelineAssemblyContext) throws WebServiceException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\dev\TubelineAssemblyContextUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */