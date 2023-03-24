package javax.jws.soap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Deprecated
public @interface SOAPMessageHandlers {
  SOAPMessageHandler[] value();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\jws\soap\SOAPMessageHandlers.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */