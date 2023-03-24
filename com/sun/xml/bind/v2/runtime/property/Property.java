package com.sun.xml.bind.v2.runtime.property;

import com.sun.xml.bind.api.AccessorException;
import com.sun.xml.bind.v2.model.core.PropertyKind;
import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
import com.sun.xml.bind.v2.runtime.XMLSerializer;
import com.sun.xml.bind.v2.runtime.reflect.Accessor;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.SAXException;

public interface Property<BeanT> extends StructureLoaderBuilder {
  void reset(BeanT paramBeanT) throws AccessorException;
  
  void serializeBody(BeanT paramBeanT, XMLSerializer paramXMLSerializer, Object paramObject) throws SAXException, AccessorException, IOException, XMLStreamException;
  
  void serializeURIs(BeanT paramBeanT, XMLSerializer paramXMLSerializer) throws SAXException, AccessorException;
  
  boolean hasSerializeURIAction();
  
  String getIdValue(BeanT paramBeanT) throws AccessorException, SAXException;
  
  PropertyKind getKind();
  
  Accessor getElementPropertyAccessor(String paramString1, String paramString2);
  
  void wrapUp();
  
  RuntimePropertyInfo getInfo();
  
  boolean isHiddenByOverride();
  
  void setHiddenByOverride(boolean paramBoolean);
  
  String getFieldName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\Property.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */