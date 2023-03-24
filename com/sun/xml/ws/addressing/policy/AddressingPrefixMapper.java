/*    */ package com.sun.xml.ws.addressing.policy;
/*    */ 
/*    */ import com.sun.xml.ws.api.addressing.AddressingVersion;
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
/*    */ 
/*    */ public class AddressingPrefixMapper
/*    */   implements PrefixMapper
/*    */ {
/* 58 */   private static final Map<String, String> prefixMap = new HashMap<String, String>();
/*    */   
/*    */   static {
/* 61 */     prefixMap.put(AddressingVersion.MEMBER.policyNsUri, "wsap");
/* 62 */     prefixMap.put(AddressingVersion.MEMBER.nsUri, "wsa");
/* 63 */     prefixMap.put("http://www.w3.org/2007/05/addressing/metadata", "wsam");
/*    */   }
/*    */   
/*    */   public Map<String, String> getPrefixMap() {
/* 67 */     return prefixMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\policy\AddressingPrefixMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */