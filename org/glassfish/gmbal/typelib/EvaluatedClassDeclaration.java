package org.glassfish.gmbal.typelib;

import java.util.List;

public interface EvaluatedClassDeclaration extends EvaluatedDeclaration {
  boolean simpleClass();
  
  void freeze();
  
  List<EvaluatedFieldDeclaration> fields();
  
  void fields(List<EvaluatedFieldDeclaration> paramList);
  
  List<EvaluatedType> instantiations();
  
  void instantiations(List<EvaluatedType> paramList);
  
  List<EvaluatedMethodDeclaration> methods();
  
  void methods(List<EvaluatedMethodDeclaration> paramList);
  
  List<EvaluatedClassDeclaration> inheritance();
  
  void inheritance(List<EvaluatedClassDeclaration> paramList);
  
  Class cls();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedClassDeclaration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */