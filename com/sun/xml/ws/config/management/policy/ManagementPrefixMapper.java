/*    */ package com.sun.xml.ws.config.management.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.spi.PrefixMapper;
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
/*    */ 
/*    */ public class ManagementPrefixMapper
/*    */   implements PrefixMapper
/*    */ {
/* 56 */   private static final Map<String, String> prefixMap = new HashMap<String, String>();
/*    */   
/*    */   static {
/* 59 */     prefixMap.put("http://java.sun.com/xml/ns/metro/management", "sunman");
/*    */   }
/*    */   
/*    */   public Map<String, String> getPrefixMap() {
/* 63 */     return prefixMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\management\policy\ManagementPrefixMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */