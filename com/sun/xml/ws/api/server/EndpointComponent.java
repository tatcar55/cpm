package com.sun.xml.ws.api.server;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

public interface EndpointComponent {
  @Nullable
  <T> T getSPI(@NotNull Class<T> paramClass);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\EndpointComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */