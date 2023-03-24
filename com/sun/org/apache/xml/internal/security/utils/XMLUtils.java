package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XMLUtils {
  private static boolean ignoreLineBreaks = false;
  
  static String dsPrefix = null;
  
  static Map namePrefixes = new HashMap();
  
  public static Element getNextElement(Node paramNode) {
    while (paramNode != null && paramNode.getNodeType() != 1)
      paramNode = paramNode.getNextSibling(); 
    return (Element)paramNode;
  }
  
  public static void getSet(Node paramNode1, Set paramSet, Node paramNode2, boolean paramBoolean) {
    if (paramNode2 != null && isDescendantOrSelf(paramNode2, paramNode1))
      return; 
    getSetRec(paramNode1, paramSet, paramNode2, paramBoolean);
  }
  
  static final void getSetRec(Node paramNode1, Set paramSet, Node paramNode2, boolean paramBoolean) {
    Element element;
    Node node;
    if (paramNode1 == paramNode2)
      return; 
    switch (paramNode1.getNodeType()) {
      case 1:
        paramSet.add(paramNode1);
        element = (Element)paramNode1;
        if (element.hasAttributes()) {
          NamedNodeMap namedNodeMap = ((Element)paramNode1).getAttributes();
          for (byte b = 0; b < namedNodeMap.getLength(); b++)
            paramSet.add(namedNodeMap.item(b)); 
        } 
      case 9:
        for (node = paramNode1.getFirstChild(); node != null; node = node.getNextSibling()) {
          if (node.getNodeType() == 3) {
            paramSet.add(node);
            while (node != null && node.getNodeType() == 3)
              node = node.getNextSibling(); 
            if (node == null)
              return; 
          } 
          getSetRec(node, paramSet, paramNode2, paramBoolean);
        } 
        return;
      case 8:
        if (paramBoolean)
          paramSet.add(paramNode1); 
        return;
      case 10:
        return;
    } 
    paramSet.add(paramNode1);
  }
  
  public static void outputDOM(Node paramNode, OutputStream paramOutputStream) {
    outputDOM(paramNode, paramOutputStream, false);
  }
  
  public static void outputDOM(Node paramNode, OutputStream paramOutputStream, boolean paramBoolean) {
    try {
      if (paramBoolean)
        paramOutputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes()); 
      paramOutputStream.write(Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments").canonicalizeSubtree(paramNode));
    } catch (IOException iOException) {
    
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      invalidCanonicalizerException.printStackTrace();
    } catch (CanonicalizationException canonicalizationException) {
      canonicalizationException.printStackTrace();
    } 
  }
  
  public static void outputDOMc14nWithComments(Node paramNode, OutputStream paramOutputStream) {
    try {
      paramOutputStream.write(Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments").canonicalizeSubtree(paramNode));
    } catch (IOException iOException) {
    
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
    
    } catch (CanonicalizationException canonicalizationException) {}
  }
  
  public static String getFullTextChildrenFromElement(Element paramElement) {
    StringBuffer stringBuffer = new StringBuffer();
    NodeList nodeList = paramElement.getChildNodes();
    int i = nodeList.getLength();
    for (byte b = 0; b < i; b++) {
      Node node = nodeList.item(b);
      if (node.getNodeType() == 3)
        stringBuffer.append(((Text)node).getData()); 
    } 
    return stringBuffer.toString();
  }
  
  public static Element createElementInSignatureSpace(Document paramDocument, String paramString) {
    if (paramDocument == null)
      throw new RuntimeException("Document is null"); 
    if (dsPrefix == null || dsPrefix.length() == 0)
      return paramDocument.createElementNS("http://www.w3.org/2000/09/xmldsig#", paramString); 
    String str = (String)namePrefixes.get(paramString);
    if (str == null) {
      StringBuffer stringBuffer = new StringBuffer(dsPrefix);
      stringBuffer.append(':');
      stringBuffer.append(paramString);
      str = stringBuffer.toString();
      namePrefixes.put(paramString, str);
    } 
    return paramDocument.createElementNS("http://www.w3.org/2000/09/xmldsig#", str);
  }
  
  public static boolean elementIsInSignatureSpace(Element paramElement, String paramString) {
    return ElementProxy.checker.isNamespaceElement(paramElement, paramString, "http://www.w3.org/2000/09/xmldsig#");
  }
  
  public static boolean elementIsInEncryptionSpace(Element paramElement, String paramString) {
    return ElementProxy.checker.isNamespaceElement(paramElement, paramString, "http://www.w3.org/2001/04/xmlenc#");
  }
  
  public static Document getOwnerDocument(Node paramNode) {
    if (paramNode.getNodeType() == 9)
      return (Document)paramNode; 
    try {
      return paramNode.getOwnerDocument();
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException(I18n.translate("endorsed.jdk1.4.0") + " Original message was \"" + nullPointerException.getMessage() + "\"");
    } 
  }
  
  public static Document getOwnerDocument(Set paramSet) {
    NullPointerException nullPointerException = null;
    for (Node node : paramSet) {
      short s = node.getNodeType();
      if (s == 9)
        return (Document)node; 
      try {
        return (s == 2) ? ((Attr)node).getOwnerElement().getOwnerDocument() : node.getOwnerDocument();
      } catch (NullPointerException nullPointerException1) {
        nullPointerException = nullPointerException1;
      } 
    } 
    throw new NullPointerException(I18n.translate("endorsed.jdk1.4.0") + " Original message was \"" + ((nullPointerException == null) ? "" : nullPointerException.getMessage()) + "\"");
  }
  
  public static Element createDSctx(Document paramDocument, String paramString1, String paramString2) {
    if (paramString1 == null || paramString1.trim().length() == 0)
      throw new IllegalArgumentException("You must supply a prefix"); 
    Element element = paramDocument.createElementNS(null, "namespaceContext");
    element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + paramString1.trim(), paramString2);
    return element;
  }
  
  public static void addReturnToElement(Element paramElement) {
    if (!ignoreLineBreaks) {
      Document document = paramElement.getOwnerDocument();
      paramElement.appendChild(document.createTextNode("\n"));
    } 
  }
  
  public static void addReturnToElement(Document paramDocument, HelperNodeList paramHelperNodeList) {
    if (!ignoreLineBreaks)
      paramHelperNodeList.appendChild(paramDocument.createTextNode("\n")); 
  }
  
  public static void addReturnBeforeChild(Element paramElement, Node paramNode) {
    if (!ignoreLineBreaks) {
      Document document = paramElement.getOwnerDocument();
      paramElement.insertBefore(document.createTextNode("\n"), paramNode);
    } 
  }
  
  public static Set convertNodelistToSet(NodeList paramNodeList) {
    if (paramNodeList == null)
      return new HashSet(); 
    int i = paramNodeList.getLength();
    HashSet hashSet = new HashSet(i);
    for (byte b = 0; b < i; b++)
      hashSet.add(paramNodeList.item(b)); 
    return hashSet;
  }
  
  public static void circumventBug2650(Document paramDocument) {
    Element element = paramDocument.getDocumentElement();
    Attr attr = element.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
    if (attr == null)
      element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", ""); 
    circumventBug2650internal(paramDocument);
  }
  
  private static void circumventBug2650internal(Node paramNode) {
    Node node1 = null;
    for (Node node2 = null;; node2 = paramNode.getNextSibling()) {
      Element element;
      switch (paramNode.getNodeType()) {
        case 1:
          element = (Element)paramNode;
          if (!element.hasChildNodes())
            break; 
          if (element.hasAttributes()) {
            NamedNodeMap namedNodeMap = element.getAttributes();
            int i = namedNodeMap.getLength();
            for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
              if (node.getNodeType() == 1) {
                Element element1 = (Element)node;
                for (byte b = 0; b < i; b++) {
                  Attr attr = (Attr)namedNodeMap.item(b);
                  if ("http://www.w3.org/2000/xmlns/" == attr.getNamespaceURI() && !element1.hasAttributeNS("http://www.w3.org/2000/xmlns/", attr.getLocalName()))
                    element1.setAttributeNS("http://www.w3.org/2000/xmlns/", attr.getName(), attr.getNodeValue()); 
                } 
              } 
            } 
          } 
        case 5:
        case 9:
          node1 = paramNode;
          node2 = paramNode.getFirstChild();
          break;
      } 
      while (node2 == null && node1 != null) {
        node2 = node1.getNextSibling();
        node1 = node1.getParentNode();
      } 
      if (node2 == null)
        return; 
      paramNode = node2;
    } 
  }
  
  public static Element selectDsNode(Node paramNode, String paramString, int paramInt) {
    while (paramNode != null) {
      if (ElementProxy.checker.isNamespaceElement(paramNode, paramString, "http://www.w3.org/2000/09/xmldsig#")) {
        if (paramInt == 0)
          return (Element)paramNode; 
        paramInt--;
      } 
      paramNode = paramNode.getNextSibling();
    } 
    return null;
  }
  
  public static Element selectXencNode(Node paramNode, String paramString, int paramInt) {
    while (paramNode != null) {
      if (ElementProxy.checker.isNamespaceElement(paramNode, paramString, "http://www.w3.org/2001/04/xmlenc#")) {
        if (paramInt == 0)
          return (Element)paramNode; 
        paramInt--;
      } 
      paramNode = paramNode.getNextSibling();
    } 
    return null;
  }
  
  public static Text selectDsNodeText(Node paramNode, String paramString, int paramInt) {
    Element element = selectDsNode(paramNode, paramString, paramInt);
    if (element == null)
      return null; 
    Node node;
    for (node = element.getFirstChild(); node != null && node.getNodeType() != 3; node = node.getNextSibling());
    return (Text)node;
  }
  
  public static Text selectNodeText(Node paramNode, String paramString1, String paramString2, int paramInt) {
    Element element = selectNode(paramNode, paramString1, paramString2, paramInt);
    if (element == null)
      return null; 
    Node node;
    for (node = element.getFirstChild(); node != null && node.getNodeType() != 3; node = node.getNextSibling());
    return (Text)node;
  }
  
  public static Element selectNode(Node paramNode, String paramString1, String paramString2, int paramInt) {
    while (paramNode != null) {
      if (ElementProxy.checker.isNamespaceElement(paramNode, paramString2, paramString1)) {
        if (paramInt == 0)
          return (Element)paramNode; 
        paramInt--;
      } 
      paramNode = paramNode.getNextSibling();
    } 
    return null;
  }
  
  public static Element[] selectDsNodes(Node paramNode, String paramString) {
    return selectNodes(paramNode, "http://www.w3.org/2000/09/xmldsig#", paramString);
  }
  
  public static Element[] selectNodes(Node paramNode, String paramString1, String paramString2) {
    int i = 20;
    Element[] arrayOfElement1 = new Element[i];
    byte b = 0;
    while (paramNode != null) {
      if (ElementProxy.checker.isNamespaceElement(paramNode, paramString2, paramString1)) {
        arrayOfElement1[b++] = (Element)paramNode;
        if (i <= b) {
          int j = i << 2;
          Element[] arrayOfElement = new Element[j];
          System.arraycopy(arrayOfElement1, 0, arrayOfElement, 0, i);
          arrayOfElement1 = arrayOfElement;
          i = j;
        } 
      } 
      paramNode = paramNode.getNextSibling();
    } 
    Element[] arrayOfElement2 = new Element[b];
    System.arraycopy(arrayOfElement1, 0, arrayOfElement2, 0, b);
    return arrayOfElement2;
  }
  
  public static Set excludeNodeFromSet(Node paramNode, Set paramSet) {
    HashSet hashSet = new HashSet();
    for (Node node : paramSet) {
      if (!isDescendantOrSelf(paramNode, node))
        hashSet.add(node); 
    } 
    return hashSet;
  }
  
  public static boolean isDescendantOrSelf(Node paramNode1, Node paramNode2) {
    if (paramNode1 == paramNode2)
      return true; 
    for (Node node = paramNode2;; node = node.getParentNode()) {
      if (node == null)
        return false; 
      if (node == paramNode1)
        return true; 
      if (node.getNodeType() == 2) {
        node = ((Attr)node).getOwnerElement();
        continue;
      } 
    } 
  }
  
  public static boolean ignoreLineBreaks() {
    return ignoreLineBreaks;
  }
  
  static {
    try {
      ignoreLineBreaks = Boolean.getBoolean("com.sun.org.apache.xml.internal.security.ignoreLineBreaks");
    } catch (Exception exception) {}
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\XMLUtils.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */