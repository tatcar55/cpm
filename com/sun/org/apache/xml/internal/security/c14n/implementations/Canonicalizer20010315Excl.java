package com.sun.org.apache.xml.internal.security.c14n.implementations;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.params.InclusiveNamespaces;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.IOException;
import java.util.Iterator;
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

public abstract class Canonicalizer20010315Excl extends CanonicalizerBase {
  TreeSet _inclusiveNSSet = new TreeSet();
  
  static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
  
  final SortedSet result = new TreeSet(COMPARE);
  
  public Canonicalizer20010315Excl(boolean paramBoolean) {
    super(paramBoolean);
  }
  
  public byte[] engineCanonicalizeSubTree(Node paramNode) throws CanonicalizationException {
    return engineCanonicalizeSubTree(paramNode, "", (Node)null);
  }
  
  public byte[] engineCanonicalizeSubTree(Node paramNode, String paramString) throws CanonicalizationException {
    return engineCanonicalizeSubTree(paramNode, paramString, (Node)null);
  }
  
  public byte[] engineCanonicalizeSubTree(Node paramNode1, String paramString, Node paramNode2) throws CanonicalizationException {
    this._inclusiveNSSet = (TreeSet)InclusiveNamespaces.prefixStr2Set(paramString);
    return engineCanonicalizeSubTree(paramNode1, paramNode2);
  }
  
  public byte[] engineCanonicalize(XMLSignatureInput paramXMLSignatureInput, String paramString) throws CanonicalizationException {
    this._inclusiveNSSet = (TreeSet)InclusiveNamespaces.prefixStr2Set(paramString);
    return engineCanonicalize(paramXMLSignatureInput);
  }
  
  Iterator handleAttributesSubtree(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
    String str;
    SortedSet sortedSet = this.result;
    sortedSet.clear();
    NamedNodeMap namedNodeMap = null;
    int i = 0;
    if (paramElement.hasAttributes()) {
      namedNodeMap = paramElement.getAttributes();
      i = namedNodeMap.getLength();
    } 
    SortedSet sortedSet1 = (SortedSet)this._inclusiveNSSet.clone();
    for (byte b = 0; b < i; b++) {
      Attr attr = (Attr)namedNodeMap.item(b);
      if ("http://www.w3.org/2000/xmlns/" != attr.getNamespaceURI()) {
        String str1 = attr.getPrefix();
        if (str1 != null && !str1.equals("xml") && !str1.equals("xmlns"))
          sortedSet1.add(str1); 
        sortedSet.add(attr);
      } else {
        String str1 = attr.getLocalName();
        String str2 = attr.getNodeValue();
        if (paramNameSpaceSymbTable.addMapping(str1, str2, attr) && C14nHelper.namespaceIsRelative(str2)) {
          Object[] arrayOfObject = { paramElement.getTagName(), str1, attr.getNodeValue() };
          throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
        } 
      } 
    } 
    if (paramElement.getNamespaceURI() != null) {
      str = paramElement.getPrefix();
      if (str == null || str.length() == 0)
        str = "xmlns"; 
    } else {
      str = "xmlns";
    } 
    sortedSet1.add(str);
    for (String str1 : sortedSet1) {
      Attr attr = paramNameSpaceSymbTable.getMapping(str1);
      if (attr == null)
        continue; 
      sortedSet.add(attr);
    } 
    return sortedSet.iterator();
  }
  
  public byte[] engineCanonicalizeXPathNodeSet(Set paramSet, String paramString) throws CanonicalizationException {
    this._inclusiveNSSet = (TreeSet)InclusiveNamespaces.prefixStr2Set(paramString);
    return engineCanonicalizeXPathNodeSet(paramSet);
  }
  
  final Iterator handleAttributes(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException {
    SortedSet sortedSet = this.result;
    sortedSet.clear();
    NamedNodeMap namedNodeMap = null;
    int i = 0;
    if (paramElement.hasAttributes()) {
      namedNodeMap = paramElement.getAttributes();
      i = namedNodeMap.getLength();
    } 
    Set set = null;
    boolean bool = (isVisibleDO(paramElement, paramNameSpaceSymbTable.getLevel()) == 1) ? true : false;
    if (bool)
      set = (Set)this._inclusiveNSSet.clone(); 
    for (byte b = 0; b < i; b++) {
      Attr attr = (Attr)namedNodeMap.item(b);
      if ("http://www.w3.org/2000/xmlns/" != attr.getNamespaceURI()) {
        if (isVisible(attr) && bool) {
          String str = attr.getPrefix();
          if (str != null && !str.equals("xml") && !str.equals("xmlns"))
            set.add(str); 
          sortedSet.add(attr);
        } 
      } else {
        String str = attr.getLocalName();
        if (bool && !isVisible(attr) && str != "xmlns") {
          paramNameSpaceSymbTable.removeMappingIfNotRender(str);
        } else {
          String str1 = attr.getNodeValue();
          if (!bool && isVisible(attr) && this._inclusiveNSSet.contains(str) && !paramNameSpaceSymbTable.removeMappingIfRender(str)) {
            Node node = paramNameSpaceSymbTable.addMappingAndRender(str, str1, attr);
            if (node != null) {
              sortedSet.add(node);
              if (C14nHelper.namespaceIsRelative(attr)) {
                Object[] arrayOfObject = { paramElement.getTagName(), str, attr.getNodeValue() };
                throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
              } 
            } 
          } 
          if (paramNameSpaceSymbTable.addMapping(str, str1, attr) && C14nHelper.namespaceIsRelative(str1)) {
            Object[] arrayOfObject = { paramElement.getTagName(), str, attr.getNodeValue() };
            throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", arrayOfObject);
          } 
        } 
      } 
    } 
    if (bool) {
      Attr attr = paramElement.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
      if (attr != null && !isVisible(attr))
        paramNameSpaceSymbTable.addMapping("xmlns", "", nullNode); 
      if (paramElement.getNamespaceURI() != null) {
        String str = paramElement.getPrefix();
        if (str == null || str.length() == 0) {
          set.add("xmlns");
        } else {
          set.add(str);
        } 
      } else {
        set.add("xmlns");
      } 
      for (String str : set) {
        Attr attr1 = paramNameSpaceSymbTable.getMapping(str);
        if (attr1 == null)
          continue; 
        sortedSet.add(attr1);
      } 
    } 
    return sortedSet.iterator();
  }
  
  void circumventBugIfNeeded(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
    if (!paramXMLSignatureInput.isNeedsToBeExpanded() || this._inclusiveNSSet.isEmpty())
      return; 
    Document document = null;
    if (paramXMLSignatureInput.getSubNode() != null) {
      document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getSubNode());
    } else {
      document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getNodeSet());
    } 
    XMLUtils.circumventBug2650(document);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\c14n\implementations\Canonicalizer20010315Excl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */