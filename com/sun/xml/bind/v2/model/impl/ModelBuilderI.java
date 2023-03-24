package com.sun.xml.bind.v2.model.impl;

import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
import com.sun.xml.bind.v2.model.nav.Navigator;

public interface ModelBuilderI<T, C, F, M> {
  Navigator<T, C, F, M> getNavigator();
  
  AnnotationReader<T, C, F, M> getReader();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\ModelBuilderI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */