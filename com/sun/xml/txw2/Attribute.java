/*    */ package com.sun.xml.txw2;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Attribute
/*    */ {
/*    */   final String nsUri;
/*    */   final String localName;
/*    */   Attribute next;
/* 58 */   final StringBuilder value = new StringBuilder();
/*    */   
/*    */   Attribute(String nsUri, String localName) {
/* 61 */     assert nsUri != null && localName != null;
/*    */     
/* 63 */     this.nsUri = nsUri;
/* 64 */     this.localName = localName;
/*    */   }
/*    */   
/*    */   boolean hasName(String nsUri, String localName) {
/* 68 */     return (this.localName.equals(localName) && this.nsUri.equals(nsUri));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\Attribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */