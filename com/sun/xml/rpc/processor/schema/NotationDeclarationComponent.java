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
/*    */ public class NotationDeclarationComponent
/*    */   extends Component
/*    */ {
/*    */   private QName _name;
/*    */   private String _systemIdentifier;
/*    */   private String _publicIdentifier;
/*    */   private AnnotationComponent _annotation;
/*    */   
/*    */   public void accept(ComponentVisitor visitor) throws Exception {
/* 39 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\NotationDeclarationComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */