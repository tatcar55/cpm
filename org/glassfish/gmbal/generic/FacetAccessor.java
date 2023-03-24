package org.glassfish.gmbal.generic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

public interface FacetAccessor {
  <T> T facet(Class<T> paramClass, boolean paramBoolean);
  
  <T> void addFacet(T paramT);
  
  void removeFacet(Class<?> paramClass);
  
  Collection<Object> facets();
  
  Object invoke(Method paramMethod, boolean paramBoolean, Object... paramVarArgs);
  
  Object get(Field paramField, boolean paramBoolean);
  
  void set(Field paramField, Object paramObject, boolean paramBoolean);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\FacetAccessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */