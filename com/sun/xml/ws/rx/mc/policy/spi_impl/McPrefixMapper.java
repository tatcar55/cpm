/*    */ package com.sun.xml.ws.rx.mc.policy.spi_impl;
/*    */ 
/*    */ import com.sun.xml.ws.policy.spi.PrefixMapper;
/*    */ import com.sun.xml.ws.rx.mc.policy.McAssertionNamespace;
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
/*    */ 
/*    */ 
/*    */ public class McPrefixMapper
/*    */   implements PrefixMapper
/*    */ {
/*    */   private static final Map<String, String> prefixMap;
/*    */   
/*    */   static {
/* 60 */     HashMap<String, String> tempMap = new HashMap<String, String>();
/* 61 */     for (McAssertionNamespace ns : McAssertionNamespace.values()) {
/* 62 */       tempMap.put(ns.toString(), ns.prefix());
/*    */     }
/* 64 */     prefixMap = Collections.unmodifiableMap(tempMap);
/*    */   }
/*    */   
/*    */   public Map<String, String> getPrefixMap() {
/* 68 */     return prefixMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\policy\spi_impl\McPrefixMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */