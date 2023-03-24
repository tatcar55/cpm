package com.sun.xml.ws.transport.http;

import com.sun.istack.NotNull;
import java.io.IOException;

public abstract class HttpMetadataPublisher {
  public abstract boolean handleMetadataRequest(@NotNull HttpAdapter paramHttpAdapter, @NotNull WSHTTPConnection paramWSHTTPConnection) throws IOException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\HttpMetadataPublisher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */