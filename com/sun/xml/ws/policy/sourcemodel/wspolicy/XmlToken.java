/*    */ package com.sun.xml.ws.policy.sourcemodel.wspolicy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum XmlToken
/*    */ {
/* 48 */   Policy("Policy", true),
/* 49 */   ExactlyOne("ExactlyOne", true),
/* 50 */   All("All", true),
/* 51 */   PolicyReference("PolicyReference", true),
/* 52 */   UsingPolicy("UsingPolicy", true),
/* 53 */   Name("Name", false),
/* 54 */   Optional("Optional", false),
/* 55 */   Ignorable("Ignorable", false),
/* 56 */   PolicyUris("PolicyURIs", false),
/* 57 */   Uri("URI", false),
/* 58 */   Digest("Digest", false),
/* 59 */   DigestAlgorithm("DigestAlgorithm", false),
/*    */   
/* 61 */   UNKNOWN("", true);
/*    */ 
/*    */ 
/*    */   
/*    */   private String tokenName;
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean element;
/*    */ 
/*    */ 
/*    */   
/*    */   public static XmlToken resolveToken(String name) {
/* 74 */     for (XmlToken token : values()) {
/* 75 */       if (token.toString().equals(name)) {
/* 76 */         return token;
/*    */       }
/*    */     } 
/*    */     
/* 80 */     return UNKNOWN;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   XmlToken(String name, boolean element) {
/* 87 */     this.tokenName = name;
/* 88 */     this.element = element;
/*    */   }
/*    */   
/*    */   public boolean isElement() {
/* 92 */     return this.element;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 97 */     return this.tokenName;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\wspolicy\XmlToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */