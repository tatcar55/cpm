package com.sun.xml.ws.api.pipe;

import com.sun.istack.NotNull;

public interface TubelineAssembler {
  @NotNull
  Tube createClient(@NotNull ClientTubeAssemblerContext paramClientTubeAssemblerContext);
  
  @NotNull
  Tube createServer(@NotNull ServerTubeAssemblerContext paramServerTubeAssemblerContext);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\TubelineAssembler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */