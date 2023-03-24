package org.jcp.xml.dsig.internal.dom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class Utils {
  public static byte[] readBytesFromStream(InputStream paramInputStream) throws IOException {
    int i;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[1024];
    do {
      i = paramInputStream.read(arrayOfByte);
      if (i == -1)
        break; 
      byteArrayOutputStream.write(arrayOfByte, 0, i);
    } while (i >= 1024);
    return byteArrayOutputStream.toByteArray();
  }
  
  static Set toNodeSet(Iterator paramIterator) {
    HashSet hashSet = new HashSet();
    while (paramIterator.hasNext()) {
      Node node = paramIterator.next();
      hashSet.add(node);
      if (node.getNodeType() == 1) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        byte b = 0;
        int i = namedNodeMap.getLength();
        while (b < i) {
          hashSet.add(namedNodeMap.item(b));
          b++;
        } 
      } 
    } 
    return hashSet;
  }
  
  public static String parseIdFromSameDocumentURI(String paramString) {
    if (paramString.length() == 0)
      return null; 
    String str = paramString.substring(1);
    if (str != null && str.startsWith("xpointer(id(")) {
      int i = str.indexOf('\'');
      int j = str.indexOf('\'', i + 1);
      str = str.substring(i + 1, j);
    } 
    return str;
  }
  
  public static boolean sameDocumentURI(String paramString) {
    return (paramString != null && (paramString.length() == 0 || paramString.charAt(0) == '#'));
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\Utils.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */