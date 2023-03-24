package org.glassfish.gmbal.typelib;

import java.lang.reflect.Method;
import java.util.List;

public interface EvaluatedMethodDeclaration extends EvaluatedAccessibleDeclaration {
  List<EvaluatedType> parameterTypes();
  
  EvaluatedType returnType();
  
  Method method();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedMethodDeclaration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */