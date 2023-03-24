package org.jcp.xml.dsig.internal.dom;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import java.io.IOException;
import javax.xml.crypto.OctetStreamData;

public class ApacheOctetStreamData extends OctetStreamData implements ApacheData {
  private XMLSignatureInput xi;
  
  public ApacheOctetStreamData(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, IOException {
    super(paramXMLSignatureInput.getOctetStream(), paramXMLSignatureInput.getSourceURI(), paramXMLSignatureInput.getMIMEType());
    this.xi = paramXMLSignatureInput;
  }
  
  public XMLSignatureInput getXMLSignatureInput() {
    return this.xi;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\ApacheOctetStreamData.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */