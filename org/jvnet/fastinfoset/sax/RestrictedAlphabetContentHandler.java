package org.jvnet.fastinfoset.sax;

import org.xml.sax.SAXException;

public interface RestrictedAlphabetContentHandler {
  void numericCharacters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException;
  
  void dateTimeCharacters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException;
  
  void alphabetCharacters(String paramString, char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\sax\RestrictedAlphabetContentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */