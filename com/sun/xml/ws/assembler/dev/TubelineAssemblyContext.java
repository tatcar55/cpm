package com.sun.xml.ws.assembler.dev;

import com.sun.xml.ws.api.pipe.Pipe;
import com.sun.xml.ws.api.pipe.Tube;

public interface TubelineAssemblyContext {
  Pipe getAdaptedTubelineHead();
  
  <T> T getImplementation(Class<T> paramClass);
  
  Tube getTubelineHead();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\dev\TubelineAssemblyContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */