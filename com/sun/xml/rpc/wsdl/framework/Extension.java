/*    */ package com.sun.xml.rpc.wsdl.framework;
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
/*    */ 
/*    */ 
/*    */ public abstract class Extension
/*    */   extends Entity
/*    */ {
/*    */   private Extensible _parent;
/*    */   
/*    */   public Extensible getParent() {
/* 40 */     return this._parent;
/*    */   }
/*    */   
/*    */   public void setParent(Extensible parent) {
/* 44 */     this._parent = parent;
/*    */   }
/*    */   
/*    */   public void accept(ExtensionVisitor visitor) throws Exception {
/* 48 */     visitor.preVisit(this);
/* 49 */     visitor.postVisit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\Extension.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */