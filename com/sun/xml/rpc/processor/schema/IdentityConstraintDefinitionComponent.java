/*    */ package com.sun.xml.rpc.processor.schema;
/*    */ 
/*    */ import java.util.List;
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
/*    */ public class IdentityConstraintDefinitionComponent
/*    */   extends Component
/*    */ {
/*    */   private QName _name;
/*    */   private Symbol _identityConstraintCategory;
/*    */   private String _selector;
/*    */   private List _fields;
/*    */   private IdentityConstraintDefinitionComponent _referencedKey;
/*    */   private AnnotationComponent _annotation;
/*    */   
/*    */   public void accept(ComponentVisitor visitor) throws Exception {
/* 42 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\IdentityConstraintDefinitionComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */