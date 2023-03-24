/*    */ package javax.jws;@Retention(RetentionPolicy.RUNTIME)
/*    */ @Target({ElementType.PARAMETER})
/*    */ public @interface WebParam { String name() default "";
/*    */   String partName() default "";
/*    */   String targetNamespace() default "";
/*    */   Mode mode() default Mode.IN;
/*    */   
/*    */   boolean header() default false;
/*    */   
/* 10 */   public enum Mode { IN,
/* 11 */     OUT,
/* 12 */     INOUT; }
/*    */    }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\jws\WebParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */