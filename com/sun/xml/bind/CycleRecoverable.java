package com.sun.xml.bind;

import javax.xml.bind.Marshaller;

public interface CycleRecoverable {
  Object onCycleDetected(Context paramContext);
  
  public static interface Context {
    Marshaller getMarshaller();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\CycleRecoverable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */