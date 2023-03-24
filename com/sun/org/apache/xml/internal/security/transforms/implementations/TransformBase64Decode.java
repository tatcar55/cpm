package com.sun.org.apache.xml.internal.security.transforms.implementations;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.Transform;
import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class TransformBase64Decode extends TransformSpi {
  public static final String implementedTransformURI = "http://www.w3.org/2000/09/xmldsig#base64";
  
  protected String engineGetURI() {
    return "http://www.w3.org/2000/09/xmldsig#base64";
  }
  
  protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, Transform paramTransform) throws IOException, CanonicalizationException, TransformationException {
    return enginePerformTransform(paramXMLSignatureInput, null, paramTransform);
  }
  
  protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws IOException, CanonicalizationException, TransformationException {
    try {
      if (paramXMLSignatureInput.isElement()) {
        Node node = paramXMLSignatureInput.getSubNode();
        if (paramXMLSignatureInput.getSubNode().getNodeType() == 3)
          node = node.getParentNode(); 
        StringBuffer stringBuffer = new StringBuffer();
        traverseElement((Element)node, stringBuffer);
        if (paramOutputStream == null) {
          byte[] arrayOfByte = Base64.decode(stringBuffer.toString());
          return new XMLSignatureInput(arrayOfByte);
        } 
        Base64.decode(stringBuffer.toString(), paramOutputStream);
        XMLSignatureInput xMLSignatureInput = new XMLSignatureInput((byte[])null);
        xMLSignatureInput.setOutputStream(paramOutputStream);
        return xMLSignatureInput;
      } 
      if (paramXMLSignatureInput.isOctetStream() || paramXMLSignatureInput.isNodeSet()) {
        if (paramOutputStream == null) {
          byte[] arrayOfByte1 = paramXMLSignatureInput.getBytes();
          byte[] arrayOfByte2 = Base64.decode(arrayOfByte1);
          return new XMLSignatureInput(arrayOfByte2);
        } 
        if (paramXMLSignatureInput.isByteArray() || paramXMLSignatureInput.isNodeSet()) {
          Base64.decode(paramXMLSignatureInput.getBytes(), paramOutputStream);
        } else {
          Base64.decode(new BufferedInputStream(paramXMLSignatureInput.getOctetStreamReal()), paramOutputStream);
        } 
        XMLSignatureInput xMLSignatureInput = new XMLSignatureInput((byte[])null);
        xMLSignatureInput.setOutputStream(paramOutputStream);
        return xMLSignatureInput;
      } 
      try {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(paramXMLSignatureInput.getOctetStream());
        Element element = document.getDocumentElement();
        StringBuffer stringBuffer = new StringBuffer();
        traverseElement(element, stringBuffer);
        byte[] arrayOfByte = Base64.decode(stringBuffer.toString());
        return new XMLSignatureInput(arrayOfByte);
      } catch (ParserConfigurationException parserConfigurationException) {
        throw new TransformationException("c14n.Canonicalizer.Exception", parserConfigurationException);
      } catch (SAXException sAXException) {
        throw new TransformationException("SAX exception", sAXException);
      } 
    } catch (Base64DecodingException base64DecodingException) {
      throw new TransformationException("Base64Decoding", base64DecodingException);
    } 
  }
  
  void traverseElement(Element paramElement, StringBuffer paramStringBuffer) {
    for (Node node = paramElement.getFirstChild(); node != null; node = node.getNextSibling()) {
      switch (node.getNodeType()) {
        case 1:
          traverseElement((Element)node, paramStringBuffer);
          break;
        case 3:
          paramStringBuffer.append(((Text)node).getData());
          break;
      } 
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\implementations\TransformBase64Decode.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */