/*    */ package com.sun.xml.ws.security.secconv;
/*    */ 
/*    */ import com.sun.xml.ws.security.SecurityContextToken;
/*    */ import com.sun.xml.ws.security.secconv.impl.elements.SecurityContextTokenImpl;
/*    */ import com.sun.xml.ws.security.trust.impl.WSTrustElementFactoryImpl;
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
/*    */ public class WSSCElementFactory
/*    */   extends WSTrustElementFactoryImpl
/*    */ {
/* 65 */   private static final WSSCElementFactory scElemFactory = new WSSCElementFactory();
/*    */   
/*    */   public static WSSCElementFactory newInstance() {
/* 68 */     return scElemFactory;
/*    */   }
/*    */   
/*    */   public SecurityContextToken createSecurityContextToken(URI identifier, String instance, String wsuId) {
/* 72 */     return (SecurityContextToken)new SecurityContextTokenImpl(identifier, instance, wsuId);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\WSSCElementFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */