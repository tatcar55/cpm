package javax.xml.ws;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebFault {
  String name() default "";
  
  String targetNamespace() default "";
  
  String faultBean() default "";
  
  String messageName() default "";
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\WebFault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */