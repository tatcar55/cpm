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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AddressBookDoCoMoResultParser
/*    */   extends AbstractDoCoMoResultParser
/*    */ {
/*    */   public AddressBookParsedResult parse(Result result) {
/* 40 */     String rawText = getMassagedText(result);
/* 41 */     if (!rawText.startsWith("MECARD:")) {
/* 42 */       return null;
/*    */     }
/* 44 */     String[] rawName = matchDoCoMoPrefixedField("N:", rawText, true);
/* 45 */     if (rawName == null) {
/* 46 */       return null;
/*    */     }
/* 48 */     String name = parseName(rawName[0]);
/* 49 */     String pronunciation = matchSingleDoCoMoPrefixedField("SOUND:", rawText, true);
/* 50 */     String[] phoneNumbers = matchDoCoMoPrefixedField("TEL:", rawText, true);
/* 51 */     String[] emails = matchDoCoMoPrefixedField("EMAIL:", rawText, true);
/* 52 */     String note = matchSingleDoCoMoPrefixedField("NOTE:", rawText, false);
/* 53 */     String[] addresses = matchDoCoMoPrefixedField("ADR:", rawText, true);
/* 54 */     String birthday = matchSingleDoCoMoPrefixedField("BDAY:", rawText, true);
/* 55 */     if (!isStringOfDigits(birthday, 8))
/*    */     {
/* 57 */       birthday = null;
/*    */     }
/* 59 */     String[] urls = matchDoCoMoPrefixedField("URL:", rawText, true);
/*    */ 
/*    */ 
/*    */     
/* 63 */     String org = matchSingleDoCoMoPrefixedField("ORG:", rawText, true);
/*    */     
/* 65 */     return new AddressBookParsedResult(maybeWrap(name), null, pronunciation, phoneNumbers, null, emails, null, null, note, addresses, null, org, birthday, null, urls, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String parseName(String name) {
/* 84 */     int comma = name.indexOf(',');
/* 85 */     if (comma >= 0)
/*    */     {
/* 87 */       return name.substring(comma + 1) + ' ' + name.substring(0, comma);
/*    */     }
/* 89 */     return name;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\AddressBookDoCoMoResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */