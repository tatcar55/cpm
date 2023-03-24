package org.glassfish.ha.store.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public interface Storeable extends Serializable {
  long _storeable_getVersion();
  
  void _storeable_setVersion(long paramLong);
  
  long _storeable_getLastAccessTime();
  
  void _storeable_setLastAccessTime(long paramLong);
  
  long _storeable_getMaxIdleTime();
  
  void _storeable_setMaxIdleTime(long paramLong);
  
  String[] _storeable_getAttributeNames();
  
  boolean[] _storeable_getDirtyStatus();
  
  void _storeable_writeState(OutputStream paramOutputStream) throws IOException;
  
  void _storeable_readState(InputStream paramInputStream) throws IOException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\api\Storeable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */