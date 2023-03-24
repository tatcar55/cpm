/*    */ package com.google.zxing.client.result;
/*    */ 
/*    */ import com.google.zxing.Result;
/*    */ import java.util.Map;
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
/*    */ public final class EmailAddressResultParser
/*    */   extends ResultParser
/*    */ {
/* 32 */   private static final Pattern COMMA = Pattern.compile(",");
/*    */ 
/*    */   
/*    */   public EmailAddressParsedResult parse(Result result) {
/* 36 */     String rawText = getMassagedText(result);
/* 37 */     if (rawText.startsWith("mailto:") || rawText.startsWith("MAILTO:")) {
/*    */       
/* 39 */       String hostEmail = rawText.substring(7);
/* 40 */       int queryStart = hostEmail.indexOf('?');
/* 41 */       if (queryStart >= 0) {
/* 42 */         hostEmail = hostEmail.substring(0, queryStart);
/*    */       }
/* 44 */       hostEmail = urlDecode(hostEmail);
/* 45 */       String[] tos = null;
/* 46 */       if (!hostEmail.isEmpty()) {
/* 47 */         tos = COMMA.split(hostEmail);
/*    */       }
/* 49 */       Map<String, String> nameValues = parseNameValuePairs(rawText);
/* 50 */       String[] ccs = null;
/* 51 */       String[] bccs = null;
/* 52 */       String subject = null;
/* 53 */       String body = null;
/* 54 */       if (nameValues != null) {
/* 55 */         if (tos == null) {
/* 56 */           String tosString = nameValues.get("to");
/* 57 */           if (tosString != null) {
/* 58 */             tos = COMMA.split(tosString);
/*    */           }
/*    */         } 
/* 61 */         String ccString = nameValues.get("cc");
/* 62 */         if (ccString != null) {
/* 63 */           ccs = COMMA.split(ccString);
/*    */         }
/* 65 */         String bccString = nameValues.get("bcc");
/* 66 */         if (bccString != null) {
/* 67 */           bccs = COMMA.split(bccString);
/*    */         }
/* 69 */         subject = nameValues.get("subject");
/* 70 */         body = nameValues.get("body");
/*    */       } 
/* 72 */       return new EmailAddressParsedResult(tos, ccs, bccs, subject, body);
/*    */     } 
/* 74 */     if (!EmailDoCoMoResultParser.isBasicallyValidEmailAddress(rawText)) {
/* 75 */       return null;
/*    */     }
/* 77 */     return new EmailAddressParsedResult(rawText);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\EmailAddressResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */