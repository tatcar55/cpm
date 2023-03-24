/*    */ package com.sun.xml.ws.rx.mc.policy;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum McAssertionNamespace
/*    */ {
/* 54 */   WSMC_200702("http://docs.oasis-open.org/ws-rx/wsmc/200702", "wsmc");
/*    */   
/*    */   public static List<String> namespacesList() {
/* 57 */     List<String> retVal = new ArrayList<String>((values()).length);
/* 58 */     for (McAssertionNamespace pns : values()) {
/* 59 */       retVal.add(pns.toString());
/*    */     }
/* 61 */     return retVal;
/*    */   }
/*    */   
/*    */   private final String prefix;
/*    */   private final String namespace;
/*    */   
/*    */   McAssertionNamespace(String namespace, String prefix) {
/* 68 */     this.namespace = namespace;
/* 69 */     this.prefix = prefix;
/*    */   }
/*    */   
/*    */   public String prefix() {
/* 73 */     return this.prefix;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 78 */     return this.namespace;
/*    */   }
/*    */   
/*    */   public QName getQName(String name) {
/* 82 */     return new QName(this.namespace, name);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\policy\McAssertionNamespace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */