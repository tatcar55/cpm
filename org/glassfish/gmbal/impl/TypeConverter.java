package org.glassfish.gmbal.impl;

import javax.management.openmbean.OpenType;
import org.glassfish.gmbal.typelib.EvaluatedType;

public interface TypeConverter {
  EvaluatedType getDataType();
  
  OpenType getManagedType();
  
  Object toManagedEntity(Object paramObject);
  
  Object fromManagedEntity(Object paramObject);
  
  boolean isIdentity();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\TypeConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */