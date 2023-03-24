/*    */ package com.sun.xml.ws.api.tx.at;
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
/*    */ public enum WsatNamespace
/*    */ {
/* 54 */   WSAT200410("wsat200410", "http://schemas.xmlsoap.org/ws/2004/10/wsat"),
/* 55 */   WSAT200606("wsat200410", "http://docs.oasis-open.org/ws-tx/wsat/2006/06");
/*    */   
/*    */   public static List<String> namespacesList() {
/* 58 */     List<String> retVal = new ArrayList<String>((values()).length);
/* 59 */     for (WsatNamespace wsatNamespaceEnum : values()) {
/* 60 */       retVal.add(wsatNamespaceEnum.namespace);
/*    */     }
/* 62 */     return retVal;
/*    */   }
/*    */   
/*    */   public final String defaultPrefix;
/*    */   public final String namespace;
/*    */   
/*    */   WsatNamespace(String defaultPrefix, String namespace) {
/* 69 */     this.defaultPrefix = defaultPrefix;
/* 70 */     this.namespace = namespace;
/*    */   }
/*    */   
/*    */   public QName createFqn(String name) {
/* 74 */     return new QName(this.namespace, name, this.defaultPrefix);
/*    */   }
/*    */   
/*    */   public QName createFqn(String prefix, String name) {
/* 78 */     return new QName(this.namespace, name, prefix);
/*    */   }
/*    */   
/*    */   public static WsatNamespace forNamespaceUri(String uri) {
/* 82 */     for (WsatNamespace ns : values()) {
/* 83 */       if (ns.namespace.equals(uri)) {
/* 84 */         return ns;
/*    */       }
/*    */     } 
/*    */     
/* 88 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\tx\at\WsatNamespace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */