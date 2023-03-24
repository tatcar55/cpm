/*    */ package com.sun.xml.rpc.processor.schema;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributeDeclarationComponent
/*    */   extends Component
/*    */ {
/*    */   private QName _name;
/*    */   private SimpleTypeDefinitionComponent _typeDefinition;
/*    */   private ComplexTypeDefinitionComponent _scope;
/*    */   private String _value;
/*    */   private Symbol _valueKind;
/*    */   private AnnotationComponent _annotation;
/*    */   
/*    */   public QName getName() {
/* 40 */     return this._name;
/*    */   }
/*    */   
/*    */   public void setName(QName name) {
/* 44 */     this._name = name;
/*    */   }
/*    */   
/*    */   public SimpleTypeDefinitionComponent getTypeDefinition() {
/* 48 */     return this._typeDefinition;
/*    */   }
/*    */   
/*    */   public void setTypeDefinition(SimpleTypeDefinitionComponent c) {
/* 52 */     this._typeDefinition = c;
/*    */   }
/*    */   
/*    */   public ComplexTypeDefinitionComponent getScope() {
/* 56 */     return this._scope;
/*    */   }
/*    */   
/*    */   public void setScope(ComplexTypeDefinitionComponent c) {
/* 60 */     this._scope = c;
/*    */   }
/*    */   
/*    */   public void setValue(String s) {
/* 64 */     this._value = s;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 69 */     return this._value;
/*    */   }
/*    */   
/*    */   public void setValueKind(Symbol s) {
/* 73 */     this._valueKind = s;
/*    */   }
/*    */ 
/*    */   
/*    */   public Symbol getValueKind() {
/* 78 */     return this._valueKind;
/*    */   }
/*    */ 
/*    */   
/*    */   public AnnotationComponent getAnnotation() {
/* 83 */     return this._annotation;
/*    */   }
/*    */   
/*    */   public void setAnnotation(AnnotationComponent c) {
/* 87 */     this._annotation = c;
/*    */   }
/*    */   
/*    */   public void accept(ComponentVisitor visitor) throws Exception {
/* 91 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\AttributeDeclarationComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */