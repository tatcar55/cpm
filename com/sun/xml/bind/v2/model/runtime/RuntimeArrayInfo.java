package com.sun.xml.bind.v2.model.runtime;

import com.sun.xml.bind.v2.model.core.ArrayInfo;
import java.lang.reflect.Type;

public interface RuntimeArrayInfo extends ArrayInfo<Type, Class>, RuntimeNonElement {
  Class getType();
  
  RuntimeNonElement getItemType();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\runtime\RuntimeArrayInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */