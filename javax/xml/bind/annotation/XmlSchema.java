package javax.xml.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE})
public @interface XmlSchema {
  public static final String NO_LOCATION = "##generate";
  
  XmlNs[] xmlns() default {};
  
  String namespace() default "";
  
  XmlNsForm elementFormDefault() default XmlNsForm.UNSET;
  
  XmlNsForm attributeFormDefault() default XmlNsForm.UNSET;
  
  String location() default "##generate";
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\annotation\XmlSchema.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */