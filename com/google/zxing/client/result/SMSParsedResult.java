/*     */ package com.google.zxing.client.result;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SMSParsedResult
/*     */   extends ParsedResult
/*     */ {
/*     */   private final String[] numbers;
/*     */   private final String[] vias;
/*     */   private final String subject;
/*     */   private final String body;
/*     */   
/*     */   public SMSParsedResult(String number, String via, String subject, String body) {
/*  33 */     super(ParsedResultType.SMS);
/*  34 */     this.numbers = new String[] { number };
/*  35 */     this.vias = new String[] { via };
/*  36 */     this.subject = subject;
/*  37 */     this.body = body;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SMSParsedResult(String[] numbers, String[] vias, String subject, String body) {
/*  44 */     super(ParsedResultType.SMS);
/*  45 */     this.numbers = numbers;
/*  46 */     this.vias = vias;
/*  47 */     this.subject = subject;
/*  48 */     this.body = body;
/*     */   }
/*     */   
/*     */   public String getSMSURI() {
/*  52 */     StringBuilder result = new StringBuilder();
/*  53 */     result.append("sms:");
/*  54 */     boolean first = true;
/*  55 */     for (int i = 0; i < this.numbers.length; i++) {
/*  56 */       if (first) {
/*  57 */         first = false;
/*     */       } else {
/*  59 */         result.append(',');
/*     */       } 
/*  61 */       result.append(this.numbers[i]);
/*  62 */       if (this.vias != null && this.vias[i] != null) {
/*  63 */         result.append(";via=");
/*  64 */         result.append(this.vias[i]);
/*     */       } 
/*     */     } 
/*  67 */     boolean hasBody = (this.body != null);
/*  68 */     boolean hasSubject = (this.subject != null);
/*  69 */     if (hasBody || hasSubject) {
/*  70 */       result.append('?');
/*  71 */       if (hasBody) {
/*  72 */         result.append("body=");
/*  73 */         result.append(this.body);
/*     */       } 
/*  75 */       if (hasSubject) {
/*  76 */         if (hasBody) {
/*  77 */           result.append('&');
/*     */         }
/*  79 */         result.append("subject=");
/*  80 */         result.append(this.subject);
/*     */       } 
/*     */     } 
/*  83 */     return result.toString();
/*     */   }
/*     */   
/*     */   public String[] getNumbers() {
/*  87 */     return this.numbers;
/*     */   }
/*     */   
/*     */   public String[] getVias() {
/*  91 */     return this.vias;
/*     */   }
/*     */   
/*     */   public String getSubject() {
/*  95 */     return this.subject;
/*     */   }
/*     */   
/*     */   public String getBody() {
/*  99 */     return this.body;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayResult() {
/* 104 */     StringBuilder result = new StringBuilder(100);
/* 105 */     maybeAppend(this.numbers, result);
/* 106 */     maybeAppend(this.subject, result);
/* 107 */     maybeAppend(this.body, result);
/* 108 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\SMSParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */