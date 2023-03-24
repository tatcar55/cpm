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
/*    */ public final class Namespace
/*    */ {
/*    */   private String _prefix;
/*    */   private String _uri;
/*    */   
/*    */   private Namespace(String prefix, String uri) {
/* 35 */     this._prefix = prefix;
/* 36 */     this._uri = uri;
/*    */   }
/*    */   
/*    */   public String getPrefix() {
/* 40 */     return this._prefix;
/*    */   }
/*    */   
/*    */   public String getURI() {
/* 44 */     return this._uri;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 48 */     if (obj == null) {
/* 49 */       return false;
/*    */     }
/* 51 */     if (!(obj instanceof Namespace)) {
/* 52 */       return false;
/*    */     }
/* 54 */     Namespace namespace = (Namespace)obj;
/*    */     
/* 56 */     return (this._prefix.equals(namespace._prefix) && this._uri.equals(namespace._uri));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 61 */     return this._prefix.hashCode() ^ this._uri.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Namespace getNamespace(String prefix, String uri) {
/* 72 */     if (prefix == null) {
/* 73 */       prefix = "";
/*    */     }
/* 75 */     if (uri == null) {
/* 76 */       uri = "";
/*    */     }
/*    */     
/* 79 */     return new Namespace(prefix, uri);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\xml\Namespace.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */