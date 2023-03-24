package com.sun.xml.rpc.spi.tools;

import java.io.InputStream;
import java.net.URL;

public interface XMLModelFileFilter {
  boolean isModelFile(InputStream paramInputStream);
  
  boolean isModelFile(URL paramURL);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\tools\XMLModelFileFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */