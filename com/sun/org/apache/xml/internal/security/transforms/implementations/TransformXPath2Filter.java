package com.sun.org.apache.xml.internal.security.transforms.implementations;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.Transform;
import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import com.sun.org.apache.xml.internal.security.transforms.params.XPath2FilterContainer;
import com.sun.org.apache.xml.internal.security.utils.CachedXPathAPIHolder;
import com.sun.org.apache.xml.internal.security.utils.CachedXPathFuncHereAPI;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TransformXPath2Filter extends TransformSpi {
  public static final String implementedTransformURI = "http://www.w3.org/2002/06/xmldsig-filter2";
  
  protected String engineGetURI() {
    return "http://www.w3.org/2002/06/xmldsig-filter2";
  }
  
  protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, Transform paramTransform) throws TransformationException {
    CachedXPathAPIHolder.setDoc(paramTransform.getElement().getOwnerDocument());
    try {
      ArrayList arrayList1 = new ArrayList();
      ArrayList arrayList2 = new ArrayList();
      ArrayList arrayList3 = new ArrayList();
      CachedXPathFuncHereAPI cachedXPathFuncHereAPI = new CachedXPathFuncHereAPI(CachedXPathAPIHolder.getCachedXPathAPI());
      Element[] arrayOfElement = XMLUtils.selectNodes(paramTransform.getElement().getFirstChild(), "http://www.w3.org/2002/06/xmldsig-filter2", "XPath");
      int i = arrayOfElement.length;
      if (i == 0) {
        Object[] arrayOfObject = { "http://www.w3.org/2002/06/xmldsig-filter2", "XPath" };
        throw new TransformationException("xml.WrongContent", arrayOfObject);
      } 
      Document document = null;
      if (paramXMLSignatureInput.getSubNode() != null) {
        document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getSubNode());
      } else {
        document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getNodeSet());
      } 
      for (byte b = 0; b < i; b++) {
        Element element = XMLUtils.selectNode(paramTransform.getElement().getFirstChild(), "http://www.w3.org/2002/06/xmldsig-filter2", "XPath", b);
        XPath2FilterContainer xPath2FilterContainer = XPath2FilterContainer.newInstance(element, paramXMLSignatureInput.getSourceURI());
        NodeList nodeList = cachedXPathFuncHereAPI.selectNodeList(document, xPath2FilterContainer.getXPathFilterTextNode(), CachedXPathFuncHereAPI.getStrFromNode(xPath2FilterContainer.getXPathFilterTextNode()), xPath2FilterContainer.getElement());
        if (xPath2FilterContainer.isIntersect()) {
          arrayList3.add(nodeList);
        } else if (xPath2FilterContainer.isSubtract()) {
          arrayList2.add(nodeList);
        } else if (xPath2FilterContainer.isUnion()) {
          arrayList1.add(nodeList);
        } 
      } 
      paramXMLSignatureInput.addNodeFilter(new XPath2NodeFilter(convertNodeListToSet(arrayList1), convertNodeListToSet(arrayList2), convertNodeListToSet(arrayList3)));
      paramXMLSignatureInput.setNodeSet(true);
      return paramXMLSignatureInput;
    } catch (TransformerException transformerException) {
      throw new TransformationException("empty", transformerException);
    } catch (DOMException dOMException) {
      throw new TransformationException("empty", dOMException);
    } catch (CanonicalizationException canonicalizationException) {
      throw new TransformationException("empty", canonicalizationException);
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      throw new TransformationException("empty", invalidCanonicalizerException);
    } catch (XMLSecurityException xMLSecurityException) {
      throw new TransformationException("empty", xMLSecurityException);
    } catch (SAXException sAXException) {
      throw new TransformationException("empty", sAXException);
    } catch (IOException iOException) {
      throw new TransformationException("empty", iOException);
    } catch (ParserConfigurationException parserConfigurationException) {
      throw new TransformationException("empty", parserConfigurationException);
    } 
  }
  
  static Set convertNodeListToSet(List paramList) {
    HashSet hashSet = new HashSet();
    for (byte b = 0; b < paramList.size(); b++) {
      NodeList nodeList = paramList.get(b);
      int i = nodeList.getLength();
      for (byte b1 = 0; b1 < i; b1++) {
        Node node = nodeList.item(b1);
        hashSet.add(node);
      } 
    } 
    return hashSet;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\implementations\TransformXPath2Filter.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */