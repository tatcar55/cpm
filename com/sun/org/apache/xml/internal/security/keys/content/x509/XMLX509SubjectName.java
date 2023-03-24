package com.sun.org.apache.xml.internal.security.keys.content.x509;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.RFC2253Parser;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import java.security.cert.X509Certificate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLX509SubjectName extends SignatureElementProxy implements XMLX509DataContent {
  public XMLX509SubjectName(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
  }
  
  public XMLX509SubjectName(Document paramDocument, String paramString) {
    super(paramDocument);
    addText(paramString);
  }
  
  public XMLX509SubjectName(Document paramDocument, X509Certificate paramX509Certificate) {
    this(paramDocument, RFC2253Parser.normalize(paramX509Certificate.getSubjectDN().getName()));
  }
  
  public String getSubjectName() {
    return RFC2253Parser.normalize(getTextFromTextChild());
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == null)
      return false; 
    if (!getClass().getName().equals(paramObject.getClass().getName()))
      return false; 
    XMLX509SubjectName xMLX509SubjectName = (XMLX509SubjectName)paramObject;
    String str1 = xMLX509SubjectName.getSubjectName();
    String str2 = getSubjectName();
    return str2.equals(str1);
  }
  
  public int hashCode() {
    return 52;
  }
  
  public String getBaseLocalName() {
    return "X509SubjectName";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\content\x509\XMLX509SubjectName.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */