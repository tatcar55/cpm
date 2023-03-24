package com.sun.org.apache.xml.internal.security.keys.content;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MgmtData extends SignatureElementProxy implements KeyInfoContent {
  public MgmtData(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
  }
  
  public MgmtData(Document paramDocument, String paramString) {
    super(paramDocument);
    addText(paramString);
  }
  
  public String getMgmtData() {
    return getTextFromTextChild();
  }
  
  public String getBaseLocalName() {
    return "MgmtData";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\content\MgmtData.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */