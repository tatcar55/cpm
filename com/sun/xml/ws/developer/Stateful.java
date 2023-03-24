package com.sun.xml.ws.developer;

import com.sun.xml.ws.api.server.InstanceResolverAnnotation;
import com.sun.xml.ws.server.StatefulInstanceResolver;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@WebServiceFeatureAnnotation(id = "http://jax-ws.dev.java.net/features/stateful", bean = StatefulFeature.class)
@InstanceResolverAnnotation(StatefulInstanceResolver.class)
public @interface Stateful {}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\Stateful.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */