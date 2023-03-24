package com.sun.xml.ws.api.pipe;

import com.sun.xml.ws.api.message.Packet;

public interface Pipe {
  Packet process(Packet paramPacket);
  
  void preDestroy();
  
  Pipe copy(PipeCloner paramPipeCloner);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\Pipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */