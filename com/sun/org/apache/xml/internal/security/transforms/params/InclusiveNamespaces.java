package com.sun.org.apache.xml.internal.security.transforms.params;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.transforms.TransformParam;
import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class InclusiveNamespaces extends ElementProxy implements TransformParam {
  public static final String _TAG_EC_INCLUSIVENAMESPACES = "InclusiveNamespaces";
  
  public static final String _ATT_EC_PREFIXLIST = "PrefixList";
  
  public static final String ExclusiveCanonicalizationNamespace = "http://www.w3.org/2001/10/xml-exc-c14n#";
  
  public InclusiveNamespaces(Document paramDocument, String paramString) {
    this(paramDocument, prefixStr2Set(paramString));
  }
  
  public InclusiveNamespaces(Document paramDocument, Set paramSet) {
    super(paramDocument);
    StringBuffer stringBuffer = new StringBuffer();
    TreeSet treeSet = new TreeSet(paramSet);
    for (String str : treeSet) {
      if (str.equals("xmlns")) {
        stringBuffer.append("#default ");
        continue;
      } 
      stringBuffer.append(str + " ");
    } 
    this._constructionElement.setAttributeNS(null, "PrefixList", stringBuffer.toString().trim());
  }
  
  public String getInclusiveNamespaces() {
    return this._constructionElement.getAttributeNS(null, "PrefixList");
  }
  
  public InclusiveNamespaces(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
  }
  
  public static SortedSet prefixStr2Set(String paramString) {
    TreeSet treeSet = new TreeSet();
    if (paramString == null || paramString.length() == 0)
      return treeSet; 
    StringTokenizer stringTokenizer = new StringTokenizer(paramString, " \t\r\n");
    while (stringTokenizer.hasMoreTokens()) {
      String str = stringTokenizer.nextToken();
      if (str.equals("#default")) {
        treeSet.add("xmlns");
        continue;
      } 
      treeSet.add(str);
    } 
    return treeSet;
  }
  
  public String getBaseNamespace() {
    return "http://www.w3.org/2001/10/xml-exc-c14n#";
  }
  
  public String getBaseLocalName() {
    return "InclusiveNamespaces";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\params\InclusiveNamespaces.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */