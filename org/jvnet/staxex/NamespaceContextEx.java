package org.jvnet.staxex;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

public interface NamespaceContextEx extends NamespaceContext, Iterable<NamespaceContextEx.Binding> {
  Iterator<Binding> iterator();
  
  public static interface Binding {
    String getPrefix();
    
    String getNamespaceURI();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\staxex\NamespaceContextEx.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */