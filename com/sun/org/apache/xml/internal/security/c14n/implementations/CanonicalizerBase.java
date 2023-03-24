package com.sun.org.apache.xml.internal.security.c14n.implementations;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.CanonicalizerSpi;
import com.sun.org.apache.xml.internal.security.c14n.helper.AttrCompare;
import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.UnsyncByteArrayOutputStream;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.SAXException;

public abstract class CanonicalizerBase extends CanonicalizerSpi {
  private static final byte[] _END_PI = new byte[] { 63, 62 };
  
  private static final byte[] _BEGIN_PI = new byte[] { 60, 63 };
  
  private static final byte[] _END_COMM = new byte[] { 45, 45, 62 };
  
  private static final byte[] _BEGIN_COMM = new byte[] { 60, 33, 45, 45 };
  
  private static final byte[] __XA_ = new byte[] { 38, 35, 120, 65, 59 };
  
  private static final byte[] __X9_ = new byte[] { 38, 35, 120, 57, 59 };
  
  private static final byte[] _QUOT_ = new byte[] { 38, 113, 117, 111, 116, 59 };
  
  private static final byte[] __XD_ = new byte[] { 38, 35, 120, 68, 59 };
  
  private static final byte[] _GT_ = new byte[] { 38, 103, 116, 59 };
  
  private static final byte[] _LT_ = new byte[] { 38, 108, 116, 59 };
  
  private static final byte[] _END_TAG = new byte[] { 60, 47 };
  
  private static final byte[] _AMP_ = new byte[] { 38, 97, 109, 112, 59 };
  
  static final AttrCompare COMPARE = new AttrCompare();
  
  static final String XML = "xml";
  
  static final String XMLNS = "xmlns";
  
  static final byte[] equalsStr = new byte[] { 61, 34 };
  
  static final int NODE_BEFORE_DOCUMENT_ELEMENT = -1;
  
  static final int NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT = 0;
  
  static final int NODE_AFTER_DOCUMENT_ELEMENT = 1;
  
  protected static final Attr nullNode;
  
  List nodeFilter;
  
  boolean _includeComments;
  
  Set _xpathNodeSet = null;
  
  Node _excludeNode = null;
  
  OutputStream _writer = new UnsyncByteArrayOutputStream();
  
  public CanonicalizerBase(boolean paramBoolean) {
    this._includeComments = paramBoolean;
  }
  
  public byte[] engineCanonicalizeSubTree(Node paramNode) throws CanonicalizationException {
    return engineCanonicalizeSubTree(paramNode, (Node)null);
  }
  
  public byte[] engineCanonicalizeXPathNodeSet(Set paramSet) throws CanonicalizationException {
    this._xpathNodeSet = paramSet;
    return engineCanonicalizeXPathNodeSetInternal(XMLUtils.getOwnerDocument(this._xpathNodeSet));
  }
  
  public byte[] engineCanonicalize(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException {
    try {
      if (paramXMLSignatureInput.isExcludeComments())
        this._includeComments = false; 
      if (paramXMLSignatureInput.isOctetStream())
        return engineCanonicalize(paramXMLSignatureInput.getBytes()); 
      if (paramXMLSignatureInput.isElement())
        return engineCanonicalizeSubTree(paramXMLSignatureInput.getSubNode(), paramXMLSignatureInput.getExcludeNode()); 
      if (paramXMLSignatureInput.isNodeSet()) {
        byte[] arrayOfByte;
        this.nodeFilter = paramXMLSignatureInput.getNodeFilters();
        circumventBugIfNeeded(paramXMLSignatureInput);
        if (paramXMLSignatureInput.getSubNode() != null) {
          arrayOfByte = engineCanonicalizeXPathNodeSetInternal(paramXMLSignatureInput.getSubNode());
        } else {
          arrayOfByte = engineCanonicalizeXPathNodeSet(paramXMLSignatureInput.getNodeSet());
        } 
        return arrayOfByte;
      } 
      return null;
    } catch (CanonicalizationException canonicalizationException) {
      throw new CanonicalizationException("empty", canonicalizationException);
    } catch (ParserConfigurationException parserConfigurationException) {
      throw new CanonicalizationException("empty", parserConfigurationException);
    } catch (IOException iOException) {
      throw new CanonicalizationException("empty", iOException);
    } catch (SAXException sAXException) {
      throw new CanonicalizationException("empty", sAXException);
    } 
  }
  
  public void setWriter(OutputStream paramOutputStream) {
    this._writer = paramOutputStream;
  }
  
  byte[] engineCanonicalizeSubTree(Node paramNode1, Node paramNode2) throws CanonicalizationException {
    this._excludeNode = paramNode2;
    try {
      NameSpaceSymbTable nameSpaceSymbTable = new NameSpaceSymbTable();
      byte b = -1;
      if (paramNode1 instanceof Element) {
        getParentNameSpaces((Element)paramNode1, nameSpaceSymbTable);
        b = 0;
      } 
      canonicalizeSubTree(paramNode1, nameSpaceSymbTable, paramNode1, b);
      this._writer.close();
      if (this._writer instanceof ByteArrayOutputStream) {
        byte[] arrayOfByte = ((ByteArrayOutputStream)this._writer).toByteArray();
        if (this.reset)
          ((ByteArrayOutputStream)this._writer).reset(); 
        return arrayOfByte;
      } 
      if (this._writer instanceof UnsyncByteArrayOutputStream) {
        byte[] arrayOfByte = ((UnsyncByteArrayOutputStream)this._writer).toByteArray();
        if (this.reset)
          ((UnsyncByteArrayOutputStream)this._writer).reset(); 
        return arrayOfByte;
      } 
      return null;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw new CanonicalizationException("empty", unsupportedEncodingException);
    } catch (IOException iOException) {
      throw new CanonicalizationException("empty", iOException);
    } 
  }
  
  final void canonicalizeSubTree(Node paramNode1, NameSpaceSymbTable paramNameSpaceSymbTable, Node paramNode2, int paramInt) throws CanonicalizationException, IOException {
    if (isVisibleInt(paramNode1) == -1)
      return; 
    Node node1 = null;
    Element element = null;
    OutputStream outputStream = this._writer;
    Node node2 = this._excludeNode;
    boolean bool = this._includeComments;
    HashMap hashMap = new HashMap();
    while (true) {
      Element element1;
      String str;
      Iterator iterator;
      switch (paramNode1.getNodeType()) {
        case 2:
        case 6:
        case 12:
          throw new CanonicalizationException("empty");
        case 9:
        case 11:
          paramNameSpaceSymbTable.outputNodePush();
          node1 = paramNode1.getFirstChild();
          break;
        case 8:
          if (bool)
            outputCommentToWriter((Comment)paramNode1, outputStream, paramInt); 
          break;
        case 7:
          outputPItoWriter((ProcessingInstruction)paramNode1, outputStream, paramInt);
          break;
        case 3:
        case 4:
          outputTextToWriter(paramNode1.getNodeValue(), outputStream);
          break;
        case 1:
          paramInt = 0;
          if (paramNode1 == node2)
            break; 
          element1 = (Element)paramNode1;
          paramNameSpaceSymbTable.outputNodePush();
          outputStream.write(60);
          str = element1.getTagName();
          UtfHelpper.writeByte(str, outputStream, hashMap);
          iterator = handleAttributesSubtree(element1, paramNameSpaceSymbTable);
          if (iterator != null)
            while (iterator.hasNext()) {
              Attr attr = iterator.next();
              outputAttrToWriter(attr.getNodeName(), attr.getNodeValue(), outputStream, hashMap);
            }  
          outputStream.write(62);
          node1 = paramNode1.getFirstChild();
          if (node1 == null) {
            outputStream.write(_END_TAG);
            UtfHelpper.writeStringToUtf8(str, outputStream);
            outputStream.write(62);
            paramNameSpaceSymbTable.outputNodePop();
            if (element != null)
              node1 = paramNode1.getNextSibling(); 
            break;
          } 
          element = element1;
          break;
      } 
      while (node1 == null && element != null) {
        outputStream.write(_END_TAG);
        UtfHelpper.writeByte(element.getTagName(), outputStream, hashMap);
        outputStream.write(62);
        paramNameSpaceSymbTable.outputNodePop();
        if (element == paramNode2)
          return; 
        node1 = element.getNextSibling();
        Node node = element.getParentNode();
        if (!(node instanceof Element)) {
          paramInt = 1;
          node = null;
        } 
      } 
      if (node1 == null)
        return; 
      paramNode1 = node1;
      node1 = paramNode1.getNextSibling();
    } 
  }
  
  private byte[] engineCanonicalizeXPathNodeSetInternal(Node paramNode) throws CanonicalizationException {
    try {
      canonicalizeXPathNodeSet(paramNode, paramNode);
      this._writer.close();
      if (this._writer instanceof ByteArrayOutputStream) {
        byte[] arrayOfByte = ((ByteArrayOutputStream)this._writer).toByteArray();
        if (this.reset)
          ((ByteArrayOutputStream)this._writer).reset(); 
        return arrayOfByte;
      } 
      if (this._writer instanceof UnsyncByteArrayOutputStream) {
        byte[] arrayOfByte = ((UnsyncByteArrayOutputStream)this._writer).toByteArray();
        if (this.reset)
          ((UnsyncByteArrayOutputStream)this._writer).reset(); 
        return arrayOfByte;
      } 
      return null;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw new CanonicalizationException("empty", unsupportedEncodingException);
    } catch (IOException iOException) {
      throw new CanonicalizationException("empty", iOException);
    } 
  }
  
  final void canonicalizeXPathNodeSet(Node paramNode1, Node paramNode2) throws CanonicalizationException, IOException {
    if (isVisibleInt(paramNode1) == -1)
      return; 
    boolean bool = false;
    NameSpaceSymbTable nameSpaceSymbTable = new NameSpaceSymbTable();
    if (paramNode1 instanceof Element)
      getParentNameSpaces((Element)paramNode1, nameSpaceSymbTable); 
    Node node = null;
    Element element = null;
    OutputStream outputStream = this._writer;
    byte b = -1;
    HashMap hashMap = new HashMap();
    while (true) {
      Element element1;
      String str;
      int i;
      Iterator iterator;
      switch (paramNode1.getNodeType()) {
        case 2:
        case 6:
        case 12:
          throw new CanonicalizationException("empty");
        case 9:
        case 11:
          nameSpaceSymbTable.outputNodePush();
          node = paramNode1.getFirstChild();
          break;
        case 8:
          if (this._includeComments && isVisibleDO(paramNode1, nameSpaceSymbTable.getLevel()) == 1)
            outputCommentToWriter((Comment)paramNode1, outputStream, b); 
          break;
        case 7:
          if (isVisible(paramNode1))
            outputPItoWriter((ProcessingInstruction)paramNode1, outputStream, b); 
          break;
        case 3:
        case 4:
          if (isVisible(paramNode1)) {
            outputTextToWriter(paramNode1.getNodeValue(), outputStream);
            for (Node node1 = paramNode1.getNextSibling(); node1 != null && (node1.getNodeType() == 3 || node1.getNodeType() == 4); node1 = node1.getNextSibling()) {
              outputTextToWriter(node1.getNodeValue(), outputStream);
              paramNode1 = node1;
              node = paramNode1.getNextSibling();
            } 
          } 
          break;
        case 1:
          b = 0;
          element1 = (Element)paramNode1;
          str = null;
          i = isVisibleDO(paramNode1, nameSpaceSymbTable.getLevel());
          if (i == -1) {
            node = paramNode1.getNextSibling();
            break;
          } 
          bool = (i == 1) ? true : false;
          if (bool) {
            nameSpaceSymbTable.outputNodePush();
            outputStream.write(60);
            str = element1.getTagName();
            UtfHelpper.writeByte(str, outputStream, hashMap);
          } else {
            nameSpaceSymbTable.push();
          } 
          iterator = handleAttributes(element1, nameSpaceSymbTable);
          if (iterator != null)
            while (iterator.hasNext()) {
              Attr attr = iterator.next();
              outputAttrToWriter(attr.getNodeName(), attr.getNodeValue(), outputStream, hashMap);
            }  
          if (bool)
            outputStream.write(62); 
          node = paramNode1.getFirstChild();
          if (node == null) {
            if (bool) {
              outputStream.write(_END_TAG);
              UtfHelpper.writeByte(str, outputStream, hashMap);
              outputStream.write(62);
              nameSpaceSymbTable.outputNodePop();
            } else {
              nameSpaceSymbTable.pop();
            } 
            if (element != null)
              node = paramNode1.getNextSibling(); 
            break;
          } 
          element = element1;
          break;
      } 
      while (node == null && element != null) {
        if (isVisible(element)) {
          outputStream.write(_END_TAG);
          UtfHelpper.writeByte(element.getTagName(), outputStream, hashMap);
          outputStream.write(62);
          nameSpaceSymbTable.outputNodePop();
        } else {
          nameSpaceSymbTable.pop();
        } 
        if (element == paramNode2)
          return; 
        node = element.getNextSibling();
        Node node1 = element.getParentNode();
        if (!(node1 instanceof Element)) {
          node1 = null;
          b = 1;
        } 
      } 
      if (node == null)
        return; 
      paramNode1 = node;
      node = paramNode1.getNextSibling();
    } 
  }
  
  int isVisibleDO(Node paramNode, int paramInt) {
    if (this.nodeFilter != null) {
      Iterator iterator = this.nodeFilter.iterator();
      while (iterator.hasNext()) {
        int i = ((NodeFilter)iterator.next()).isNodeIncludeDO(paramNode, paramInt);
        if (i != 1)
          return i; 
      } 
    } 
    return (this._xpathNodeSet != null && !this._xpathNodeSet.contains(paramNode)) ? 0 : 1;
  }
  
  int isVisibleInt(Node paramNode) {
    if (this.nodeFilter != null) {
      Iterator iterator = this.nodeFilter.iterator();
      while (iterator.hasNext()) {
        int i = ((NodeFilter)iterator.next()).isNodeInclude(paramNode);
        if (i != 1)
          return i; 
      } 
    } 
    return (this._xpathNodeSet != null && !this._xpathNodeSet.contains(paramNode)) ? 0 : 1;
  }
  
  boolean isVisible(Node paramNode) {
    if (this.nodeFilter != null) {
      Iterator iterator = this.nodeFilter.iterator();
      while (iterator.hasNext()) {
        if (((NodeFilter)iterator.next()).isNodeInclude(paramNode) != 1)
          return false; 
      } 
    } 
    return !(this._xpathNodeSet != null && !this._xpathNodeSet.contains(paramNode));
  }
  
  void handleParent(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) {
    if (!paramElement.hasAttributes())
      return; 
    NamedNodeMap namedNodeMap = paramElement.getAttributes();
    int i = namedNodeMap.getLength();
    for (byte b = 0; b < i; b++) {
      Attr attr = (Attr)namedNodeMap.item(b);
      if ("http://www.w3.org/2000/xmlns/" == attr.getNamespaceURI()) {
        String str1 = attr.getLocalName();
        String str2 = attr.getNodeValue();
        if (!"xml".equals(str1) || !"http://www.w3.org/XML/1998/namespace".equals(str2))
          paramNameSpaceSymbTable.addMapping(str1, str2, attr); 
      } 
    } 
  }
  
  final void getParentNameSpaces(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) {
    ArrayList arrayList = new ArrayList(10);
    Node node = paramElement.getParentNode();
    if (!(node instanceof Element))
      return; 
    for (Element element = (Element)node; element != null; element = (Element)node1) {
      arrayList.add(element);
      Node node1 = element.getParentNode();
      if (!(node1 instanceof Element))
        break; 
    } 
    ListIterator listIterator = arrayList.listIterator(arrayList.size());
    while (listIterator.hasPrevious()) {
      Element element1 = listIterator.previous();
      handleParent(element1, paramNameSpaceSymbTable);
    } 
    Attr attr;
    if ((attr = paramNameSpaceSymbTable.getMappingWithoutRendered("xmlns")) != null && "".equals(attr.getValue()))
      paramNameSpaceSymbTable.addMappingAndRender("xmlns", "", nullNode); 
  }
  
  abstract Iterator handleAttributes(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException;
  
  abstract Iterator handleAttributesSubtree(Element paramElement, NameSpaceSymbTable paramNameSpaceSymbTable) throws CanonicalizationException;
  
  abstract void circumventBugIfNeeded(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException;
  
  static final void outputAttrToWriter(String paramString1, String paramString2, OutputStream paramOutputStream, Map paramMap) throws IOException {
    paramOutputStream.write(32);
    UtfHelpper.writeByte(paramString1, paramOutputStream, paramMap);
    paramOutputStream.write(equalsStr);
    int i = paramString2.length();
    byte b = 0;
    while (b < i) {
      byte[] arrayOfByte;
      char c = paramString2.charAt(b++);
      switch (c) {
        case '&':
          arrayOfByte = _AMP_;
          break;
        case '<':
          arrayOfByte = _LT_;
          break;
        case '"':
          arrayOfByte = _QUOT_;
          break;
        case '\t':
          arrayOfByte = __X9_;
          break;
        case '\n':
          arrayOfByte = __XA_;
          break;
        case '\r':
          arrayOfByte = __XD_;
          break;
        default:
          if (c < '') {
            paramOutputStream.write(c);
            continue;
          } 
          UtfHelpper.writeCharToUtf8(c, paramOutputStream);
          continue;
      } 
      paramOutputStream.write(arrayOfByte);
    } 
    paramOutputStream.write(34);
  }
  
  static final void outputPItoWriter(ProcessingInstruction paramProcessingInstruction, OutputStream paramOutputStream, int paramInt) throws IOException {
    if (paramInt == 1)
      paramOutputStream.write(10); 
    paramOutputStream.write(_BEGIN_PI);
    String str1 = paramProcessingInstruction.getTarget();
    int i = str1.length();
    for (byte b = 0; b < i; b++) {
      char c = str1.charAt(b);
      if (c == '\r') {
        paramOutputStream.write(__XD_);
      } else if (c < '') {
        paramOutputStream.write(c);
      } else {
        UtfHelpper.writeCharToUtf8(c, paramOutputStream);
      } 
    } 
    String str2 = paramProcessingInstruction.getData();
    i = str2.length();
    if (i > 0) {
      paramOutputStream.write(32);
      for (byte b1 = 0; b1 < i; b1++) {
        char c = str2.charAt(b1);
        if (c == '\r') {
          paramOutputStream.write(__XD_);
        } else {
          UtfHelpper.writeCharToUtf8(c, paramOutputStream);
        } 
      } 
    } 
    paramOutputStream.write(_END_PI);
    if (paramInt == -1)
      paramOutputStream.write(10); 
  }
  
  static final void outputCommentToWriter(Comment paramComment, OutputStream paramOutputStream, int paramInt) throws IOException {
    if (paramInt == 1)
      paramOutputStream.write(10); 
    paramOutputStream.write(_BEGIN_COMM);
    String str = paramComment.getData();
    int i = str.length();
    for (byte b = 0; b < i; b++) {
      char c = str.charAt(b);
      if (c == '\r') {
        paramOutputStream.write(__XD_);
      } else if (c < '') {
        paramOutputStream.write(c);
      } else {
        UtfHelpper.writeCharToUtf8(c, paramOutputStream);
      } 
    } 
    paramOutputStream.write(_END_COMM);
    if (paramInt == -1)
      paramOutputStream.write(10); 
  }
  
  static final void outputTextToWriter(String paramString, OutputStream paramOutputStream) throws IOException {
    int i = paramString.length();
    for (byte b = 0;; b++) {
      if (b < i) {
        byte[] arrayOfByte;
        char c = paramString.charAt(b);
        switch (c) {
          case '&':
            arrayOfByte = _AMP_;
            break;
          case '<':
            arrayOfByte = _LT_;
            break;
          case '>':
            arrayOfByte = _GT_;
            break;
          case '\r':
            arrayOfByte = __XD_;
            break;
          default:
            if (c < '') {
              paramOutputStream.write(c);
            } else {
              UtfHelpper.writeCharToUtf8(c, paramOutputStream);
            } 
            b++;
            continue;
        } 
        paramOutputStream.write(arrayOfByte);
      } else {
        break;
      } 
    } 
  }
  
  static {
    try {
      nullNode = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns");
      nullNode.setValue("");
    } catch (Exception exception) {
      throw new RuntimeException("Unable to create nullNode" + exception);
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\c14n\implementations\CanonicalizerBase.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */