package com.sun.xml.ws;

import java.io.Closeable;
import javax.xml.ws.WebServiceException;

public interface Closeable extends Closeable {
  void close() throws WebServiceException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\Closeable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */