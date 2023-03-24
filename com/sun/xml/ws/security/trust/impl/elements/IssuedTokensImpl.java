/*    */ package com.sun.xml.ws.security.trust.impl.elements;
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
/*    */ public class IssuedTokensImpl
/*    */   implements IssuedTokens
/*    */ {
/* 55 */   RequestSecurityTokenResponseCollection collection = null;
/*    */ 
/*    */   
/*    */   public IssuedTokensImpl() {}
/*    */ 
/*    */   
/*    */   public IssuedTokensImpl(RequestSecurityTokenResponseCollection rcollection) {
/* 62 */     setIssuedTokens(rcollection);
/*    */   }
/*    */   
/*    */   public RequestSecurityTokenResponseCollection getIssuedTokens() {
/* 66 */     return this.collection;
/*    */   }
/*    */   
/*    */   public final void setIssuedTokens(RequestSecurityTokenResponseCollection rcollection) {
/* 70 */     this.collection = rcollection;
/*    */   }
/*    */   
/*    */   public List<Object> getAny() {
/* 74 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\IssuedTokensImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */