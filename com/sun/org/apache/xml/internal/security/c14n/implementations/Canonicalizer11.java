package com.sun.org.apache.xml.internal.security.c14n.implementations;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public abstract class Canonicalizer11 extends CanonicalizerBase {
  boolean firstCall = true;
  
  final SortedSet result = new TreeSet(COMPARE);
  
  static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
  
  static final String XML_LANG_URI = "http://www.w3.org/XML/1998/namespace";
  
  static Logger log = Logger.getLogger(Canonicalizer11.class.getName());
  
  XmlAttrStack xmlattrStack = new XmlAttrStack();
  
  public Canonicalizer11(boolean paramBoolean) {
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
          if (attr.getLocalName().equals("id")) {
            if (bool)
              sortedSet.add(attr); 
          } else {
            this.xmlattrStack.addXmlnsAttr(attr);
          } 
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
  
  private static String joinURI(String paramString1, String paramString2) throws URISyntaxException {
    String str11;
    String str12;
    String str13;
    String str14;
    String str1 = null;
    String str2 = null;
    String str3 = "";
    String str4 = null;
    String str5 = null;
    if (paramString1 != null) {
      if (paramString1.endsWith(".."))
        paramString1 = paramString1 + "/"; 
      URI uRI1 = new URI(paramString1);
      str1 = uRI1.getScheme();
      str2 = uRI1.getAuthority();
      str3 = uRI1.getPath();
      str4 = uRI1.getQuery();
      str5 = uRI1.getFragment();
    } 
    URI uRI = new URI(paramString2);
    String str6 = uRI.getScheme();
    String str7 = uRI.getAuthority();
    String str8 = uRI.getPath();
    String str9 = uRI.getQuery();
    String str10 = null;
    if (str6 != null && str6.equals(str1))
      str6 = null; 
    if (str6 != null) {
      str11 = str6;
      str12 = str7;
      str13 = removeDotSegments(str8);
      str14 = str9;
    } else {
      if (str7 != null) {
        str12 = str7;
        str13 = removeDotSegments(str8);
        str14 = str9;
      } else {
        if (str8.length() == 0) {
          str13 = str3;
          if (str9 != null) {
            str14 = str9;
          } else {
            str14 = str4;
          } 
        } else {
          if (str8.startsWith("/")) {
            str13 = removeDotSegments(str8);
          } else {
            if (str2 != null && str3.length() == 0) {
              str13 = "/" + str8;
            } else {
              int i = str3.lastIndexOf('/');
              if (i == -1) {
                str13 = str8;
              } else {
                str13 = str3.substring(0, i + 1) + str8;
              } 
            } 
            str13 = removeDotSegments(str13);
          } 
          str14 = str9;
        } 
        str12 = str2;
      } 
      str11 = str1;
    } 
    String str15 = str10;
    return (new URI(str11, str12, str13, str14, str15)).toString();
  }
  
  private static String removeDotSegments(String paramString) {
    log.log(Level.FINE, "STEP   OUTPUT BUFFER\t\tINPUT BUFFER");
    String str;
    for (str = paramString; str.indexOf("//") > -1; str = str.replaceAll("//", "/"));
    StringBuffer stringBuffer = new StringBuffer();
    if (str.charAt(0) == '/') {
      stringBuffer.append("/");
      str = str.substring(1);
    } 
    printStep("1 ", stringBuffer.toString(), str);
    while (str.length() != 0) {
      String str1;
      if (str.startsWith("./")) {
        str = str.substring(2);
        printStep("2A", stringBuffer.toString(), str);
        continue;
      } 
      if (str.startsWith("../")) {
        str = str.substring(3);
        if (!stringBuffer.toString().equals("/"))
          stringBuffer.append("../"); 
        printStep("2A", stringBuffer.toString(), str);
        continue;
      } 
      if (str.startsWith("/./")) {
        str = str.substring(2);
        printStep("2B", stringBuffer.toString(), str);
        continue;
      } 
      if (str.equals("/.")) {
        str = str.replaceFirst("/.", "/");
        printStep("2B", stringBuffer.toString(), str);
        continue;
      } 
      if (str.startsWith("/../")) {
        str = str.substring(3);
        if (stringBuffer.length() == 0) {
          stringBuffer.append("/");
        } else if (stringBuffer.toString().endsWith("../")) {
          stringBuffer.append("..");
        } else if (stringBuffer.toString().endsWith("..")) {
          stringBuffer.append("/..");
        } else {
          int k = stringBuffer.lastIndexOf("/");
          if (k == -1) {
            stringBuffer = new StringBuffer();
            if (str.charAt(0) == '/')
              str = str.substring(1); 
          } else {
            stringBuffer = stringBuffer.delete(k, stringBuffer.length());
          } 
        } 
        printStep("2C", stringBuffer.toString(), str);
        continue;
      } 
      if (str.equals("/..")) {
        str = str.replaceFirst("/..", "/");
        if (stringBuffer.length() == 0) {
          stringBuffer.append("/");
        } else if (stringBuffer.toString().endsWith("../")) {
          stringBuffer.append("..");
        } else if (stringBuffer.toString().endsWith("..")) {
          stringBuffer.append("/..");
        } else {
          int k = stringBuffer.lastIndexOf("/");
          if (k == -1) {
            stringBuffer = new StringBuffer();
            if (str.charAt(0) == '/')
              str = str.substring(1); 
          } else {
            stringBuffer = stringBuffer.delete(k, stringBuffer.length());
          } 
        } 
        printStep("2C", stringBuffer.toString(), str);
        continue;
      } 
      if (str.equals(".")) {
        str = "";
        printStep("2D", stringBuffer.toString(), str);
        continue;
      } 
      if (str.equals("..")) {
        if (!stringBuffer.toString().equals("/"))
          stringBuffer.append(".."); 
        str = "";
        printStep("2D", stringBuffer.toString(), str);
        continue;
      } 
      int i = -1;
      int j = str.indexOf('/');
      if (j == 0) {
        i = str.indexOf('/', 1);
      } else {
        i = j;
        j = 0;
      } 
      if (i == -1) {
        str1 = str.substring(j);
        str = "";
      } else {
        str1 = str.substring(j, i);
        str = str.substring(i);
      } 
      stringBuffer.append(str1);
      printStep("2E", stringBuffer.toString(), str);
    } 
    if (stringBuffer.toString().endsWith("..")) {
      stringBuffer.append("/");
      printStep("3 ", stringBuffer.toString(), str);
    } 
    return stringBuffer.toString();
  }
  
  private static void printStep(String paramString1, String paramString2, String paramString3) {
    log.log(Level.FINE, " " + paramString1 + ":   " + paramString2);
    if (paramString2.length() == 0) {
      log.log(Level.FINE, "\t\t\t\t" + paramString3);
    } else {
      log.log(Level.FINE, "\t\t\t" + paramString3);
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
      if (this.cur == null) {
        this.cur = new XmlsStackElement();
        this.cur.level = this.currentLevel;
        this.lastlevel = this.currentLevel;
        this.levels.add(this.cur);
      } 
      int i = this.levels.size() - 2;
      boolean bool1 = false;
      XmlsStackElement xmlsStackElement = null;
      if (i == -1) {
        bool1 = true;
      } else {
        xmlsStackElement = this.levels.get(i);
        if (xmlsStackElement.rendered && xmlsStackElement.level + 1 == this.currentLevel)
          bool1 = true; 
      } 
      if (bool1) {
        param1Collection.addAll(this.cur.nodes);
        this.cur.rendered = true;
        return;
      } 
      HashMap hashMap = new HashMap();
      ArrayList arrayList = new ArrayList();
      boolean bool2 = true;
      while (i >= 0) {
        xmlsStackElement = this.levels.get(i);
        if (xmlsStackElement.rendered)
          bool2 = false; 
        Iterator iterator = xmlsStackElement.nodes.iterator();
        while (iterator.hasNext() && bool2) {
          Attr attr = iterator.next();
          if (attr.getLocalName().equals("base")) {
            if (!xmlsStackElement.rendered)
              arrayList.add(attr); 
            continue;
          } 
          if (!hashMap.containsKey(attr.getName()))
            hashMap.put(attr.getName(), attr); 
        } 
        i--;
      } 
      if (!arrayList.isEmpty()) {
        null = this.cur.nodes.iterator();
        String str = null;
        Attr attr = null;
        while (null.hasNext()) {
          Attr attr1 = null.next();
          if (attr1.getLocalName().equals("base")) {
            str = attr1.getValue();
            attr = attr1;
            break;
          } 
        } 
        for (Attr attr1 : arrayList) {
          if (str == null) {
            str = attr1.getValue();
            attr = attr1;
            continue;
          } 
          try {
            str = Canonicalizer11.joinURI(attr1.getValue(), str);
          } catch (URISyntaxException uRISyntaxException) {
            uRISyntaxException.printStackTrace();
          } 
        } 
        if (str != null && str.length() != 0) {
          attr.setValue(str);
          param1Collection.add(attr);
        } 
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\c14n\implementations\Canonicalizer11.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */