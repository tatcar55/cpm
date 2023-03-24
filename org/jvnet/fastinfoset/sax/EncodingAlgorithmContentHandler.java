package org.jvnet.fastinfoset.sax;

import org.xml.sax.SAXException;

public interface EncodingAlgorithmContentHandler {
  void octets(String paramString, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) throws SAXException;
  
  void object(String paramString, int paramInt, Object paramObject) throws SAXException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\sax\EncodingAlgorithmContentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */