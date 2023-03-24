package org.glassfish.gmbal.typelib;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;

public interface EvaluatedDeclaration extends EvaluatedType {
  <T extends Annotation> T annotation(Class<T> paramClass);
  
  List<Annotation> annotations();
  
  int modifiers();
  
  AnnotatedElement element();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedDeclaration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */