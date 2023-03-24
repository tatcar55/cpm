package com.sun.xml.bind.v2.model.runtime;

import com.sun.xml.bind.v2.model.core.ElementInfo;
import java.lang.reflect.Type;
import javax.xml.bind.JAXBElement;

public interface RuntimeElementInfo extends ElementInfo<Type, Class>, RuntimeElement {
  RuntimeClassInfo getScope();
  
  RuntimeElementPropertyInfo getProperty();
  
  Class<? extends JAXBElement> getType();
  
  RuntimeNonElement getContentType();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\runtime\RuntimeElementInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */