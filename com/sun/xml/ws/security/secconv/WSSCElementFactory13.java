/*    */ package com.sun.xml.ws.security.secconv;
/*    */ 
/*    */ import com.sun.xml.ws.security.SecurityContextToken;
/*    */ import com.sun.xml.ws.security.secconv.impl.wssx.elements.SecurityContextTokenImpl;
/*    */ import com.sun.xml.ws.security.trust.impl.wssx.WSTrustElementFactoryImpl;
/*    */ import java.net.URI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSSCElementFactory13
/*    */   extends WSTrustElementFactoryImpl
/*    */ {
/* 65 */   private static final WSSCElementFactory13 scElemFactory13 = new WSSCElementFactory13();
/*    */   
/*    */   public static WSSCElementFactory13 newInstance() {
/* 68 */     return scElemFactory13;
/*    */   }
/*    */   
/*    */   public SecurityContextToken createSecurityContextToken(URI identifier, String instance, String wsuId) {
/* 72 */     return (SecurityContextToken)new SecurityContextTokenImpl(identifier, instance, wsuId);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\WSSCElementFactory13.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */