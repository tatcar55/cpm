package com.sun.org.apache.xml.internal.security.c14n.implementations;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public abstract class Canonicalizer20010315 extends CanonicalizerBase {
  boolean firstCall = true;
  
  final SortedSet result = new TreeSet(COMPARE);
  
  static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
  
  static final String XML_LANG_URI = "http://www.w3.org/XML/1998/namespace";
  
  XmlAttrStack xmlattrStack = new XmlAttrStack();
  
  public Canonicalizer20010315(boolean paramBoolean) {
    super(paramBoolean);
  }
  
  Iterator handleAttributesSubtree(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
    if (!paramElement.hasAttributes() && !this.firstCall)
      return null; 
    SortedSet sortedSet = this.result;
    sortedSet.clear();
    NamedNodeMap namedNodeMap = paramElement.getAttributes();
    int i = namedNodeMap.getLength();
    for (byte b = 0; b < i; b++) {
      Attr attr = (Attr)namedNodeMap.item(b);
      String str = attr.getNamespaceURI();
      if ("http://www.w3.org/2000/xmlns/" != str) {
        sortedSet.add(attr);
      } else {
        String str1 = attr.getLocalName();
        String str2 = attr.getValue();
        if (!"xml".equals(str1) || !"http://www.w3.org/XML/1998/namespace".equals(str2)) {
          Node node = paramNameSpaceSymbTable.addMappingAndRender(str1, str2, attr);
          if (node != null) {
            sortedSet.add(node);
            if (C14nHelper.namespaceIsRelative(attr)) {
              Object[] arrayOfObject = { paramElement.getTagName(), str1, attr.getNodeValue() };
              throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
            } 
          } 
        } 
      } 
    } 
    if (this.firstCall) {
      paramNameSpaceSymbTable.getUnrenderedNodes(sortedSet);
      this.xmlattrStack.getXmlnsAttr(sortedSet);
      this.firstCall = false;
    } 
    return sortedSet.iterator();
  }
  
  Iterator handleAttributes(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
    this.xmlattrStack.push(paramNameSpaceSymbTable.getLevel());
    boolean bool = (isVisibleDO(paramElement, paramNameSpaceSymbTable.getLevel()) == 1) ? true : false;
    NamedNodeMap namedNodeMap = null;
    int i = 0;
    if (paramElement.hasAttributes()) {
      namedNodeMap = paramElement.getAttributes();
      i = namedNodeMap.getLength();
    } 
    SortedSet sortedSet = this.result;
    sortedSet.clear();
    for (byte b = 0; b < i; b++) {
      Attr attr = (Attr)namedNodeMap.item(b);
      String str = attr.getNamespaceURI();
      if ("http://www.w3.org/2000/xmlns/" != str) {
        if ("http://www.w3.org/XML/1998/namespace" == str) {
          this.xmlattrStack.addXmlnsAttr(attr);
        } else if (bool) {
          sortedSet.add(attr);
        } 
      } else {
        String str1 = attr.getLocalName();
        String str2 = attr.getValue();
        if (!"xml".equals(str1) || !"http://www.w3.org/XML/1998/namespace".equals(str2))
          if (isVisible(attr)) {
            if (bool || !paramNameSpaceSymbTable.removeMappingIfRender(str1)) {
              Node node = paramNameSpaceSymbTable.addMappingAndRender(str1, str2, attr);
              if (node != null) {
                sortedSet.add(node);
                if (C14nHelper.namespaceIsRelative(attr)) {
                  Object[] arrayOfObject = { paramElement.getTagName(), str1, attr.getNodeValue() };
                  throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
                } 
              } 
            } 
          } else if (bool && str1 != "xmlns") {
            paramNameSpaceSymbTable.removeMapping(str1);
          } else {
            paramNameSpaceSymbTable.addMapping(str1, str2, attr);
          }  
      } 
    } 
    if (bool) {
      Attr attr = paramElement.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
      Node node = null;
      if (attr == null) {
        node = paramNameSpaceSymbTable.getMapping("xmlns");
      } else if (!isVisible(attr)) {
        node = paramNameSpaceSymbTable.addMappingAndRender("xmlns", "", nullNode);
      } 
      if (node != null)
        sortedSet.add(node); 
      this.xmlattrStack.getXmlnsAttr(sortedSet);
      paramNameSpaceSymbTable.getUnrenderedNodes(sortedSet);
    } 
    return sortedSet.iterator();
  }
  
  public byte[] engineCanonicalizeXPathNodeSet(Set paramSet, String paramString) throws CanonicalizationException {
    throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
  }
  
  public byte[] engineCanonicalizeSubTree(Node paramNode, String paramString) throws CanonicalizationException {
    throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
  }
  
  void circumventBugIfNeeded(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
    if (!paramXMLSignatureInput.isNeedsToBeExpanded())
      return; 
    Document document = null;
    if (paramXMLSignatureInput.getSubNode() != null) {
      document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getSubNode());
    } else {
      document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getNodeSet());
    } 
    XMLUtils.circumventBug2650(document);
  }
  
  void handleParent(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) {
    if (!paramElement.hasAttributes())
      return; 
    this.xmlattrStack.push(-1);
    NamedNodeMap namedNodeMap = paramElement.getAttributes();
    int i = namedNodeMap.getLength();
    for (byte b = 0; b < i; b++) {
      Attr attr = (Attr)namedNodeMap.item(b);
      if ("http://www.w3.org/2000/xmlns/" != attr.getNamespaceURI()) {
        if ("http://www.w3.org/XML/1998/namespace" == attr.getNamespaceURI())
          this.xmlattrStack.addXmlnsAttr(attr); 
      } else {
        String str1 = attr.getLocalName();
        String str2 = attr.getNodeValue();
        if (!"xml".equals(str1) || !"http://www.w3.org/XML/1998/namespace".equals(str2))
          paramNameSpaceSymbTable.addMapping(str1, str2, attr); 
      } 
    } 
  }
  
  static class XmlAttrStack {
    int currentLevel = 0;
    
    int lastlevel = 0;
    
    XmlsStackElement cur;
    
    List levels = new ArrayList();
    
    void push(int param1Int) {
      this.currentLevel = param1Int;
      if (this.currentLevel == -1)
        return; 
      this.cur = null;
      while (this.lastlevel >= this.currentLevel) {
        this.levels.remove(this.levels.size() - 1);
        if (this.levels.size() == 0) {
          this.lastlevel = 0;
          return;
        } 
        this.lastlevel = ((XmlsStackElement)this.levels.get(this.levels.size() - 1)).level;
      } 
    }
    
    void addXmlnsAttr(Attr param1Attr) {
      if (this.cur == null) {
        this.cur = new XmlsStackElement();
        this.cur.level = this.currentLevel;
        this.levels.add(this.cur);
        this.lastlevel = this.currentLevel;
      } 
      this.cur.nodes.add(param1Attr);
    }
    
    void getXmlnsAttr(Collection param1Collection) {
      int i = this.levels.size() - 1;
      if (this.cur == null) {
        this.cur = new XmlsStackElement();
        this.cur.level = this.currentLevel;
        this.lastlevel = this.currentLevel;
        this.levels.add(this.cur);
      } 
      boolean bool = false;
      XmlsStackElement xmlsStackElement = null;
      if (i == -1) {
        bool = true;
      } else {
        xmlsStackElement = this.levels.get(i);
        if (xmlsStackElement.rendered && xmlsStackElement.level + 1 == this.currentLevel)
          bool = true; 
      } 
      if (bool) {
        param1Collection.addAll(this.cur.nodes);
        this.cur.rendered = true;
        return;
      } 
      HashMap hashMap = new HashMap();
      while (i >= 0) {
        xmlsStackElement = this.levels.get(i);
        for (Attr attr : xmlsStackElement.nodes) {
          if (!hashMap.containsKey(attr.getName()))
            hashMap.put(attr.getName(), attr); 
        } 
        i--;
      } 
      this.cur.rendered = true;
      param1Collection.addAll(hashMap.values());
    }
    
    static class XmlsStackElement {
      int level;
      
      boolean rendered = false;
      
      List nodes = new ArrayList();
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\c14n\implementations\Canonicalizer20010315.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */