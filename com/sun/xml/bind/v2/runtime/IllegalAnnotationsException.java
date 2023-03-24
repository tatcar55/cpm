/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.core.ErrorHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ public class IllegalAnnotationsException
/*     */   extends JAXBException
/*     */ {
/*     */   private final List<IllegalAnnotationException> errors;
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public IllegalAnnotationsException(List<IllegalAnnotationException> errors) {
/*  68 */     super(errors.size() + " counts of IllegalAnnotationExceptions");
/*  69 */     assert !errors.isEmpty() : "there must be at least one error";
/*  70 */     this.errors = Collections.unmodifiableList(new ArrayList<IllegalAnnotationException>(errors));
/*     */   }
/*     */   
/*     */   public String toString() {
/*  74 */     StringBuilder sb = new StringBuilder(super.toString());
/*  75 */     sb.append('\n');
/*     */     
/*  77 */     for (IllegalAnnotationException error : this.errors) {
/*  78 */       sb.append(error.toString()).append('\n');
/*     */     }
/*  80 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IllegalAnnotationException> getErrors() {
/*  91 */     return this.errors;
/*     */   }
/*     */   
/*     */   public static class Builder implements ErrorHandler {
/*  95 */     private final List<IllegalAnnotationException> list = new ArrayList<IllegalAnnotationException>();
/*     */     public void error(IllegalAnnotationException e) {
/*  97 */       this.list.add(e);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void check() throws IllegalAnnotationsException {
/* 104 */       if (this.list.isEmpty())
/*     */         return; 
/* 106 */       throw new IllegalAnnotationsException(this.list);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\IllegalAnnotationsException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */