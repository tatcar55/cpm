package com.sun.xml.ws.api;

import com.oracle.webservices.api.message.MessageContextFactory;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.handler.Handler;

public interface WSBinding extends Binding {
  SOAPVersion getSOAPVersion();
  
  AddressingVersion getAddressingVersion();
  
  @NotNull
  BindingID getBindingId();
  
  @NotNull
  List<Handler> getHandlerChain();
  
  boolean isFeatureEnabled(@NotNull Class<? extends WebServiceFeature> paramClass);
  
  boolean isOperationFeatureEnabled(@NotNull Class<? extends WebServiceFeature> paramClass, @NotNull QName paramQName);
  
  @Nullable
  <F extends WebServiceFeature> F getFeature(@NotNull Class<F> paramClass);
  
  @Nullable
  <F extends WebServiceFeature> F getOperationFeature(@NotNull Class<F> paramClass, @NotNull QName paramQName);
  
  @NotNull
  WSFeatureList getFeatures();
  
  @NotNull
  WSFeatureList getOperationFeatures(@NotNull QName paramQName);
  
  @NotNull
  WSFeatureList getInputMessageFeatures(@NotNull QName paramQName);
  
  @NotNull
  WSFeatureList getOutputMessageFeatures(@NotNull QName paramQName);
  
  @NotNull
  WSFeatureList getFaultMessageFeatures(@NotNull QName paramQName1, @NotNull QName paramQName2);
  
  @NotNull
  Set<QName> getKnownHeaders();
  
  boolean addKnownHeader(QName paramQName);
  
  @NotNull
  MessageContextFactory getMessageContextFactory();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\WSBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */