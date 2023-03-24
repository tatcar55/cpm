/*    */ package com.sun.xml.ws.util.xml;
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
/* 49 */     this._text = text;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 53 */     return this._text;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 57 */     if (obj == null) {
/* 58 */       return false;
/*    */     }
/* 60 */     if (!(obj instanceof CDATA)) {
/* 61 */       return false;
/*    */     }
/* 63 */     CDATA cdata = (CDATA)obj;
/*    */     
/* 65 */     return this._text.equals(cdata._text);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 69 */     return this._text.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\xml\CDATA.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */