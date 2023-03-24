/*    */ package com.sun.xml.rpc.processor.schema;
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
/*    */ 
/*    */ public class AttributeUseComponent
/*    */   extends Component
/*    */ {
/*    */   private boolean _required;
/*    */   private AttributeDeclarationComponent _attributeDeclaration;
/*    */   private String _value;
/*    */   private Symbol _valueKind;
/*    */   private AnnotationComponent _annotation;
/*    */   
/*    */   public boolean isRequired() {
/* 38 */     return this._required;
/*    */   }
/*    */   
/*    */   public void setRequired(boolean b) {
/* 42 */     this._required = b;
/*    */   }
/*    */   
/*    */   public AttributeDeclarationComponent getAttributeDeclaration() {
/* 46 */     return this._attributeDeclaration;
/*    */   }
/*    */   
/*    */   public void setAttributeDeclaration(AttributeDeclarationComponent c) {
/* 50 */     this._attributeDeclaration = c;
/*    */   }
/*    */   
/*    */   public void setValue(String s) {
/* 54 */     this._value = s;
/*    */   }
/*    */   
/*    */   public void setValueKind(Symbol s) {
/* 58 */     this._valueKind = s;
/*    */   }
/*    */   
/*    */   public Symbol getValueKind() {
/* 62 */     return this._valueKind;
/*    */   }
/*    */   
/*    */   public AnnotationComponent getAnnotation() {
/* 66 */     return this._annotation;
/*    */   }
/*    */   
/*    */   public void setAnnotation(AnnotationComponent c) {
/* 70 */     this._annotation = c;
/*    */   }
/*    */   
/*    */   public void accept(ComponentVisitor visitor) throws Exception {
/* 74 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\AttributeUseComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */