/*    */ package com.sun.xml.ws.transport.tcp.wsit;
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
/*    */ public class TCPTransportPrefixMapper
/*    */   implements PrefixMapper
/*    */ {
/* 53 */   private static final Map<String, String> prefixMap = new HashMap<String, String>();
/*    */   
/*    */   static {
/* 56 */     prefixMap.put("http://java.sun.com/xml/ns/wsit/2006/09/policy/soaptcp/service", "soaptcpsvc");
/* 57 */     prefixMap.put("http://java.sun.com/xml/ns/wsit/2006/09/policy/transport/client", "transport");
/* 58 */     prefixMap.put("http://java.sun.com/xml/ns/wsit/2006/09/policy/soaptcp", "soaptcp");
/*    */   }
/*    */   
/*    */   public Map<String, String> getPrefixMap() {
/* 62 */     return prefixMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\wsit\TCPTransportPrefixMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */