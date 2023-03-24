package com.sun.xml.ws.api.databinding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public interface MetadataReader {
  Annotation[] getAnnotations(Method paramMethod);
  
  Annotation[][] getParameterAnnotations(Method paramMethod);
  
  <A extends Annotation> A getAnnotation(Class<A> paramClass, Method paramMethod);
  
  <A extends Annotation> A getAnnotation(Class<A> paramClass, Class<?> paramClass1);
  
  Annotation[] getAnnotations(Class<?> paramClass);
  
  void getProperties(Map<String, Object> paramMap, Class<?> paramClass);
  
  void getProperties(Map<String, Object> paramMap, Method paramMethod);
  
  void getProperties(Map<String, Object> paramMap, Method paramMethod, int paramInt);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\databinding\MetadataReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */