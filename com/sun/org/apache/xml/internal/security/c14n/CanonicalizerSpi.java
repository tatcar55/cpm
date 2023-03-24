package com.sun.org.apache.xml.internal.security.c14n;

import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class CanonicalizerSpi {
  protected boolean reset = false;
  
  public byte[] engineCanonicalize(byte[] paramArrayOfbyte) throws ParserConfigurationException, IOException, SAXException, CanonicalizationException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
    InputSource inputSource = new InputSource(byteArrayInputStream);
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setNamespaceAware(true);
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document document = documentBuilder.parse(inputSource);
    return engineCanonicalizeSubTree(document);
  }
  
  public byte[] engineCanonicalizeXPathNodeSet(NodeList paramNodeList) throws CanonicalizationException {
    return engineCanonicalizeXPathNodeSet(XMLUtils.convertNodelistToSet(paramNodeList));
  }
  
  public byte[] engineCanonicalizeXPathNodeSet(NodeList paramNodeList, String paramString) throws CanonicalizationException {
    return engineCanonicalizeXPathNodeSet(XMLUtils.convertNodelistToSet(paramNodeList), paramString);
  }
  
  public abstract String engineGetURI();
  
  public abstract boolean engineGetIncludeComments();
  
  public abstract byte[] engineCanonicalizeXPathNodeSet(Set paramSet) throws CanonicalizationException;
  
  public abstract byte[] engineCanonicalizeXPathNodeSet(Set paramSet, String paramString) throws CanonicalizationException;
  
  public abstract byte[] engineCanonicalizeSubTree(Node paramNode) throws CanonicalizationException;
  
  public abstract byte[] engineCanonicalizeSubTree(Node paramNode, String paramString) throws CanonicalizationException;
  
  public abstract void setWriter(OutputStream paramOutputStream);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\c14n\CanonicalizerSpi.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */