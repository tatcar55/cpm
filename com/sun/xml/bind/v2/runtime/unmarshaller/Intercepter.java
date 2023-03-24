package com.sun.xml.bind.v2.runtime.unmarshaller;

import org.xml.sax.SAXException;

public interface Intercepter {
  Object intercept(UnmarshallingContext.State paramState, Object paramObject) throws SAXException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\Intercepter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */