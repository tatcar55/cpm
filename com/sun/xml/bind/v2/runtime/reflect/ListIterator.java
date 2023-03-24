package com.sun.xml.bind.v2.runtime.reflect;

import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

public interface ListIterator<E> {
  boolean hasNext();
  
  E next() throws SAXException, JAXBException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\ListIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */