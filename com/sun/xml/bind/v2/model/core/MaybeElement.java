package com.sun.xml.bind.v2.model.core;

import javax.xml.namespace.QName;

public interface MaybeElement<T, C> extends NonElement<T, C> {
  boolean isElement();
  
  QName getElementName();
  
  Element<T, C> asElement();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\MaybeElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */