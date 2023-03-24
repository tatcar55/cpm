package org.glassfish.gmbal.typelib;

public interface Visitor<R> {
  R visitEvaluatedType(EvaluatedType paramEvaluatedType);
  
  R visitEvaluatedArrayType(EvaluatedArrayType paramEvaluatedArrayType);
  
  R visitEvaluatedDeclaration(EvaluatedDeclaration paramEvaluatedDeclaration);
  
  R visitEvaluatedClassDeclaration(EvaluatedClassDeclaration paramEvaluatedClassDeclaration);
  
  R visitEvaluatedFieldDeclaration(EvaluatedFieldDeclaration paramEvaluatedFieldDeclaration);
  
  R visitEvaluatedMethodDeclaration(EvaluatedMethodDeclaration paramEvaluatedMethodDeclaration);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\Visitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */