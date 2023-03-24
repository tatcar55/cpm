package com.sun.xml.rpc.streaming;

import com.sun.xml.rpc.util.xml.CDATA;
import javax.xml.namespace.QName;

public interface XMLWriter {
  void startElement(QName paramQName);
  
  void startElement(QName paramQName, String paramString);
  
  void startElement(String paramString);
  
  void startElement(String paramString1, String paramString2);
  
  void startElement(String paramString1, String paramString2, String paramString3);
  
  void writeAttribute(QName paramQName, String paramString);
  
  void writeAttribute(String paramString1, String paramString2);
  
  void writeAttribute(String paramString1, String paramString2, String paramString3);
  
  void writeAttributeUnquoted(QName paramQName, String paramString);
  
  void writeAttributeUnquoted(String paramString1, String paramString2);
  
  void writeAttributeUnquoted(String paramString1, String paramString2, String paramString3);
  
  void writeNamespaceDeclaration(String paramString1, String paramString2);
  
  void writeNamespaceDeclaration(String paramString);
  
  void writeChars(String paramString);
  
  void writeChars(CDATA paramCDATA);
  
  void writeCharsUnquoted(String paramString);
  
  void writeCharsUnquoted(char[] paramArrayOfchar, int paramInt1, int paramInt2);
  
  void writeComment(String paramString);
  
  void endElement();
  
  PrefixFactory getPrefixFactory();
  
  void setPrefixFactory(PrefixFactory paramPrefixFactory);
  
  String getURI(String paramString);
  
  String getPrefix(String paramString);
  
  void flush();
  
  void close();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */