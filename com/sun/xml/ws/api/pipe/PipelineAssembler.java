package com.sun.xml.ws.api.pipe;

import com.sun.istack.NotNull;

public interface PipelineAssembler {
  @NotNull
  Pipe createClient(@NotNull ClientPipeAssemblerContext paramClientPipeAssemblerContext);
  
  @NotNull
  Pipe createServer(@NotNull ServerPipeAssemblerContext paramServerPipeAssemblerContext);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\PipelineAssembler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */