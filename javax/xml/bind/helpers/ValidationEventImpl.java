/*     */ package javax.xml.bind.helpers;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventLocator;
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
/*     */ public class ValidationEventImpl
/*     */   implements ValidationEvent
/*     */ {
/*     */   private int severity;
/*     */   private String message;
/*     */   private Throwable linkedException;
/*     */   private ValidationEventLocator locator;
/*     */   
/*     */   public ValidationEventImpl(int _severity, String _message, ValidationEventLocator _locator) {
/*  79 */     this(_severity, _message, _locator, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidationEventImpl(int _severity, String _message, ValidationEventLocator _locator, Throwable _linkedException) {
/*  98 */     setSeverity(_severity);
/*  99 */     this.message = _message;
/* 100 */     this.locator = _locator;
/* 101 */     this.linkedException = _linkedException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeverity() {
/* 110 */     return this.severity;
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
/*     */   public void setSeverity(int _severity) {
/* 123 */     if (_severity != 0 && _severity != 1 && _severity != 2)
/*     */     {
/*     */       
/* 126 */       throw new IllegalArgumentException(Messages.format("ValidationEventImpl.IllegalSeverity"));
/*     */     }
/*     */ 
/*     */     
/* 130 */     this.severity = _severity;
/*     */   }
/*     */   
/*     */   public String getMessage() {
/* 134 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(String _message) {
/* 142 */     this.message = _message;
/*     */   }
/*     */   
/*     */   public Throwable getLinkedException() {
/* 146 */     return this.linkedException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinkedException(Throwable _linkedException) {
/* 154 */     this.linkedException = _linkedException;
/*     */   }
/*     */   
/*     */   public ValidationEventLocator getLocator() {
/* 158 */     return this.locator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocator(ValidationEventLocator _locator) {
/* 166 */     this.locator = _locator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 177 */     switch (getSeverity()) { case 0:
/* 178 */         s = "WARNING";
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 183 */         return MessageFormat.format("[severity={0},message={1},locator={2}]", new Object[] { s, getMessage(), getLocator() });case 1: s = "ERROR"; return MessageFormat.format("[severity={0},message={1},locator={2}]", new Object[] { s, getMessage(), getLocator() });case 2: s = "FATAL_ERROR"; return MessageFormat.format("[severity={0},message={1},locator={2}]", new Object[] { s, getMessage(), getLocator() }); }  String s = String.valueOf(getSeverity()); return MessageFormat.format("[severity={0},message={1},locator={2}]", new Object[] { s, getMessage(), getLocator() });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\helpers\ValidationEventImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */