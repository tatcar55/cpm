/*    */ package com.sun.xml.rpc.wsdl.framework;
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
/*    */ 
/*    */ public class ExternalEntityReference
/*    */ {
/*    */   private AbstractDocument _document;
/*    */   private Kind _kind;
/*    */   private QName _name;
/*    */   
/*    */   public ExternalEntityReference(AbstractDocument document, Kind kind, QName name) {
/* 42 */     this._document = document;
/* 43 */     this._kind = kind;
/* 44 */     this._name = name;
/*    */   }
/*    */   
/*    */   public AbstractDocument getDocument() {
/* 48 */     return this._document;
/*    */   }
/*    */   
/*    */   public Kind getKind() {
/* 52 */     return this._kind;
/*    */   }
/*    */   
/*    */   public QName getName() {
/* 56 */     return this._name;
/*    */   }
/*    */   
/*    */   public GloballyKnown resolve() {
/* 60 */     return this._document.find(this._kind, this._name);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\ExternalEntityReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */