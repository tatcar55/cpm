package com.sun.xml.rpc.tools.wscompile;

import com.sun.xml.rpc.processor.model.Model;
import com.sun.xml.rpc.processor.model.Port;
import com.sun.xml.rpc.processor.util.IndentingWriter;
import java.io.IOException;

public interface StubHooksIf {
  void writeStubStatic(Model paramModel, Port paramPort, IndentingWriter paramIndentingWriter) throws IOException;
  
  void writeStubStatic(Model paramModel, IndentingWriter paramIndentingWriter) throws IOException;
  
  void _preHandlingHook(Model paramModel, IndentingWriter paramIndentingWriter, StubHooksState paramStubHooksState) throws IOException;
  
  void _preRequestSendingHook(Model paramModel, IndentingWriter paramIndentingWriter, StubHooksState paramStubHooksState) throws IOException;
  
  public static class StubHooksState {
    public boolean superDone;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wscompile\StubHooksIf.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */