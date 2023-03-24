/*    */ package com.google.zxing.client.result;
/*    */ 
/*    */ import com.google.zxing.Result;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SMTPResultParser
/*    */   extends ResultParser
/*    */ {
/*    */   public EmailAddressParsedResult parse(Result result) {
/* 31 */     String rawText = getMassagedText(result);
/* 32 */     if (!rawText.startsWith("smtp:") && !rawText.startsWith("SMTP:")) {
/* 33 */       return null;
/*    */     }
/* 35 */     String emailAddress = rawText.substring(5);
/* 36 */     String subject = null;
/* 37 */     String body = null;
/* 38 */     int colon = emailAddress.indexOf(':');
/* 39 */     if (colon >= 0) {
/* 40 */       subject = emailAddress.substring(colon + 1);
/* 41 */       emailAddress = emailAddress.substring(0, colon);
/* 42 */       colon = subject.indexOf(':');
/* 43 */       if (colon >= 0) {
/* 44 */         body = subject.substring(colon + 1);
/* 45 */         subject = subject.substring(0, colon);
/*    */       } 
/*    */     } 
/* 48 */     return new EmailAddressParsedResult(new String[] { emailAddress }, null, null, subject, body);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\SMTPResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */