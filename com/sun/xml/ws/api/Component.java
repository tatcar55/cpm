package com.sun.xml.ws.api;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

public interface Component {
  @Nullable
  <S> S getSPI(@NotNull Class<S> paramClass);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\Component.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */