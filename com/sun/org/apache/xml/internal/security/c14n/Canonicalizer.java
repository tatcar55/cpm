package com.sun.org.apache.xml.internal.security.c14n;

import com.sun.org.apache.xml.internal.security.exceptions.AlgorithmAlreadyRegisteredException;
import com.sun.org.apache.xml.internal.security.utils.IgnoreAllErrorHandler;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Canonicalizer {
  public static final String ENCODING = "UTF8";
  
  public static final String XPATH_C14N_WITH_COMMENTS_SINGLE_NODE = "(.//. | .//@* | .//namespace::*)";
  
  public static final String ALGO_ID_C14N_OMIT_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
  
  public static final String ALGO_ID_C14N_WITH_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
  
  public static final String ALGO_ID_C14N_EXCL_OMIT_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#";
  
  public static final String ALGO_ID_C14N_EXCL_WITH_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
  
  public static final String ALGO_ID_C14N11_OMIT_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11";
  
  public static final String ALGO_ID_C14N11_WITH_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11#WithComments";
  
  static boolean _alreadyInitialized = false;
  
  static Map _canonicalizerHash = null;
  
  protected CanonicalizerSpi canonicalizerSpi = null;
  
  public static void init() {
    if (!_alreadyInitialized) {
      _canonicalizerHash = new HashMap(10);
      _alreadyInitialized = true;
    } 
  }
  
  private Canonicalizer(String paramString) throws InvalidCanonicalizerException {
    try {
      Class clazz = getImplementingClass(paramString);
      this.canonicalizerSpi = clazz.newInstance();
      this.canonicalizerSpi.reset = true;
    } catch (Exception exception) {
      Object[] arrayOfObject = { paramString };
      throw new InvalidCanonicalizerException("signature.Canonicalizer.UnknownCanonicalizer", arrayOfObject);
    } 
  }
  
  public static final Canonicalizer getInstance(String paramString) throws InvalidCanonicalizerException {
    return new Canonicalizer(paramString);
  }
  
  public static void register(String paramString1, String paramString2) throws AlgorithmAlreadyRegisteredException {
    Class clazz = getImplementingClass(paramString1);
    if (clazz != null) {
      Object[] arrayOfObject = { paramString1, clazz };
      throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", arrayOfObject);
    } 
    try {
      _canonicalizerHash.put(paramString1, Class.forName(paramString2));
    } catch (ClassNotFoundException classNotFoundException) {
      throw new RuntimeException("c14n class not found");
    } 
  }
  
  public final String getURI() {
    return this.canonicalizerSpi.engineGetURI();
  }
  
  public boolean getIncludeComments() {
    return this.canonicalizerSpi.engineGetIncludeComments();
  }
  
  public byte[] canonicalize(byte[] paramArrayOfbyte) throws ParserConfigurationException, IOException, SAXException, CanonicalizationException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
    InputSource inputSource = new InputSource(byteArrayInputStream);
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setNamespaceAware(true);
    documentBuilderFactory.setValidating(true);
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    documentBuilder.setErrorHandler(new IgnoreAllErrorHandler());
    Document document = documentBuilder.parse(inputSource);
    return canonicalizeSubtree(document);
  }
  
  public byte[] canonicalizeSubtree(Node paramNode) throws CanonicalizationException {
    return this.canonicalizerSpi.engineCanonicalizeSubTree(paramNode);
  }
  
  public byte[] canonicalizeSubtree(Node paramNode, String paramString) throws CanonicalizationException {
    return this.canonicalizerSpi.engineCanonicalizeSubTree(paramNode, paramString);
  }
  
  public byte[] canonicalizeXPathNodeSet(NodeList paramNodeList) throws CanonicalizationException {
    return this.canonicalizerSpi.engineCanonicalizeXPathNodeSet(paramNodeList);
  }
  
  public byte[] canonicalizeXPathNodeSet(NodeList paramNodeList, String paramString) throws CanonicalizationException {
    return this.canonicalizerSpi.engineCanonicalizeXPathNodeSet(paramNodeList, paramString);
  }
  
  public byte[] canonicalizeXPathNodeSet(Set paramSet) throws CanonicalizationException {
    return this.canonicalizerSpi.engineCanonicalizeXPathNodeSet(paramSet);
  }
  
  public byte[] canonicalizeXPathNodeSet(Set paramSet, String paramString) throws CanonicalizationException {
    return this.canonicalizerSpi.engineCanonicalizeXPathNodeSet(paramSet, paramString);
  }
  
  public void setWriter(OutputStream paramOutputStream) {
    this.canonicalizerSpi.setWriter(paramOutputStream);
  }
  
  public String getImplementingCanonicalizerClass() {
    return this.canonicalizerSpi.getClass().getName();
  }
  
  private static Class getImplementingClass(String paramString) {
    return (Class)_canonicalizerHash.get(paramString);
  }
  
  public void notReset() {
    this.canonicalizerSpi.reset = false;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\c14n\Canonicalizer.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */