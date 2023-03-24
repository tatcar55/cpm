package com.oracle.webservices.api.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

public interface PropertySet {
  boolean containsKey(Object paramObject);
  
  Object get(Object paramObject);
  
  Object put(String paramString, Object paramObject);
  
  boolean supports(Object paramObject);
  
  Object remove(Object paramObject);
  
  @Deprecated
  Map<String, Object> createMapView();
  
  Map<String, Object> asMap();
  
  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.METHOD})
  public static @interface Property {
    String[] value();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\message\PropertySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */