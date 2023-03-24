package javanet.staxutils;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

public interface ExtendedNamespaceContext extends NamespaceContext {
  NamespaceContext getParent();
  
  boolean isPrefixDeclared(String paramString);
  
  Iterator getPrefixes();
  
  Iterator getDeclaredPrefixes();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\ExtendedNamespaceContext.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */