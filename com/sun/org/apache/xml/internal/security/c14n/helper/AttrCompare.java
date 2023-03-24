package com.sun.org.apache.xml.internal.security.c14n.helper;

import java.io.Serializable;
import java.util.Comparator;
import org.w3c.dom.Attr;

public class AttrCompare implements Comparator, Serializable {
  private static final long serialVersionUID = -7113259629930576230L;
  
  private static final int ATTR0_BEFORE_ATTR1 = -1;
  
  private static final int ATTR1_BEFORE_ATTR0 = 1;
  
  private static final String XMLNS = "http://www.w3.org/2000/xmlns/";
  
  public int compare(Object paramObject1, Object paramObject2) {
    Attr attr1 = (Attr)paramObject1;
    Attr attr2 = (Attr)paramObject2;
    String str1 = attr1.getNamespaceURI();
    String str2 = attr2.getNamespaceURI();
    boolean bool1 = ("http://www.w3.org/2000/xmlns/" == str1) ? true : false;
    boolean bool2 = ("http://www.w3.org/2000/xmlns/" == str2) ? true : false;
    if (bool1) {
      if (bool2) {
        String str3 = attr1.getLocalName();
        String str4 = attr2.getLocalName();
        if (str3.equals("xmlns"))
          str3 = ""; 
        if (str4.equals("xmlns"))
          str4 = ""; 
        return str3.compareTo(str4);
      } 
      return -1;
    } 
    if (bool2)
      return 1; 
    if (str1 == null) {
      if (str2 == null) {
        String str3 = attr1.getName();
        String str4 = attr2.getName();
        return str3.compareTo(str4);
      } 
      return -1;
    } 
    if (str2 == null)
      return 1; 
    int i = str1.compareTo(str2);
    return (i != 0) ? i : attr1.getLocalName().compareTo(attr2.getLocalName());
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\c14n\helper\AttrCompare.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */