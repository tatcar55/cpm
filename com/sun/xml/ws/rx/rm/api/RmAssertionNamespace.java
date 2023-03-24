/*    */ package com.sun.xml.ws.rx.rm.api;
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
/*    */ public enum RmAssertionNamespace
/*    */ {
/* 54 */   WSRMP_200502("http://schemas.xmlsoap.org/ws/2005/02/rm/policy", "wsrmp10"),
/* 55 */   WSRMP_200702("http://docs.oasis-open.org/ws-rx/wsrmp/200702", "wsrmp"),
/* 56 */   MICROSOFT_200502("http://schemas.microsoft.com/net/2005/02/rm/policy", "net30rmp"),
/* 57 */   MICROSOFT_200702("http://schemas.microsoft.com/ws-rx/wsrmp/200702", "net35rmp"),
/* 58 */   METRO_200603("http://sun.com/2006/03/rm", "sunrmp"),
/* 59 */   METRO_CLIENT_200603("http://sun.com/2006/03/rm/client", "sunrmcp"),
/* 60 */   METRO_200702("http://java.sun.com/xml/ns/metro/ws-rx/wsrmp/200702", "metrormp");
/*    */   
/*    */   public static List<String> namespacesList() {
/* 63 */     List<String> retVal = new ArrayList<String>((values()).length);
/* 64 */     for (RmAssertionNamespace pns : values()) {
/* 65 */       retVal.add(pns.toString());
/*    */     }
/* 67 */     return retVal;
/*    */   }
/*    */   
/*    */   private final String namespace;
/*    */   private final String prefix;
/*    */   
/*    */   RmAssertionNamespace(String namespace, String prefix) {
/* 74 */     this.namespace = namespace;
/* 75 */     this.prefix = prefix;
/*    */   }
/*    */   
/*    */   public String defaultPrefix() {
/* 79 */     return this.prefix;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 84 */     return this.namespace;
/*    */   }
/*    */   
/*    */   public QName getQName(String name) {
/* 88 */     return new QName(this.namespace, name);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\api\RmAssertionNamespace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */