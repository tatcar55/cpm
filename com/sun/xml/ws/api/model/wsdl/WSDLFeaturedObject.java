package com.sun.xml.ws.api.model.wsdl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.WSFeatureList;
import javax.xml.ws.WebServiceFeature;

public interface WSDLFeaturedObject extends WSDLObject {
  @Nullable
  <F extends WebServiceFeature> F getFeature(@NotNull Class<F> paramClass);
  
  @NotNull
  WSFeatureList getFeatures();
  
  void addFeature(@NotNull WebServiceFeature paramWebServiceFeature);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLFeaturedObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */