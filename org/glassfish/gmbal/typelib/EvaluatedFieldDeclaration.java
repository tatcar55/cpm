package org.glassfish.gmbal.typelib;

import java.lang.reflect.Field;

public interface EvaluatedFieldDeclaration extends EvaluatedAccessibleDeclaration {
  EvaluatedType fieldType();
  
  Field field();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedFieldDeclaration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */