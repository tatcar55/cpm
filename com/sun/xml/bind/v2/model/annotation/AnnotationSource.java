package com.sun.xml.bind.v2.model.annotation;

import java.lang.annotation.Annotation;

public interface AnnotationSource {
  <A extends Annotation> A readAnnotation(Class<A> paramClass);
  
  boolean hasAnnotation(Class<? extends Annotation> paramClass);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\AnnotationSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */