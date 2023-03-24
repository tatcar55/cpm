/*    */ package com.google.zxing.client.result;
/*    */ 
/*    */ import com.google.zxing.Result;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EmailDoCoMoResultParser
/*    */   extends AbstractDoCoMoResultParser
/*    */ {
/* 32 */   private static final Pattern ATEXT_ALPHANUMERIC = Pattern.compile("[a-zA-Z0-9@.!#$%&'*+\\-/=?^_`{|}~]+");
/*    */ 
/*    */   
/*    */   public EmailAddressParsedResult parse(Result result) {
/* 36 */     String rawText = getMassagedText(result);
/* 37 */     if (!rawText.startsWith("MATMSG:")) {
/* 38 */       return null;
/*    */     }
/* 40 */     String[] tos = matchDoCoMoPrefixedField("TO:", rawText, true);
/* 41 */     if (tos == null) {
/* 42 */       return null;
/*    */     }
/* 44 */     for (String to : tos) {
/* 45 */       if (!isBasicallyValidEmailAddress(to)) {
/* 46 */         return null;
/*    */       }
/*    */     } 
/* 49 */     String subject = matchSingleDoCoMoPrefixedField("SUB:", rawText, false);
/* 50 */     String body = matchSingleDoCoMoPrefixedField("BODY:", rawText, false);
/* 51 */     return new EmailAddressParsedResult(tos, null, null, subject, body);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static boolean isBasicallyValidEmailAddress(String email) {
/* 61 */     return (email != null && ATEXT_ALPHANUMERIC.matcher(email).matches() && email.indexOf('@') >= 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\EmailDoCoMoResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */