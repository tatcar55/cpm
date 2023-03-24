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
@WebServiceFeatureAnnotation(id = "http://jax-ws.dev.java.net/features/mime", bean = StreamingAttachmentFeature.class)
public @interface StreamingAttachment {
  String dir() default "";
  
  boolean parseEagerly() default false;
  
  long memoryThreshold() default 1048576L;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\StreamingAttachment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */