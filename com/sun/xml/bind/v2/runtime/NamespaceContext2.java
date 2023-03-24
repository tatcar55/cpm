package com.sun.xml.bind.v2.runtime;

import com.sun.istack.NotNull;
import javax.xml.namespace.NamespaceContext;

public interface NamespaceContext2 extends NamespaceContext {
  String declareNamespace(String paramString1, String paramString2, boolean paramBoolean);
  
  int force(@NotNull String paramString1, @NotNull String paramString2);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\NamespaceContext2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */