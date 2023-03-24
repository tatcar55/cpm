/*     */ package com.sun.xml.ws.wsdl.parser;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class InaccessibleWSDLException
/*     */   extends WebServiceException
/*     */ {
/*     */   private final List<Throwable> errors;
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public InaccessibleWSDLException(List<Throwable> errors) {
/*  63 */     super(errors.size() + " counts of InaccessibleWSDLException.\n");
/*  64 */     assert !errors.isEmpty() : "there must be at least one error";
/*  65 */     this.errors = Collections.unmodifiableList(new ArrayList<Throwable>(errors));
/*     */   }
/*     */   
/*     */   public String toString() {
/*  69 */     StringBuilder sb = new StringBuilder(super.toString());
/*  70 */     sb.append('\n');
/*     */     
/*  72 */     for (Throwable error : this.errors) {
/*  73 */       sb.append(error.toString()).append('\n');
/*     */     }
/*  75 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Throwable> getErrors() {
/*  86 */     return this.errors;
/*     */   }
/*     */   
/*     */   public static class Builder implements ErrorHandler {
/*  90 */     private final List<Throwable> list = new ArrayList<Throwable>();
/*     */     public void error(Throwable e) {
/*  92 */       this.list.add(e);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void check() throws InaccessibleWSDLException {
/*  99 */       if (this.list.isEmpty())
/*     */         return; 
/* 101 */       throw new InaccessibleWSDLException(this.list);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\InaccessibleWSDLException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */