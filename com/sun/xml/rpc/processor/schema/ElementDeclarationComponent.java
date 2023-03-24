/*    */ package com.sun.xml.rpc.processor.schema;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ElementDeclarationComponent
/*    */   extends Component
/*    */ {
/*    */   private QName _name;
/*    */   private TypeDefinitionComponent _typeDefinition;
/*    */   private ComplexTypeDefinitionComponent _scope;
/*    */   private String _value;
/*    */   private Symbol _valueKind;
/*    */   private boolean _nillable;
/*    */   private List _identityConstraintDefinitions;
/*    */   private ElementDeclarationComponent _substitutionGroupAffiliation;
/*    */   private Set _substitutionGroupExclusions;
/*    */   private Set _disallowedSubstitutions;
/*    */   private boolean _abstract;
/*    */   private AnnotationComponent _annotation;
/*    */   
/*    */   public QName getName() {
/* 43 */     return this._name;
/*    */   }
/*    */   
/*    */   public void setName(QName n) {
/* 47 */     this._name = n;
/*    */   }
/*    */   
/*    */   public TypeDefinitionComponent getTypeDefinition() {
/* 51 */     return this._typeDefinition;
/*    */   }
/*    */   
/*    */   public void setTypeDefinition(TypeDefinitionComponent c) {
/* 55 */     this._typeDefinition = c;
/*    */   }
/*    */   
/*    */   public void accept(ComponentVisitor visitor) throws Exception {
/* 59 */     visitor.visit(this);
/*    */   }
/*    */   
/*    */   public ComplexTypeDefinitionComponent getScope() {
/* 63 */     return this._scope;
/*    */   }
/*    */   
/*    */   public void setScope(ComplexTypeDefinitionComponent c) {
/* 67 */     this._scope = c;
/*    */   }
/*    */   
/*    */   public boolean isNillable() {
/* 71 */     return this._nillable;
/*    */   }
/*    */   
/*    */   public void setNillable(boolean b) {
/* 75 */     this._nillable = b;
/*    */   }
/*    */   
/*    */   public void setValue(String s) {
/* 79 */     this._value = s;
/*    */   }
/*    */   
/*    */   public void setValueKind(Symbol s) {
/* 83 */     this._valueKind = s;
/*    */   }
/*    */   
/*    */   public void setDisallowedSubstitutions(Set s) {
/* 87 */     this._disallowedSubstitutions = s;
/*    */   }
/*    */   
/*    */   public void setSubstitutionsGroupExclusions(Set s) {
/* 91 */     this._substitutionGroupExclusions = s;
/*    */   }
/*    */   
/*    */   public void setAbstract(boolean b) {
/* 95 */     this._abstract = b;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\ElementDeclarationComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */