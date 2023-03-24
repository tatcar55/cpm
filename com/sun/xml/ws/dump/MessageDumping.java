package com.sun.xml.ws.dump;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@WebServiceFeatureAnnotation(id = "com.sun.xml.ws.messagedump.MessageDumpingFeature", bean = MessageDumpingFeature.class)
public @interface MessageDumping {
  boolean enabled() default true;
  
  String messageLoggingRoot() default "com.sun.xml.ws.messagedump";
  
  String messageLoggingLevel() default "FINE";
  
  boolean storeMessages() default false;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\dump\MessageDumping.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */