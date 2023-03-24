package com.sun.xml.ws.api;

import com.sun.istack.NotNull;

public interface ComponentEx extends Component {
  @NotNull
  <S> Iterable<S> getIterableSPI(@NotNull Class<S> paramClass);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\ComponentEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */