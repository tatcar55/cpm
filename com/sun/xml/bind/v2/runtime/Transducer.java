package com.sun.xml.bind.v2.runtime;

import com.sun.istack.NotNull;
import com.sun.xml.bind.api.AccessorException;
import java.io.IOException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.SAXException;

public interface Transducer<ValueT> {
  boolean isDefault();
  
  boolean useNamespace();
  
  void declareNamespace(ValueT paramValueT, XMLSerializer paramXMLSerializer) throws AccessorException;
  
  @NotNull
  CharSequence print(@NotNull ValueT paramValueT) throws AccessorException;
  
  ValueT parse(CharSequence paramCharSequence) throws AccessorException, SAXException;
  
  void writeText(XMLSerializer paramXMLSerializer, ValueT paramValueT, String paramString) throws IOException, SAXException, XMLStreamException, AccessorException;
  
  void writeLeafElement(XMLSerializer paramXMLSerializer, Name paramName, @NotNull ValueT paramValueT, String paramString) throws IOException, SAXException, XMLStreamException, AccessorException;
  
  QName getTypeName(@NotNull ValueT paramValueT);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\Transducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */