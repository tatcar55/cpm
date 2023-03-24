package com.sun.xml.ws.developer.servlet;

import com.sun.xml.ws.api.server.InstanceResolverAnnotation;
import com.sun.xml.ws.server.servlet.HttpSessionInstanceResolver;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@WebServiceFeatureAnnotation(id = "http://jax-ws.dev.java.net/features/servlet/httpSessionScope", bean = HttpSessionScopeFeature.class)
@InstanceResolverAnnotation(HttpSessionInstanceResolver.class)
public @interface HttpSessionScope {}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\servlet\HttpSessionScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */