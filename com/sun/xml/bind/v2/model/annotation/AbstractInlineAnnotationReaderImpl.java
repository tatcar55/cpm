/*     */ package com.sun.xml.bind.v2.model.annotation;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.core.ErrorHandler;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import java.lang.annotation.Annotation;
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
/*     */ public abstract class AbstractInlineAnnotationReaderImpl<T, C, F, M>
/*     */   implements AnnotationReader<T, C, F, M>
/*     */ {
/*     */   private ErrorHandler errorHandler;
/*     */   
/*     */   public void setErrorHandler(ErrorHandler errorHandler) {
/*  62 */     if (errorHandler == null)
/*  63 */       throw new IllegalArgumentException(); 
/*  64 */     this.errorHandler = errorHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ErrorHandler getErrorHandler() {
/*  71 */     assert this.errorHandler != null : "error handler must be set before use";
/*  72 */     return this.errorHandler;
/*     */   }
/*     */   
/*     */   public final <A extends Annotation> A getMethodAnnotation(Class<A> annotation, M getter, M setter, Locatable srcPos) {
/*  76 */     A a1 = (getter == null) ? null : (A)getMethodAnnotation(annotation, getter, srcPos);
/*  77 */     A a2 = (setter == null) ? null : (A)getMethodAnnotation(annotation, setter, srcPos);
/*     */     
/*  79 */     if (a1 == null) {
/*  80 */       if (a2 == null) {
/*  81 */         return null;
/*     */       }
/*  83 */       return a2;
/*     */     } 
/*  85 */     if (a2 == null) {
/*  86 */       return a1;
/*     */     }
/*     */     
/*  89 */     getErrorHandler().error(new IllegalAnnotationException(Messages.DUPLICATE_ANNOTATIONS.format(new Object[] { annotation.getName(), fullName(getter), fullName(setter) }, ), (Annotation)a1, (Annotation)a2));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     return a1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMethodAnnotation(Class<? extends Annotation> annotation, String propertyName, M getter, M setter, Locatable srcPos) {
/* 100 */     boolean x = (getter != null && hasMethodAnnotation(annotation, getter));
/* 101 */     boolean y = (setter != null && hasMethodAnnotation(annotation, setter));
/*     */     
/* 103 */     if (x && y)
/*     */     {
/* 105 */       getMethodAnnotation(annotation, getter, setter, srcPos);
/*     */     }
/*     */     
/* 108 */     return (x || y);
/*     */   }
/*     */   
/*     */   protected abstract String fullName(M paramM);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\AbstractInlineAnnotationReaderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */