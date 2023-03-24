/*    */ package com.sun.xml.rpc.util.xml;
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
/*    */ public final class CDATA
/*    */ {
/*    */   private String _text;
/*    */   
/*    */   public CDATA(String text) {
/* 35 */     this._text = text;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 39 */     return this._text;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 43 */     if (obj == null) {
/* 44 */       return false;
/*    */     }
/* 46 */     if (!(obj instanceof CDATA)) {
/* 47 */       return false;
/*    */     }
/* 49 */     CDATA cdata = (CDATA)obj;
/*    */     
/* 51 */     return this._text.equals(cdata._text);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 55 */     return this._text.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\xml\CDATA.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */