/*    */ package com.sun.xml.ws.security.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.spi.PrefixMapper;
/*    */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
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
/*    */ public class SecurityPrefixMapper
/*    */   implements PrefixMapper
/*    */ {
/* 55 */   private static final Map<String, String> prefixMap = new HashMap<String, String>();
/*    */   
/*    */   static {
/* 58 */     prefixMap.put(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "sp");
/* 59 */     prefixMap.put(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "sp");
/* 60 */     prefixMap.put("http://schemas.xmlsoap.org/ws/2005/02/trust", "wst");
/* 61 */     prefixMap.put("http://docs.oasis-open.org/wss/2004/01/oasis- 200401-wss-wssecurity-utility-1.0.xsd", "wsu");
/* 62 */     prefixMap.put("http://schemas.sun.com/2006/03/wss/client", "csp");
/* 63 */     prefixMap.put("http://schemas.sun.com/2006/03/wss/server", "ssp");
/* 64 */     prefixMap.put("http://schemas.sun.com/ws/2006/05/trust/client", "ctp");
/* 65 */     prefixMap.put("http://schemas.sun.com/ws/2006/05/trust/server", "stp");
/* 66 */     prefixMap.put("http://schemas.sun.com/ws/2006/05/sc/client", "cscp");
/* 67 */     prefixMap.put("http://schemas.sun.com/ws/2006/05/sc/server", "sscp");
/*    */   }
/*    */   
/*    */   public Map<String, String> getPrefixMap() {
/* 71 */     return prefixMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SecurityPrefixMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */