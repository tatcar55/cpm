package com.sun.xml.ws.rx.mc.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@WebServiceFeatureAnnotation(id = "http://docs.oasis-open.org/ws-rx/wsmc/", bean = MakeConnectionSupportedFeature.class)
public @interface MakeConnectionSupported {
  boolean enabled() default true;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\api\MakeConnectionSupported.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */