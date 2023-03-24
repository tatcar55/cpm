package com.sun.org.apache.xml.internal.security.transforms.implementations;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityRuntimeException;
import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.Transform;
import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import com.sun.org.apache.xml.internal.security.utils.CachedXPathAPIHolder;
import com.sun.org.apache.xml.internal.security.utils.CachedXPathFuncHereAPI;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault;
import com.sun.org.apache.xpath.internal.objects.XObject;
import javax.xml.transform.TransformerException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TransformXPath extends TransformSpi {
  public static final String implementedTransformURI = "http://www.w3.org/TR/1999/REC-xpath-19991116";
  
  protected String engineGetURI() {
    return "http://www.w3.org/TR/1999/REC-xpath-19991116";
  }
  
  protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, Transform paramTransform) throws TransformationException {
    try {
      CachedXPathAPIHolder.setDoc(paramTransform.getElement().getOwnerDocument());
      Element element = XMLUtils.selectDsNode(paramTransform.getElement().getFirstChild(), "XPath", 0);
      if (element == null) {
        Object[] arrayOfObject = { "ds:XPath", "Transform" };
        throw new TransformationException("xml.WrongContent", arrayOfObject);
      } 
      Node node = element.getChildNodes().item(0);
      String str = CachedXPathFuncHereAPI.getStrFromNode(node);
      paramXMLSignatureInput.setNeedsToBeExpanded(needsCircunvent(str));
      if (node == null)
        throw new DOMException((short)3, "Text must be in ds:Xpath"); 
      paramXMLSignatureInput.addNodeFilter(new XPathNodeFilter(element, node, str));
      paramXMLSignatureInput.setNodeSet(true);
      return paramXMLSignatureInput;
    } catch (DOMException dOMException) {
      throw new TransformationException("empty", dOMException);
    } 
  }
  
  private boolean needsCircunvent(String paramString) {
    return (paramString.indexOf("namespace") != -1 || paramString.indexOf("name()") != -1);
  }
  
  static class XPathNodeFilter implements NodeFilter {
    PrefixResolverDefault prefixResolver;
    
    CachedXPathFuncHereAPI xPathFuncHereAPI = new CachedXPathFuncHereAPI(CachedXPathAPIHolder.getCachedXPathAPI());
    
    Node xpathnode;
    
    String str;
    
    XPathNodeFilter(Element param1Element, Node param1Node, String param1String) {
      this.xpathnode = param1Node;
      this.str = param1String;
      this.prefixResolver = new PrefixResolverDefault(param1Element);
    }
    
    public int isNodeInclude(Node param1Node) {
      try {
        XObject xObject = this.xPathFuncHereAPI.eval(param1Node, this.xpathnode, this.str, this.prefixResolver);
        return xObject.bool() ? 1 : 0;
      } catch (TransformerException transformerException) {
        Object[] arrayOfObject = { param1Node };
        throw new XMLSecurityRuntimeException("signature.Transform.node", arrayOfObject, transformerException);
      } catch (Exception exception) {
        Object[] arrayOfObject = { param1Node, new Short(param1Node.getNodeType()) };
        throw new XMLSecurityRuntimeException("signature.Transform.nodeAndType", arrayOfObject, exception);
      } 
    }
    
    public int isNodeIncludeDO(Node param1Node, int param1Int) {
      return isNodeInclude(param1Node);
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\implementations\TransformXPath.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */