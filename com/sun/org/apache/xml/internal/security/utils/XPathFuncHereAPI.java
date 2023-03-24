package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xml.internal.security.transforms.implementations.FuncHereContext;
import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault;
import com.sun.org.apache.xpath.internal.XPath;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.objects.XObject;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.traversal.NodeIterator;

public class XPathFuncHereAPI {
  public static Node selectSingleNode(Node paramNode1, Node paramNode2) throws TransformerException {
    return selectSingleNode(paramNode1, paramNode2, paramNode1);
  }
  
  public static Node selectSingleNode(Node paramNode1, Node paramNode2, Node paramNode3) throws TransformerException {
    NodeIterator nodeIterator = selectNodeIterator(paramNode1, paramNode2, paramNode3);
    return nodeIterator.nextNode();
  }
  
  public static NodeIterator selectNodeIterator(Node paramNode1, Node paramNode2) throws TransformerException {
    return selectNodeIterator(paramNode1, paramNode2, paramNode1);
  }
  
  public static NodeIterator selectNodeIterator(Node paramNode1, Node paramNode2, Node paramNode3) throws TransformerException {
    XObject xObject = eval(paramNode1, paramNode2, paramNode3);
    return xObject.nodeset();
  }
  
  public static NodeList selectNodeList(Node paramNode1, Node paramNode2) throws TransformerException {
    return selectNodeList(paramNode1, paramNode2, paramNode1);
  }
  
  public static NodeList selectNodeList(Node paramNode1, Node paramNode2, Node paramNode3) throws TransformerException {
    XObject xObject = eval(paramNode1, paramNode2, paramNode3);
    return xObject.nodelist();
  }
  
  public static XObject eval(Node paramNode1, Node paramNode2) throws TransformerException {
    return eval(paramNode1, paramNode2, paramNode1);
  }
  
  public static XObject eval(Node paramNode1, Node paramNode2, Node paramNode3) throws TransformerException {
    FuncHereContext funcHereContext = new FuncHereContext(paramNode2);
    PrefixResolverDefault prefixResolverDefault = new PrefixResolverDefault((paramNode3.getNodeType() == 9) ? ((Document)paramNode3).getDocumentElement() : paramNode3);
    String str = getStrFromNode(paramNode2);
    XPath xPath = new XPath(str, null, prefixResolverDefault, 0, null);
    int i = funcHereContext.getDTMHandleFromNode(paramNode1);
    return xPath.execute((XPathContext)funcHereContext, i, prefixResolverDefault);
  }
  
  public static XObject eval(Node paramNode1, Node paramNode2, PrefixResolver paramPrefixResolver) throws TransformerException {
    String str = getStrFromNode(paramNode2);
    XPath xPath = new XPath(str, null, paramPrefixResolver, 0, null);
    FuncHereContext funcHereContext = new FuncHereContext(paramNode2);
    int i = funcHereContext.getDTMHandleFromNode(paramNode1);
    return xPath.execute((XPathContext)funcHereContext, i, paramPrefixResolver);
  }
  
  private static String getStrFromNode(Node paramNode) {
    return (paramNode.getNodeType() == 3) ? ((Text)paramNode).getData() : ((paramNode.getNodeType() == 2) ? ((Attr)paramNode).getNodeValue() : ((paramNode.getNodeType() == 7) ? ((ProcessingInstruction)paramNode).getNodeValue() : ""));
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\XPathFuncHereAPI.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */