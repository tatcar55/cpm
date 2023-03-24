package com.sun.xml.rpc.tools.wscompile;

import com.sun.xml.rpc.processor.model.Model;
import com.sun.xml.rpc.processor.model.Port;
import com.sun.xml.rpc.processor.util.IndentingWriter;
import java.io.IOException;

public interface TieHooksIf {
  void writeTieStatic(Model paramModel, Port paramPort, IndentingWriter paramIndentingWriter) throws IOException;
  
  void writeTieStatic(Model paramModel, IndentingWriter paramIndentingWriter) throws IOException;
  
  void preHandlingHook(Model paramModel, IndentingWriter paramIndentingWriter, TieHooksState paramTieHooksState) throws IOException;
  
  void postResponseWritingHook(Model paramModel, IndentingWriter paramIndentingWriter, TieHooksState paramTieHooksState) throws IOException;
  
  public static class TieHooksState {
    public boolean superDone;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wscompile\TieHooksIf.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */