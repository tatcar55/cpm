package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.c14n.helper.AttrCompare;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Set;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;

public class XMLSignatureInputDebugger {
  private Set _xpathNodeSet;
  
  private Set _inclusiveNamespaces;
  
  private Document _doc = null;
  
  private Writer _writer = null;
  
  static final String HTMLPrefix = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n<html>\n<head>\n<title>Caninical XML node set</title>\n<style type=\"text/css\">\n<!-- \n.INCLUDED { \n   color: #000000; \n   background-color: \n   #FFFFFF; \n   font-weight: bold; } \n.EXCLUDED { \n   color: #666666; \n   background-color: \n   #999999; } \n.INCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #FFFFFF; \n   font-weight: bold; \n   font-style: italic; } \n.EXCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #999999; \n   font-style: italic; } \n--> \n</style> \n</head>\n<body bgcolor=\"#999999\">\n<h1>Explanation of the output</h1>\n<p>The following text contains the nodeset of the given Reference before it is canonicalized. There exist four different styles to indicate how a given node is treated.</p>\n<ul>\n<li class=\"INCLUDED\">A node which is in the node set is labeled using the INCLUDED style.</li>\n<li class=\"EXCLUDED\">A node which is <em>NOT</em> in the node set is labeled EXCLUDED style.</li>\n<li class=\"INCLUDEDINCLUSIVENAMESPACE\">A namespace which is in the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n<li class=\"EXCLUDEDINCLUSIVENAMESPACE\">A namespace which is in NOT the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n</ul>\n<h1>Output</h1>\n<pre>\n";
  
  static final String HTMLSuffix = "</pre></body></html>";
  
  static final String HTMLExcludePrefix = "<span class=\"EXCLUDED\">";
  
  static final String HTMLExcludeSuffix = "</span>";
  
  static final String HTMLIncludePrefix = "<span class=\"INCLUDED\">";
  
  static final String HTMLIncludeSuffix = "</span>";
  
  static final String HTMLIncludedInclusiveNamespacePrefix = "<span class=\"INCLUDEDINCLUSIVENAMESPACE\">";
  
  static final String HTMLIncludedInclusiveNamespaceSuffix = "</span>";
  
  static final String HTMLExcludedInclusiveNamespacePrefix = "<span class=\"EXCLUDEDINCLUSIVENAMESPACE\">";
  
  static final String HTMLExcludedInclusiveNamespaceSuffix = "</span>";
  
  private static final int NODE_BEFORE_DOCUMENT_ELEMENT = -1;
  
  private static final int NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT = 0;
  
  private static final int NODE_AFTER_DOCUMENT_ELEMENT = 1;
  
  static final AttrCompare ATTR_COMPARE = new AttrCompare();
  
  private XMLSignatureInputDebugger() {}
  
  public XMLSignatureInputDebugger(XMLSignatureInput paramXMLSignatureInput) {
    if (!paramXMLSignatureInput.isNodeSet()) {
      this._xpathNodeSet = null;
    } else {
      this._xpathNodeSet = paramXMLSignatureInput._inputNodeSet;
    } 
  }
  
  public XMLSignatureInputDebugger(XMLSignatureInput paramXMLSignatureInput, Set paramSet) {
    this(paramXMLSignatureInput);
    this._inclusiveNamespaces = paramSet;
  }
  
  public String getHTMLRepresentation() throws XMLSignatureException {
    if (this._xpathNodeSet == null || this._xpathNodeSet.size() == 0)
      return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n<html>\n<head>\n<title>Caninical XML node set</title>\n<style type=\"text/css\">\n<!-- \n.INCLUDED { \n   color: #000000; \n   background-color: \n   #FFFFFF; \n   font-weight: bold; } \n.EXCLUDED { \n   color: #666666; \n   background-color: \n   #999999; } \n.INCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #FFFFFF; \n   font-weight: bold; \n   font-style: italic; } \n.EXCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #999999; \n   font-style: italic; } \n--> \n</style> \n</head>\n<body bgcolor=\"#999999\">\n<h1>Explanation of the output</h1>\n<p>The following text contains the nodeset of the given Reference before it is canonicalized. There exist four different styles to indicate how a given node is treated.</p>\n<ul>\n<li class=\"INCLUDED\">A node which is in the node set is labeled using the INCLUDED style.</li>\n<li class=\"EXCLUDED\">A node which is <em>NOT</em> in the node set is labeled EXCLUDED style.</li>\n<li class=\"INCLUDEDINCLUSIVENAMESPACE\">A namespace which is in the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n<li class=\"EXCLUDEDINCLUSIVENAMESPACE\">A namespace which is in NOT the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n</ul>\n<h1>Output</h1>\n<pre>\n<blink>no node set, sorry</blink></pre></body></html>"; 
    Node node = this._xpathNodeSet.iterator().next();
    this._doc = XMLUtils.getOwnerDocument(node);
    try {
      this._writer = new StringWriter();
      canonicalizeXPathNodeSet(this._doc);
      this._writer.close();
      return this._writer.toString();
    } catch (IOException iOException) {
      throw new XMLSignatureException("empty", iOException);
    } finally {
      this._xpathNodeSet = null;
      this._doc = null;
      this._writer = null;
    } 
  }
  
  private void canonicalizeXPathNodeSet(Node paramNode) throws XMLSignatureException, IOException {
    Node node1;
    int i;
    short s = paramNode.getNodeType();
    switch (s) {
      default:
        return;
      case 2:
      case 6:
      case 11:
      case 12:
        throw new XMLSignatureException("empty");
      case 9:
        this._writer.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n<html>\n<head>\n<title>Caninical XML node set</title>\n<style type=\"text/css\">\n<!-- \n.INCLUDED { \n   color: #000000; \n   background-color: \n   #FFFFFF; \n   font-weight: bold; } \n.EXCLUDED { \n   color: #666666; \n   background-color: \n   #999999; } \n.INCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #FFFFFF; \n   font-weight: bold; \n   font-style: italic; } \n.EXCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #999999; \n   font-style: italic; } \n--> \n</style> \n</head>\n<body bgcolor=\"#999999\">\n<h1>Explanation of the output</h1>\n<p>The following text contains the nodeset of the given Reference before it is canonicalized. There exist four different styles to indicate how a given node is treated.</p>\n<ul>\n<li class=\"INCLUDED\">A node which is in the node set is labeled using the INCLUDED style.</li>\n<li class=\"EXCLUDED\">A node which is <em>NOT</em> in the node set is labeled EXCLUDED style.</li>\n<li class=\"INCLUDEDINCLUSIVENAMESPACE\">A namespace which is in the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n<li class=\"EXCLUDEDINCLUSIVENAMESPACE\">A namespace which is in NOT the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n</ul>\n<h1>Output</h1>\n<pre>\n");
        for (node1 = paramNode.getFirstChild(); node1 != null; node1 = node1.getNextSibling())
          canonicalizeXPathNodeSet(node1); 
        this._writer.write("</pre></body></html>");
      case 8:
        if (this._xpathNodeSet.contains(paramNode)) {
          this._writer.write("<span class=\"INCLUDED\">");
        } else {
          this._writer.write("<span class=\"EXCLUDED\">");
        } 
        i = getPositionRelativeToDocumentElement(paramNode);
        if (i == 1)
          this._writer.write("\n"); 
        outputCommentToWriter((Comment)paramNode);
        if (i == -1)
          this._writer.write("\n"); 
        if (this._xpathNodeSet.contains(paramNode)) {
          this._writer.write("</span>");
        } else {
          this._writer.write("</span>");
        } 
      case 7:
        if (this._xpathNodeSet.contains(paramNode)) {
          this._writer.write("<span class=\"INCLUDED\">");
        } else {
          this._writer.write("<span class=\"EXCLUDED\">");
        } 
        i = getPositionRelativeToDocumentElement(paramNode);
        if (i == 1)
          this._writer.write("\n"); 
        outputPItoWriter((ProcessingInstruction)paramNode);
        if (i == -1)
          this._writer.write("\n"); 
        if (this._xpathNodeSet.contains(paramNode)) {
          this._writer.write("</span>");
        } else {
          this._writer.write("</span>");
        } 
      case 3:
      case 4:
        if (this._xpathNodeSet.contains(paramNode)) {
          this._writer.write("<span class=\"INCLUDED\">");
        } else {
          this._writer.write("<span class=\"EXCLUDED\">");
        } 
        outputTextToWriter(paramNode.getNodeValue());
        for (node2 = paramNode.getNextSibling(); node2 != null && (node2.getNodeType() == 3 || node2.getNodeType() == 4); node2 = node2.getNextSibling())
          outputTextToWriter(node2.getNodeValue()); 
        if (this._xpathNodeSet.contains(paramNode)) {
          this._writer.write("</span>");
        } else {
          this._writer.write("</span>");
        } 
      case 1:
        break;
    } 
    Node node2 = paramNode;
    if (this._xpathNodeSet.contains(paramNode)) {
      this._writer.write("<span class=\"INCLUDED\">");
    } else {
      this._writer.write("<span class=\"EXCLUDED\">");
    } 
    this._writer.write("&lt;");
    this._writer.write(node2.getTagName());
    if (this._xpathNodeSet.contains(paramNode)) {
      this._writer.write("</span>");
    } else {
      this._writer.write("</span>");
    } 
    NamedNodeMap namedNodeMap = node2.getAttributes();
    int j = namedNodeMap.getLength();
    Object[] arrayOfObject1 = new Object[j];
    for (byte b1 = 0; b1 < j; b1++)
      arrayOfObject1[b1] = namedNodeMap.item(b1); 
    Arrays.sort(arrayOfObject1, ATTR_COMPARE);
    Object[] arrayOfObject2 = arrayOfObject1;
    for (byte b2 = 0; b2 < j; b2++) {
      Attr attr = (Attr)arrayOfObject2[b2];
      boolean bool1 = this._xpathNodeSet.contains(attr);
      boolean bool2 = this._inclusiveNamespaces.contains(attr.getName());
      if (bool1) {
        if (bool2) {
          this._writer.write("<span class=\"INCLUDEDINCLUSIVENAMESPACE\">");
        } else {
          this._writer.write("<span class=\"INCLUDED\">");
        } 
      } else if (bool2) {
        this._writer.write("<span class=\"EXCLUDEDINCLUSIVENAMESPACE\">");
      } else {
        this._writer.write("<span class=\"EXCLUDED\">");
      } 
      outputAttrToWriter(attr.getNodeName(), attr.getNodeValue());
      if (bool1) {
        if (bool2) {
          this._writer.write("</span>");
        } else {
          this._writer.write("</span>");
        } 
      } else if (bool2) {
        this._writer.write("</span>");
      } else {
        this._writer.write("</span>");
      } 
    } 
    if (this._xpathNodeSet.contains(paramNode)) {
      this._writer.write("<span class=\"INCLUDED\">");
    } else {
      this._writer.write("<span class=\"EXCLUDED\">");
    } 
    this._writer.write("&gt;");
    if (this._xpathNodeSet.contains(paramNode)) {
      this._writer.write("</span>");
    } else {
      this._writer.write("</span>");
    } 
    for (Node node3 = paramNode.getFirstChild(); node3 != null; node3 = node3.getNextSibling())
      canonicalizeXPathNodeSet(node3); 
    if (this._xpathNodeSet.contains(paramNode)) {
      this._writer.write("<span class=\"INCLUDED\">");
    } else {
      this._writer.write("<span class=\"EXCLUDED\">");
    } 
    this._writer.write("&lt;/");
    this._writer.write(node2.getTagName());
    this._writer.write("&gt;");
    if (this._xpathNodeSet.contains(paramNode))
      this._writer.write("</span>"); 
    this._writer.write("</span>");
  }
  
  private int getPositionRelativeToDocumentElement(Node paramNode) {
    if (paramNode == null)
      return 0; 
    Document document = paramNode.getOwnerDocument();
    if (paramNode.getParentNode() != document)
      return 0; 
    Element element = document.getDocumentElement();
    if (element == null)
      return 0; 
    if (element == paramNode)
      return 0; 
    for (Node node = paramNode; node != null; node = node.getNextSibling()) {
      if (node == element)
        return -1; 
    } 
    return 1;
  }
  
  private void outputAttrToWriter(String paramString1, String paramString2) throws IOException {
    this._writer.write(" ");
    this._writer.write(paramString1);
    this._writer.write("=\"");
    int i = paramString2.length();
    for (byte b = 0; b < i; b++) {
      char c = paramString2.charAt(b);
      switch (c) {
        case '&':
          this._writer.write("&amp;amp;");
          break;
        case '<':
          this._writer.write("&amp;lt;");
          break;
        case '"':
          this._writer.write("&amp;quot;");
          break;
        case '\t':
          this._writer.write("&amp;#x9;");
          break;
        case '\n':
          this._writer.write("&amp;#xA;");
          break;
        case '\r':
          this._writer.write("&amp;#xD;");
          break;
        default:
          this._writer.write(c);
          break;
      } 
    } 
    this._writer.write("\"");
  }
  
  private void outputPItoWriter(ProcessingInstruction paramProcessingInstruction) throws IOException {
    if (paramProcessingInstruction == null)
      return; 
    this._writer.write("&lt;?");
    String str1 = paramProcessingInstruction.getTarget();
    int i = str1.length();
    for (byte b = 0; b < i; b++) {
      char c = str1.charAt(b);
      switch (c) {
        case '\r':
          this._writer.write("&amp;#xD;");
          break;
        case ' ':
          this._writer.write("&middot;");
          break;
        case '\n':
          this._writer.write("&para;\n");
          break;
        default:
          this._writer.write(c);
          break;
      } 
    } 
    String str2 = paramProcessingInstruction.getData();
    i = str2.length();
    if (i > 0) {
      this._writer.write(" ");
      for (byte b1 = 0; b1 < i; b1++) {
        char c = str2.charAt(b1);
        switch (c) {
          case '\r':
            this._writer.write("&amp;#xD;");
            break;
          default:
            this._writer.write(c);
            break;
        } 
      } 
    } 
    this._writer.write("?&gt;");
  }
  
  private void outputCommentToWriter(Comment paramComment) throws IOException {
    if (paramComment == null)
      return; 
    this._writer.write("&lt;!--");
    String str = paramComment.getData();
    int i = str.length();
    for (byte b = 0; b < i; b++) {
      char c = str.charAt(b);
      switch (c) {
        case '\r':
          this._writer.write("&amp;#xD;");
          break;
        case ' ':
          this._writer.write("&middot;");
          break;
        case '\n':
          this._writer.write("&para;\n");
          break;
        default:
          this._writer.write(c);
          break;
      } 
    } 
    this._writer.write("--&gt;");
  }
  
  private void outputTextToWriter(String paramString) throws IOException {
    if (paramString == null)
      return; 
    int i = paramString.length();
    for (byte b = 0; b < i; b++) {
      char c = paramString.charAt(b);
      switch (c) {
        case '&':
          this._writer.write("&amp;amp;");
          break;
        case '<':
          this._writer.write("&amp;lt;");
          break;
        case '>':
          this._writer.write("&amp;gt;");
          break;
        case '\r':
          this._writer.write("&amp;#xD;");
          break;
        case ' ':
          this._writer.write("&middot;");
          break;
        case '\n':
          this._writer.write("&para;\n");
          break;
        default:
          this._writer.write(c);
          break;
      } 
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\signature\XMLSignatureInputDebugger.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */