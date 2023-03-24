package com.sun.org.apache.xml.internal.security.utils.resolver.implementations;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.w3c.dom.Attr;

public class ResolverAnonymous extends ResourceResolverSpi {
  private XMLSignatureInput _input = null;
  
  public ResolverAnonymous(String paramString) throws FileNotFoundException, IOException {
    this._input = new XMLSignatureInput(new FileInputStream(paramString));
  }
  
  public ResolverAnonymous(InputStream paramInputStream) {
    this._input = new XMLSignatureInput(paramInputStream);
  }
  
  public XMLSignatureInput engineResolve(Attr paramAttr, String paramString) {
    return this._input;
  }
  
  public boolean engineCanResolve(Attr paramAttr, String paramString) {
    return (paramAttr == null);
  }
  
  public String[] engineGetPropertyKeys() {
    return new String[0];
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\resolver\implementations\ResolverAnonymous.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */