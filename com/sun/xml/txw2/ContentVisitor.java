package com.sun.xml.txw2;

interface ContentVisitor {
  void onStartDocument();
  
  void onEndDocument();
  
  void onEndTag();
  
  void onPcdata(StringBuilder paramStringBuilder);
  
  void onCdata(StringBuilder paramStringBuilder);
  
  void onStartTag(String paramString1, String paramString2, Attribute paramAttribute, NamespaceDecl paramNamespaceDecl);
  
  void onComment(StringBuilder paramStringBuilder);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\ContentVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */