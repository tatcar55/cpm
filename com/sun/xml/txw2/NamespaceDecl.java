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
/*    */ final class NamespaceDecl
/*    */ {
/*    */   final String uri;
/*    */   boolean requirePrefix;
/*    */   final String dummyPrefix;
/*    */   final char uniqueId;
/*    */   String prefix;
/*    */   boolean declared;
/*    */   NamespaceDecl next;
/*    */   
/*    */   NamespaceDecl(char uniqueId, String uri, String prefix, boolean requirePrefix) {
/* 77 */     this.dummyPrefix = (new StringBuilder(2)).append(false).append(uniqueId).toString();
/* 78 */     this.uri = uri;
/* 79 */     this.prefix = prefix;
/* 80 */     this.requirePrefix = requirePrefix;
/* 81 */     this.uniqueId = uniqueId;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\NamespaceDecl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */