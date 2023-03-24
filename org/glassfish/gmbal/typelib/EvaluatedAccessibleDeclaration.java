package org.glassfish.gmbal.typelib;

import java.lang.reflect.AccessibleObject;

public interface EvaluatedAccessibleDeclaration extends EvaluatedDeclaration {
  AccessibleObject accessible();
  
  EvaluatedClassDeclaration containingClass();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedAccessibleDeclaration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */