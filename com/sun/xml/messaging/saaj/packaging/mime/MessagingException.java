/*     */ package com.sun.xml.messaging.saaj.packaging.mime;
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
/*     */ 
/*     */ 
/*     */ public class MessagingException
/*     */   extends Exception
/*     */ {
/*     */   private Exception next;
/*     */   
/*     */   public MessagingException() {}
/*     */   
/*     */   public MessagingException(String s) {
/*  78 */     super(s);
/*     */   }
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
/*     */   public MessagingException(String s, Exception e) {
/*  91 */     super(s);
/*  92 */     this.next = e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception getNextException() {
/* 103 */     return this.next;
/*     */   }
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
/*     */   public synchronized boolean setNextException(Exception ex) {
/* 116 */     Exception theEnd = this;
/*     */     
/* 118 */     while (theEnd instanceof MessagingException && ((MessagingException)theEnd).next != null) {
/* 119 */       theEnd = ((MessagingException)theEnd).next;
/*     */     }
/*     */ 
/*     */     
/* 123 */     if (theEnd instanceof MessagingException) {
/* 124 */       ((MessagingException)theEnd).next = ex;
/* 125 */       return true;
/*     */     } 
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 135 */     if (this.next == null)
/* 136 */       return super.getMessage(); 
/* 137 */     Exception n = this.next;
/* 138 */     String s = super.getMessage();
/* 139 */     StringBuffer sb = new StringBuffer((s == null) ? "" : s);
/* 140 */     while (n != null) {
/* 141 */       sb.append(";\n  nested exception is:\n\t");
/* 142 */       if (n instanceof MessagingException) {
/* 143 */         MessagingException mex = (MessagingException)n;
/* 144 */         sb.append(n.getClass().toString());
/* 145 */         String msg = mex.getSuperMessage();
/* 146 */         if (msg != null) {
/* 147 */           sb.append(": ");
/* 148 */           sb.append(msg);
/*     */         } 
/* 150 */         n = mex.next; continue;
/*     */       } 
/* 152 */       sb.append(n.toString());
/* 153 */       n = null;
/*     */     } 
/*     */     
/* 156 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private String getSuperMessage() {
/* 160 */     return super.getMessage();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mime\MessagingException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */