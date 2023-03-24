package com.sun.xml.ws.developer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Documented
@WebServiceFeatureAnnotation(id = "http://jax-ws.dev.java.net/features/schema-validation", bean = SchemaValidationFeature.class)
public @interface SchemaValidation {
  Class<? extends ValidationErrorHandler> handler() default com.sun.xml.ws.server.DraconianValidationErrorHandler.class;
  
  boolean inbound() default true;
  
  boolean outbound() default true;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\SchemaValidation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */