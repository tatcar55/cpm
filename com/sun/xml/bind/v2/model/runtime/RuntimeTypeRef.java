package com.sun.xml.bind.v2.model.runtime;

import com.sun.xml.bind.v2.model.core.TypeRef;
import java.lang.reflect.Type;

public interface RuntimeTypeRef extends TypeRef<Type, Class>, RuntimeNonElementRef {
  RuntimeNonElement getTarget();
  
  RuntimePropertyInfo getSource();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\runtime\RuntimeTypeRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */