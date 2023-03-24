package com.sun.xml.rpc.server;

import com.sun.xml.rpc.spi.runtime.Tie;
import java.rmi.Remote;

public interface Tie extends Tie {
  void destroy();
  
  Remote getTarget();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\Tie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */