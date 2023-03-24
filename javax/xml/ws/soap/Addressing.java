package javax.xml.ws.soap;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WebServiceFeatureAnnotation(id = "http://www.w3.org/2005/08/addressing/module", bean = AddressingFeature.class)
public @interface Addressing {
  boolean enabled() default true;
  
  boolean required() default false;
  
  AddressingFeature.Responses responses() default AddressingFeature.Responses.ALL;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\soap\Addressing.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */