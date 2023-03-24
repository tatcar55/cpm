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
/*    */ public abstract class GlobalEntity
/*    */   extends Entity
/*    */   implements GloballyKnown
/*    */ {
/*    */   private Defining _defining;
/*    */   private String _name;
/*    */   
/*    */   public GlobalEntity(Defining defining) {
/* 37 */     this._defining = defining;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 41 */     return this._name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 45 */     this._name = name;
/*    */   }
/*    */   
/*    */   public abstract Kind getKind();
/*    */   
/*    */   public Defining getDefining() {
/* 51 */     return this._defining;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\GlobalEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */