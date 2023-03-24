package javax.xml.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface XmlElementRef {
  Class type() default DEFAULT.class;
  
  String namespace() default "";
  
  String name() default "##default";
  
  boolean required() default true;
  
  public static final class DEFAULT {}
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\annotation\XmlElementRef.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */