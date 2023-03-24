/*    */ package com.google.zxing.client.result;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EmailAddressParsedResult
/*    */   extends ParsedResult
/*    */ {
/*    */   private final String[] tos;
/*    */   private final String[] ccs;
/*    */   private final String[] bccs;
/*    */   private final String subject;
/*    */   private final String body;
/*    */   
/*    */   EmailAddressParsedResult(String to) {
/* 31 */     this(new String[] { to }, (String[])null, (String[])null, (String)null, (String)null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   EmailAddressParsedResult(String[] tos, String[] ccs, String[] bccs, String subject, String body) {
/* 39 */     super(ParsedResultType.EMAIL_ADDRESS);
/* 40 */     this.tos = tos;
/* 41 */     this.ccs = ccs;
/* 42 */     this.bccs = bccs;
/* 43 */     this.subject = subject;
/* 44 */     this.body = body;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public String getEmailAddress() {
/* 53 */     return (this.tos == null || this.tos.length == 0) ? null : this.tos[0];
/*    */   }
/*    */   
/*    */   public String[] getTos() {
/* 57 */     return this.tos;
/*    */   }
/*    */   
/*    */   public String[] getCCs() {
/* 61 */     return this.ccs;
/*    */   }
/*    */   
/*    */   public String[] getBCCs() {
/* 65 */     return this.bccs;
/*    */   }
/*    */   
/*    */   public String getSubject() {
/* 69 */     return this.subject;
/*    */   }
/*    */   
/*    */   public String getBody() {
/* 73 */     return this.body;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public String getMailtoURI() {
/* 82 */     return "mailto:";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayResult() {
/* 87 */     StringBuilder result = new StringBuilder(30);
/* 88 */     maybeAppend(this.tos, result);
/* 89 */     maybeAppend(this.ccs, result);
/* 90 */     maybeAppend(this.bccs, result);
/* 91 */     maybeAppend(this.subject, result);
/* 92 */     maybeAppend(this.body, result);
/* 93 */     return result.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\EmailAddressParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */