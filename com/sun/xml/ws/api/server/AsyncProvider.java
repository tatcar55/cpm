package com.sun.xml.ws.api.server;

import com.sun.istack.NotNull;
import javax.xml.ws.WebServiceContext;

public interface AsyncProvider<T> {
  void invoke(@NotNull T paramT, @NotNull AsyncProviderCallback<T> paramAsyncProviderCallback, @NotNull WebServiceContext paramWebServiceContext);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\AsyncProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */