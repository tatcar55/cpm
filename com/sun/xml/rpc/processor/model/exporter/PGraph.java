/*    */ package com.sun.xml.rpc.processor.model.exporter;
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
/*    */ public class PGraph
/*    */ {
/*    */   private QName name;
/*    */   private PObject root;
/*    */   private String version;
/*    */   
/*    */   public PObject getRoot() {
/* 40 */     return this.root;
/*    */   }
/*    */   
/*    */   public void setRoot(PObject o) {
/* 44 */     this.root = o;
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 48 */     return this.version;
/*    */   }
/*    */   
/*    */   public void setVersion(String s) {
/* 52 */     this.version = s;
/*    */   }
/*    */   
/*    */   public QName getName() {
/* 56 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(QName n) {
/* 60 */     this.name = n;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\exporter\PGraph.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */