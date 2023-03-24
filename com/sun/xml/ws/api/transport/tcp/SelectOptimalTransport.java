package com.sun.xml.ws.api.transport.tcp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@WebServiceFeatureAnnotation(id = "com.sun.xml.ws.transport.SelectOptimalTransportFeature", bean = SelectOptimalTransportFeature.class)
public @interface SelectOptimalTransport {
  boolean enabled() default true;
  
  SelectOptimalTransportFeature.Transport transport() default SelectOptimalTransportFeature.Transport.TCP;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\transport\tcp\SelectOptimalTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */