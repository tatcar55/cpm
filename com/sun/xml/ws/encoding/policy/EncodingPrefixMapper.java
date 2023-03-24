/*    */ package com.sun.xml.ws.encoding.policy;
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
/*    */ public class EncodingPrefixMapper
/*    */   implements PrefixMapper
/*    */ {
/* 53 */   private static final Map<String, String> prefixMap = new HashMap<String, String>();
/*    */   
/*    */   static {
/* 56 */     prefixMap.put("http://schemas.xmlsoap.org/ws/2004/09/policy/encoding", "wspe");
/* 57 */     prefixMap.put("http://schemas.xmlsoap.org/ws/2004/09/policy/optimizedmimeserialization", "wsoma");
/* 58 */     prefixMap.put("http://java.sun.com/xml/ns/wsit/2006/09/policy/encoding/client", "cenc");
/* 59 */     prefixMap.put("http://java.sun.com/xml/ns/wsit/2006/09/policy/fastinfoset/service", "fi");
/*    */   }
/*    */   
/*    */   public Map<String, String> getPrefixMap() {
/* 63 */     return prefixMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\policy\EncodingPrefixMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */