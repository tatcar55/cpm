package com.sun.org.apache.xml.internal.security.utils;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class IdResolver {
  private static Logger log = Logger.getLogger(IdResolver.class.getName());
  
  private static WeakHashMap docMap = new WeakHashMap();
  
  private static List names;
  
  private static int namesLength = names.size();
  
  public static void registerElementById(Element paramElement, String paramString) {
    WeakHashMap weakHashMap;
    Document document = paramElement.getOwnerDocument();
    synchronized (docMap) {
      weakHashMap = (WeakHashMap)docMap.get(document);
    } 
    if (weakHashMap == null) {
      weakHashMap = new WeakHashMap();
      synchronized (docMap) {
        docMap.put(document, weakHashMap);
      } 
    } 
    weakHashMap.put(paramString, new WeakReference(paramElement));
  }
  
  public static void registerElementById(Element paramElement, Attr paramAttr) {
    registerElementById(paramElement, paramAttr.getNodeValue());
  }
  
  public static Element getElementById(Document paramDocument, String paramString) {
    Element element = getElementByIdType(paramDocument, paramString);
    if (element != null) {
      log.log(Level.FINE, "I could find an Element using the simple getElementByIdType method: " + element.getTagName());
      return element;
    } 
    element = getElementByIdUsingDOM(paramDocument, paramString);
    if (element != null) {
      log.log(Level.FINE, "I could find an Element using the simple getElementByIdUsingDOM method: " + element.getTagName());
      return element;
    } 
    element = getElementBySearching(paramDocument, paramString);
    if (element != null) {
      registerElementById(element, paramString);
      return element;
    } 
    return null;
  }
  
  private static Element getElementByIdUsingDOM(Document paramDocument, String paramString) {
    log.log(Level.FINE, "getElementByIdUsingDOM() Search for ID " + paramString);
    return paramDocument.getElementById(paramString);
  }
  
  private static Element getElementByIdType(Document paramDocument, String paramString) {
    WeakHashMap weakHashMap;
    log.log(Level.FINE, "getElementByIdType() Search for ID " + paramString);
    synchronized (docMap) {
      weakHashMap = (WeakHashMap)docMap.get(paramDocument);
    } 
    if (weakHashMap != null) {
      WeakReference weakReference = (WeakReference)weakHashMap.get(paramString);
      if (weakReference != null)
        return weakReference.get(); 
    } 
    return null;
  }
  
  private static Element getElementBySearching(Node paramNode, String paramString) {
    Element[] arrayOfElement = new Element[namesLength + 1];
    getEl(paramNode, paramString, arrayOfElement);
    for (byte b = 0; b < arrayOfElement.length; b++) {
      if (arrayOfElement[b] != null)
        return arrayOfElement[b]; 
    } 
    return null;
  }
  
  private static int getEl(Node paramNode, String paramString, Element[] paramArrayOfElement) {
    Node node = null;
    Element element = null;
    while (true) {
      Element element1;
      switch (paramNode.getNodeType()) {
        case 9:
        case 11:
          node = paramNode.getFirstChild();
          break;
        case 1:
          element1 = (Element)paramNode;
          if (isElement(element1, paramString, paramArrayOfElement) == 1)
            return 1; 
          node = paramNode.getFirstChild();
          if (node == null) {
            if (element != null)
              node = paramNode.getNextSibling(); 
            break;
          } 
          element = element1;
          break;
      } 
      while (node == null && element != null) {
        node = element.getNextSibling();
        Node node1 = element.getParentNode();
        if (!(node1 instanceof Element))
          node1 = null; 
      } 
      if (node == null)
        return 1; 
      paramNode = node;
      node = paramNode.getNextSibling();
    } 
  }
  
  public static int isElement(Element paramElement, String paramString, Element[] paramArrayOfElement) {
    if (!paramElement.hasAttributes())
      return 0; 
    NamedNodeMap namedNodeMap = paramElement.getAttributes();
    int i = names.indexOf(paramElement.getNamespaceURI());
    i = (i < 0) ? namesLength : i;
    int j = namedNodeMap.getLength();
    for (byte b = 0; b < j; b++) {
      Attr attr = (Attr)namedNodeMap.item(b);
      String str1 = attr.getNamespaceURI();
      int k = (str1 == null) ? i : names.indexOf(attr.getNamespaceURI());
      k = (k < 0) ? namesLength : k;
      String str2 = attr.getLocalName();
      if (str2.length() <= 2) {
        String str = attr.getNodeValue();
        if (str2.charAt(0) == 'I') {
          char c = str2.charAt(1);
          if (c == 'd' && str.equals(paramString)) {
            paramArrayOfElement[k] = paramElement;
            if (k == 0)
              return 1; 
          } else if (c == 'D' && str.endsWith(paramString)) {
            if (k != 3)
              k = namesLength; 
            paramArrayOfElement[k] = paramElement;
          } 
        } else if ("id".equals(str2) && str.equals(paramString)) {
          if (k != 2)
            k = namesLength; 
          paramArrayOfElement[k] = paramElement;
        } 
      } 
    } 
    if (i == 3 && (paramElement.getAttribute("OriginalRequestID").equals(paramString) || paramElement.getAttribute("RequestID").equals(paramString) || paramElement.getAttribute("ResponseID").equals(paramString))) {
      paramArrayOfElement[3] = paramElement;
    } else if (i == 4 && paramElement.getAttribute("AssertionID").equals(paramString)) {
      paramArrayOfElement[4] = paramElement;
    } else if (i == 5 && (paramElement.getAttribute("RequestID").equals(paramString) || paramElement.getAttribute("ResponseID").equals(paramString))) {
      paramArrayOfElement[5] = paramElement;
    } 
    return 0;
  }
  
  static {
    String[] arrayOfString = { "http://www.w3.org/2000/09/xmldsig#", "http://www.w3.org/2001/04/xmlenc#", "http://schemas.xmlsoap.org/soap/security/2000-12", "http://www.w3.org/2002/03/xkms#", "urn:oasis:names:tc:SAML:1.0:assertion", "urn:oasis:names:tc:SAML:1.0:protocol" };
    names = Arrays.asList(arrayOfString);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\IdResolver.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */