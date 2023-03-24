/*    */ package javax.jws.soap;@Retention(RetentionPolicy.RUNTIME)
/*    */ @Target({ElementType.TYPE, ElementType.METHOD})
/*    */ public @interface SOAPBinding { Style style() default Style.DOCUMENT;
/*    */   
/*    */   Use use() default Use.LITERAL;
/*    */   
/*    */   ParameterStyle parameterStyle() default ParameterStyle.WRAPPED;
/*    */   
/*    */   public enum Style {
/* 10 */     DOCUMENT,
/* 11 */     RPC;
/*    */   }
/*    */   
/*    */   public enum Use {
/* 15 */     LITERAL,
/* 16 */     ENCODED;
/*    */   }
/*    */   
/*    */   public enum ParameterStyle {
/* 20 */     BARE,
/* 21 */     WRAPPED;
/*    */   } }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\jws\soap\SOAPBinding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */