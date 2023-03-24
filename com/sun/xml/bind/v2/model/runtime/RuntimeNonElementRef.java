package com.sun.xml.bind.v2.model.runtime;

import com.sun.xml.bind.v2.model.core.NonElementRef;
import com.sun.xml.bind.v2.runtime.Transducer;
import java.lang.reflect.Type;

public interface RuntimeNonElementRef extends NonElementRef<Type, Class> {
  RuntimeNonElement getTarget();
  
  RuntimePropertyInfo getSource();
  
  Transducer getTransducer();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\runtime\RuntimeNonElementRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */