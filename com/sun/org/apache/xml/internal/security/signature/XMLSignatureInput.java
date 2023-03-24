package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_OmitComments;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315OmitComments;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityRuntimeException;
import com.sun.org.apache.xml.internal.security.utils.IgnoreAllErrorHandler;
import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLSignatureInput implements Cloneable {
  static Logger log = Logger.getLogger(XMLSignatureInput.class.getName());
  
  InputStream _inputOctetStreamProxy = null;
  
  Set _inputNodeSet = null;
  
  Node _subNode = null;
  
  Node excludeNode = null;
  
  boolean excludeComments = false;
  
  boolean isNodeSet = false;
  
  byte[] bytes = null;
  
  private String _MIMEType = null;
  
  private String _SourceURI = null;
  
  List nodeFilters = new ArrayList();
  
  boolean needsToBeExpanded = false;
  
  OutputStream outputStream = null;
  
  public boolean isNeedsToBeExpanded() {
    return this.needsToBeExpanded;
  }
  
  public void setNeedsToBeExpanded(boolean paramBoolean) {
    this.needsToBeExpanded = paramBoolean;
  }
  
  public XMLSignatureInput(byte[] paramArrayOfbyte) {
    this.bytes = paramArrayOfbyte;
  }
  
  public XMLSignatureInput(InputStream paramInputStream) {
    this._inputOctetStreamProxy = paramInputStream;
  }
  
  public XMLSignatureInput(String paramString) {
    this(paramString.getBytes());
  }
  
  public XMLSignatureInput(String paramString1, String paramString2) throws UnsupportedEncodingException {
    this(paramString1.getBytes(paramString2));
  }
  
  public XMLSignatureInput(Node paramNode) {
    this._subNode = paramNode;
  }
  
  public XMLSignatureInput(Set paramSet) {
    this._inputNodeSet = paramSet;
  }
  
  public Set getNodeSet() throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
    return getNodeSet(false);
  }
  
  public Set getNodeSet(boolean paramBoolean) throws ParserConfigurationException, IOException, SAXException, CanonicalizationException {
    if (this._inputNodeSet != null)
      return this._inputNodeSet; 
    if (this._inputOctetStreamProxy == null && this._subNode != null) {
      if (paramBoolean)
        XMLUtils.circumventBug2650(XMLUtils.getOwnerDocument(this._subNode)); 
      this._inputNodeSet = new HashSet();
      XMLUtils.getSet(this._subNode, this._inputNodeSet, this.excludeNode, this.excludeComments);
      return this._inputNodeSet;
    } 
    if (isOctetStream()) {
      convertToNodes();
      HashSet hashSet = new HashSet();
      XMLUtils.getSet(this._subNode, hashSet, null, false);
      return hashSet;
    } 
    throw new RuntimeException("getNodeSet() called but no input data present");
  }
  
  public InputStream getOctetStream() throws IOException {
    return getResetableInputStream();
  }
  
  public InputStream getOctetStreamReal() {
    return this._inputOctetStreamProxy;
  }
  
  public byte[] getBytes() throws IOException, CanonicalizationException {
    if (this.bytes != null)
      return this.bytes; 
    InputStream inputStream = getResetableInputStream();
    if (inputStream != null) {
      if (this.bytes == null) {
        inputStream.reset();
        this.bytes = JavaUtils.getBytesFromStream(inputStream);
      } 
      return this.bytes;
    } 
    Canonicalizer20010315OmitComments canonicalizer20010315OmitComments = new Canonicalizer20010315OmitComments();
    this.bytes = canonicalizer20010315OmitComments.engineCanonicalize(this);
    return this.bytes;
  }
  
  public boolean isNodeSet() {
    return ((this._inputOctetStreamProxy == null && this._inputNodeSet != null) || this.isNodeSet);
  }
  
  public boolean isElement() {
    return (this._inputOctetStreamProxy == null && this._subNode != null && this._inputNodeSet == null && !this.isNodeSet);
  }
  
  public boolean isOctetStream() {
    return ((this._inputOctetStreamProxy != null || this.bytes != null) && this._inputNodeSet == null && this._subNode == null);
  }
  
  public boolean isOutputStreamSet() {
    return (this.outputStream != null);
  }
  
  public boolean isByteArray() {
    return (this.bytes != null && this._inputNodeSet == null && this._subNode == null);
  }
  
  public boolean isInitialized() {
    return (isOctetStream() || isNodeSet());
  }
  
  public String getMIMEType() {
    return this._MIMEType;
  }
  
  public void setMIMEType(String paramString) {
    this._MIMEType = paramString;
  }
  
  public String getSourceURI() {
    return this._SourceURI;
  }
  
  public void setSourceURI(String paramString) {
    this._SourceURI = paramString;
  }
  
  public String toString() {
    if (isNodeSet())
      return "XMLSignatureInput/NodeSet/" + this._inputNodeSet.size() + " nodes/" + getSourceURI(); 
    if (isElement())
      return "XMLSignatureInput/Element/" + this._subNode + " exclude " + this.excludeNode + " comments:" + this.excludeComments + "/" + getSourceURI(); 
    try {
      return "XMLSignatureInput/OctetStream/" + (getBytes()).length + " octets/" + getSourceURI();
    } catch (IOException iOException) {
      return "XMLSignatureInput/OctetStream//" + getSourceURI();
    } catch (CanonicalizationException canonicalizationException) {
      return "XMLSignatureInput/OctetStream//" + getSourceURI();
    } 
  }
  
  public String getHTMLRepresentation() throws XMLSignatureException {
    XMLSignatureInputDebugger xMLSignatureInputDebugger = new XMLSignatureInputDebugger(this);
    return xMLSignatureInputDebugger.getHTMLRepresentation();
  }
  
  public String getHTMLRepresentation(Set paramSet) throws XMLSignatureException {
    XMLSignatureInputDebugger xMLSignatureInputDebugger = new XMLSignatureInputDebugger(this, paramSet);
    return xMLSignatureInputDebugger.getHTMLRepresentation();
  }
  
  public Node getExcludeNode() {
    return this.excludeNode;
  }
  
  public void setExcludeNode(Node paramNode) {
    this.excludeNode = paramNode;
  }
  
  public Node getSubNode() {
    return this._subNode;
  }
  
  public boolean isExcludeComments() {
    return this.excludeComments;
  }
  
  public void setExcludeComments(boolean paramBoolean) {
    this.excludeComments = paramBoolean;
  }
  
  public void updateOutputStream(OutputStream paramOutputStream) throws CanonicalizationException, IOException {
    updateOutputStream(paramOutputStream, false);
  }
  
  public void updateOutputStream(OutputStream paramOutputStream, boolean paramBoolean) throws CanonicalizationException, IOException {
    if (paramOutputStream == this.outputStream)
      return; 
    if (this.bytes != null) {
      paramOutputStream.write(this.bytes);
      return;
    } 
    if (this._inputOctetStreamProxy == null) {
      Canonicalizer20010315OmitComments canonicalizer20010315OmitComments;
      Canonicalizer11_OmitComments canonicalizer11_OmitComments = null;
      if (paramBoolean) {
        canonicalizer11_OmitComments = new Canonicalizer11_OmitComments();
      } else {
        canonicalizer20010315OmitComments = new Canonicalizer20010315OmitComments();
      } 
      canonicalizer20010315OmitComments.setWriter(paramOutputStream);
      canonicalizer20010315OmitComments.engineCanonicalize(this);
      return;
    } 
    InputStream inputStream = getResetableInputStream();
    if (this.bytes != null) {
      paramOutputStream.write(this.bytes, 0, this.bytes.length);
      return;
    } 
    inputStream.reset();
    byte[] arrayOfByte = new byte[1024];
    int i;
    while ((i = inputStream.read(arrayOfByte)) > 0)
      paramOutputStream.write(arrayOfByte, 0, i); 
  }
  
  public void setOutputStream(OutputStream paramOutputStream) {
    this.outputStream = paramOutputStream;
  }
  
  protected InputStream getResetableInputStream() throws IOException {
    if (this._inputOctetStreamProxy instanceof ByteArrayInputStream) {
      if (!this._inputOctetStreamProxy.markSupported())
        throw new RuntimeException("Accepted as Markable but not truly been" + this._inputOctetStreamProxy); 
      return this._inputOctetStreamProxy;
    } 
    if (this.bytes != null) {
      this._inputOctetStreamProxy = new ByteArrayInputStream(this.bytes);
      return this._inputOctetStreamProxy;
    } 
    if (this._inputOctetStreamProxy == null)
      return null; 
    if (this._inputOctetStreamProxy.markSupported())
      log.log(Level.INFO, "Mark Suported but not used as reset"); 
    this.bytes = JavaUtils.getBytesFromStream(this._inputOctetStreamProxy);
    this._inputOctetStreamProxy.close();
    this._inputOctetStreamProxy = new ByteArrayInputStream(this.bytes);
    return this._inputOctetStreamProxy;
  }
  
  public void addNodeFilter(NodeFilter paramNodeFilter) {
    if (isOctetStream())
      try {
        convertToNodes();
      } catch (Exception exception) {
        throw new XMLSecurityRuntimeException("signature.XMLSignatureInput.nodesetReference", exception);
      }  
    this.nodeFilters.add(paramNodeFilter);
  }
  
  public List getNodeFilters() {
    return this.nodeFilters;
  }
  
  public void setNodeSet(boolean paramBoolean) {
    this.isNodeSet = paramBoolean;
  }
  
  void convertToNodes() throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setValidating(false);
    documentBuilderFactory.setNamespaceAware(true);
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    try {
      documentBuilder.setErrorHandler(new IgnoreAllErrorHandler());
      Document document = documentBuilder.parse(getOctetStream());
      this._subNode = document.getDocumentElement();
    } catch (SAXException sAXException) {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      byteArrayOutputStream.write("<container>".getBytes());
      byteArrayOutputStream.write(getBytes());
      byteArrayOutputStream.write("</container>".getBytes());
      byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
      Document document = documentBuilder.parse(new ByteArrayInputStream(arrayOfByte));
      this._subNode = document.getDocumentElement().getFirstChild().getFirstChild();
    } 
    this._inputOctetStreamProxy = null;
    this.bytes = null;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\signature\XMLSignatureInput.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */