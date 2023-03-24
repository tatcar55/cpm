package com.sun.xml.ws.api.pipe;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Packet;

public interface Tube {
  @NotNull
  NextAction processRequest(@NotNull Packet paramPacket);
  
  @NotNull
  NextAction processResponse(@NotNull Packet paramPacket);
  
  @NotNull
  NextAction processException(@NotNull Throwable paramThrowable);
  
  void preDestroy();
  
  Tube copy(TubeCloner paramTubeCloner);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\Tube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */