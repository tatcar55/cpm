package com.sun.xml.registry.common;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class ConnectionFactoryFactory implements ObjectFactory {
  public Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable paramHashtable) throws Exception {
    if (paramObject instanceof Reference) {
      Reference reference = (Reference)paramObject;
      return new ConnectionFactoryImpl();
    } 
    return null;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\common\ConnectionFactoryFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */