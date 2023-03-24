package javax.xml.soap;

import java.util.Iterator;
import javax.xml.namespace.QName;

public interface Detail extends SOAPFaultElement {
  DetailEntry addDetailEntry(Name paramName) throws SOAPException;
  
  DetailEntry addDetailEntry(QName paramQName) throws SOAPException;
  
  Iterator getDetailEntries();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\soap\Detail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */