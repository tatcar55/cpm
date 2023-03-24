/*     */ package javax.annotation;
/*     */ 
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
/*     */ @Retention(RetentionPolicy.RUNTIME)
/*     */ public @interface Resource
/*     */ {
/*     */   String name() default "";
/*     */   
/*     */   String lookup() default "";
/*     */   
/*     */   Class<?> type() default Object.class;
/*     */   
/*     */   AuthenticationType authenticationType() default AuthenticationType.CONTAINER;
/*     */   
/*     */   boolean shareable() default true;
/*     */   
/*     */   String mappedName() default "";
/*     */   
/*     */   String description() default "";
/*     */   
/*     */   public enum AuthenticationType
/*     */   {
/* 102 */     CONTAINER,
/* 103 */     APPLICATION;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\annotation\Resource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */