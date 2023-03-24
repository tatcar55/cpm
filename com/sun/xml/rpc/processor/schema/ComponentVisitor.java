package com.sun.xml.rpc.processor.schema;

public interface ComponentVisitor {
  void visit(AnnotationComponent paramAnnotationComponent) throws Exception;
  
  void visit(AttributeDeclarationComponent paramAttributeDeclarationComponent) throws Exception;
  
  void visit(AttributeGroupDefinitionComponent paramAttributeGroupDefinitionComponent) throws Exception;
  
  void visit(AttributeUseComponent paramAttributeUseComponent) throws Exception;
  
  void visit(ComplexTypeDefinitionComponent paramComplexTypeDefinitionComponent) throws Exception;
  
  void visit(ElementDeclarationComponent paramElementDeclarationComponent) throws Exception;
  
  void visit(IdentityConstraintDefinitionComponent paramIdentityConstraintDefinitionComponent) throws Exception;
  
  void visit(ModelGroupComponent paramModelGroupComponent) throws Exception;
  
  void visit(ModelGroupDefinitionComponent paramModelGroupDefinitionComponent) throws Exception;
  
  void visit(NotationDeclarationComponent paramNotationDeclarationComponent) throws Exception;
  
  void visit(ParticleComponent paramParticleComponent) throws Exception;
  
  void visit(SimpleTypeDefinitionComponent paramSimpleTypeDefinitionComponent) throws Exception;
  
  void visit(WildcardComponent paramWildcardComponent) throws Exception;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\ComponentVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */