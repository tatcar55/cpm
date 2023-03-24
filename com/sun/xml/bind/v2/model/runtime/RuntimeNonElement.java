package com.sun.xml.bind.v2.model.runtime;

import com.sun.xml.bind.v2.model.core.NonElement;
import com.sun.xml.bind.v2.runtime.Transducer;
import java.lang.reflect.Type;

public interface RuntimeNonElement extends NonElement<Type, Class>, RuntimeTypeInfo {
  <V> Transducer<V> getTransducer();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\runtime\RuntimeNonElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */