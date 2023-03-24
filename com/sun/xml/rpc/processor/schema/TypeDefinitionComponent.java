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
/*    */ 
/*    */ 
/*    */ public abstract class TypeDefinitionComponent
/*    */   extends Component
/*    */ {
/*    */   private QName _name;
/*    */   
/*    */   public QName getName() {
/* 40 */     return this._name;
/*    */   }
/*    */   
/*    */   public void setName(QName name) {
/* 44 */     this._name = name;
/*    */   }
/*    */   
/*    */   public boolean isSimple() {
/* 48 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isComplex() {
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\TypeDefinitionComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */