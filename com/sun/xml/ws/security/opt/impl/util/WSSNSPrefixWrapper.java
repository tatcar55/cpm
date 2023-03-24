/*    */ package com.sun.xml.ws.security.opt.impl.util;
/*    */ 
/*    */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSSNSPrefixWrapper
/*    */   extends NamespacePrefixMapper
/*    */ {
/* 49 */   private NamespacePrefixMapper npm = null;
/*    */   
/*    */   public WSSNSPrefixWrapper(NamespacePrefixMapper nsw) {
/* 52 */     this.npm = nsw;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
/* 57 */     return this.npm.getPreferredPrefix(namespaceUri, suggestion, requirePrefix);
/*    */   }
/*    */   
/*    */   public String[] getPreDeclaredNamespaceUris() {
/* 61 */     return this.npm.getPreDeclaredNamespaceUris();
/*    */   }
/*    */   
/*    */   public String[] getContextualNamespaceDecls() {
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\WSSNSPrefixWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */