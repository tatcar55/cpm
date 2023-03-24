package com.sun.xml.ws.developer;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.message.Packet;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

public interface StatefulWebServiceManager<T> {
  @NotNull
  <EPR extends EndpointReference> EPR export(Class<EPR> paramClass, T paramT);
  
  @NotNull
  <EPR extends EndpointReference> EPR export(Class<EPR> paramClass, T paramT, @Nullable EPRRecipe paramEPRRecipe);
  
  @NotNull
  W3CEndpointReference export(T paramT);
  
  @NotNull
  <EPR extends EndpointReference> EPR export(Class<EPR> paramClass, @NotNull WebServiceContext paramWebServiceContext, T paramT);
  
  @NotNull
  <EPR extends EndpointReference> EPR export(Class<EPR> paramClass, @NotNull Packet paramPacket, T paramT);
  
  @NotNull
  <EPR extends EndpointReference> EPR export(Class<EPR> paramClass, @NotNull Packet paramPacket, T paramT, EPRRecipe paramEPRRecipe);
  
  @NotNull
  <EPR extends EndpointReference> EPR export(Class<EPR> paramClass, String paramString, T paramT);
  
  void unexport(@Nullable T paramT);
  
  @Nullable
  T resolve(@NotNull EndpointReference paramEndpointReference);
  
  void setFallbackInstance(T paramT);
  
  void setTimeout(long paramLong, @Nullable Callback<T> paramCallback);
  
  void touch(T paramT);
  
  public static interface Callback<T> {
    void onTimeout(@NotNull T param1T, @NotNull StatefulWebServiceManager<T> param1StatefulWebServiceManager);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\StatefulWebServiceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */