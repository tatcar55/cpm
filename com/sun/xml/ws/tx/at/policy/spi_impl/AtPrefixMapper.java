/*    */ package com.sun.xml.ws.tx.at.policy.spi_impl;
/*    */ 
/*    */ import com.sun.xml.ws.api.tx.at.WsatNamespace;
/*    */ import com.sun.xml.ws.policy.spi.PrefixMapper;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AtPrefixMapper
/*    */   implements PrefixMapper
/*    */ {
/*    */   private static final Map<String, String> prefixMap;
/*    */   
/*    */   static {
/* 58 */     Map<String, String> tmpMap = new HashMap<String, String>();
/*    */     
/* 60 */     for (WsatNamespace ns : WsatNamespace.values()) {
/* 61 */       tmpMap.put(ns.namespace, ns.defaultPrefix);
/*    */     }
/*    */     
/* 64 */     prefixMap = Collections.unmodifiableMap(tmpMap);
/*    */   }
/*    */   
/*    */   public Map<String, String> getPrefixMap() {
/* 68 */     return prefixMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\policy\spi_impl\AtPrefixMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */