/*    */ package com.sun.xml.ws.security.trust.elements.str;
/*    */ 
/*    */ import com.sun.xml.ws.security.Token;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface SecurityTokenReference
/*    */   extends Token
/*    */ {
/*    */   public static final String KEYIDENTIFIER = "KeyIdentifier";
/*    */   public static final String REFERENCE = "Reference";
/* 59 */   public static final QName TOKEN_TYPE = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "TokenType");
/*    */   
/*    */   void setReference(Reference paramReference);
/*    */   
/*    */   Reference getReference();
/*    */   
/*    */   void setTokenType(String paramString);
/*    */   
/*    */   String getTokenType();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\str\SecurityTokenReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */