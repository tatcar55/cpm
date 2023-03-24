/*    */ package com.sun.xml.rpc.soap.message;
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
/*    */ public class SOAPHeaderBlockInfo
/*    */   extends SOAPBlockInfo
/*    */ {
/*    */   private String _actor;
/*    */   private boolean _mustUnderstand;
/*    */   
/*    */   public SOAPHeaderBlockInfo(QName name, String actor, boolean mustUnderstand) {
/* 40 */     super(name);
/* 41 */     this._actor = actor;
/* 42 */     this._mustUnderstand = mustUnderstand;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\message\SOAPHeaderBlockInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */