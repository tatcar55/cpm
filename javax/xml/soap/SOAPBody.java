package javax.xml.soap;

import java.util.Locale;
import javax.xml.namespace.QName;
import org.w3c.dom.Document;

public interface SOAPBody extends SOAPElement {
  SOAPFault addFault() throws SOAPException;
  
  SOAPFault addFault(Name paramName, String paramString, Locale paramLocale) throws SOAPException;
  
  SOAPFault addFault(QName paramQName, String paramString, Locale paramLocale) throws SOAPException;
  
  SOAPFault addFault(Name paramName, String paramString) throws SOAPException;
  
  SOAPFault addFault(QName paramQName, String paramString) throws SOAPException;
  
  boolean hasFault();
  
  SOAPFault getFault();
  
  SOAPBodyElement addBodyElement(Name paramName) throws SOAPException;
  
  SOAPBodyElement addBodyElement(QName paramQName) throws SOAPException;
  
  SOAPBodyElement addDocument(Document paramDocument) throws SOAPException;
  
  Document extractContentAsDocument() throws SOAPException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\soap\SOAPBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */