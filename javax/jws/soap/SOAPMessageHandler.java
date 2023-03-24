package javax.jws.soap;

@Deprecated
public @interface SOAPMessageHandler {
  String name() default "";
  
  String className();
  
  InitParam[] initParams() default {};
  
  String[] roles() default {};
  
  String[] headers() default {};
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\jws\soap\SOAPMessageHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */