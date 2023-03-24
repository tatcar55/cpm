/*    */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*    */ 
/*    */ import com.sun.xml.ws.security.trust.elements.IssuedTokens;
/*    */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IssuedTokensImpl
/*    */   implements IssuedTokens
/*    */ {
/* 56 */   RequestSecurityTokenResponseCollection collection = null;
/*    */ 
/*    */   
/*    */   public IssuedTokensImpl() {}
/*    */ 
/*    */   
/*    */   public IssuedTokensImpl(RequestSecurityTokenResponseCollection rcollection) {
/* 63 */     setIssuedTokens(rcollection);
/*    */   }
/*    */   
/*    */   public RequestSecurityTokenResponseCollection getIssuedTokens() {
/* 67 */     return this.collection;
/*    */   }
/*    */   
/*    */   public void setIssuedTokens(RequestSecurityTokenResponseCollection rcollection) {
/* 71 */     this.collection = rcollection;
/*    */   }
/*    */   
/*    */   public List<Object> getAny() {
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\IssuedTokensImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */