package com.sun.xml.ws.api.wsdl.parser;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.xml.sax.EntityResolver;

public abstract class MetadataResolverFactory {
  @NotNull
  public abstract MetaDataResolver metadataResolver(@Nullable EntityResolver paramEntityResolver);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\wsdl\parser\MetadataResolverFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */