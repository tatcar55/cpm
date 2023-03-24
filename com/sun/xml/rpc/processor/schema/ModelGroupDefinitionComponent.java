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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelGroupDefinitionComponent
/*    */   extends Component
/*    */ {
/*    */   private QName _name;
/*    */   private ModelGroupComponent _modelGroup;
/*    */   private AnnotationComponent _annotation;
/*    */   
/*    */   public QName getName() {
/* 40 */     return this._name;
/*    */   }
/*    */   
/*    */   public void accept(ComponentVisitor visitor) throws Exception {
/* 44 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\ModelGroupDefinitionComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */