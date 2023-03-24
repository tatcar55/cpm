package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xml.internal.dtm.DTMManager;
import com.sun.org.apache.xml.internal.security.transforms.implementations.FuncHere;
import com.sun.org.apache.xml.internal.security.transforms.implementations.FuncHereContext;
import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault;
import com.sun.org.apache.xpath.internal.CachedXPathAPI;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.XPath;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.compiler.FunctionTable;
import com.sun.org.apache.xpath.internal.objects.XObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.traversal.NodeIterator;

public class CachedXPathFuncHereAPI {
  static Logger log = Logger.getLogger(CachedXPathFuncHereAPI.class.getName());
  
  FuncHereContext _funcHereContext = null;
  
  DTMManager _dtmManager = null;
  
  XPathContext _context = null;
  
  String xpathStr = null;
  
  XPath xpath = null;
  
  static FunctionTable _funcTable = null;
  
  public FuncHereContext getFuncHereContext() {
    return this._funcHereContext;
  }
  
  private CachedXPathFuncHereAPI() {}
  
  public CachedXPathFuncHereAPI(XPathContext paramXPathContext) {
    this._dtmManager = paramXPathContext.getDTMManager();
    this._context = paramXPathContext;
  }
  
  public CachedXPathFuncHereAPI(CachedXPathAPI paramCachedXPathAPI) {
    this._dtmManager = paramCachedXPathAPI.getXPathContext().getDTMManager();
    this._context = paramCachedXPathAPI.getXPathContext();
  }
  
  public Node selectSingleNode(Node paramNode1, Node paramNode2) throws TransformerException {
    return selectSingleNode(paramNode1, paramNode2, paramNode1);
  }
  
  public Node selectSingleNode(Node paramNode1, Node paramNode2, Node paramNode3) throws TransformerException {
    NodeIterator nodeIterator = selectNodeIterator(paramNode1, paramNode2, paramNode3);
    return nodeIterator.nextNode();
  }
  
  public NodeIterator selectNodeIterator(Node paramNode1, Node paramNode2) throws TransformerException {
    return selectNodeIterator(paramNode1, paramNode2, paramNode1);
  }
  
  public NodeIterator selectNodeIterator(Node paramNode1, Node paramNode2, Node paramNode3) throws TransformerException {
    XObject xObject = eval(paramNode1, paramNode2, getStrFromNode(paramNode2), paramNode3);
    return xObject.nodeset();
  }
  
  public NodeList selectNodeList(Node paramNode1, Node paramNode2) throws TransformerException {
    return selectNodeList(paramNode1, paramNode2, getStrFromNode(paramNode2), paramNode1);
  }
  
  public NodeList selectNodeList(Node paramNode1, Node paramNode2, String paramString, Node paramNode3) throws TransformerException {
    XObject xObject = eval(paramNode1, paramNode2, paramString, paramNode3);
    return xObject.nodelist();
  }
  
  public XObject eval(Node paramNode1, Node paramNode2) throws TransformerException {
    return eval(paramNode1, paramNode2, getStrFromNode(paramNode2), paramNode1);
  }
  
  public XObject eval(Node paramNode1, Node paramNode2, String paramString, Node paramNode3) throws TransformerException {
    if (this._funcHereContext == null)
      this._funcHereContext = new FuncHereContext(paramNode2, this._dtmManager); 
    PrefixResolverDefault prefixResolverDefault = new PrefixResolverDefault((paramNode3.getNodeType() == 9) ? ((Document)paramNode3).getDocumentElement() : paramNode3);
    if (paramString != this.xpathStr) {
      if (paramString.indexOf("here()") > 0) {
        this._context.reset();
        this._dtmManager = this._context.getDTMManager();
      } 
      this.xpath = createXPath(paramString, prefixResolverDefault);
      this.xpathStr = paramString;
    } 
    int i = this._funcHereContext.getDTMHandleFromNode(paramNode1);
    return this.xpath.execute((XPathContext)this._funcHereContext, i, prefixResolverDefault);
  }
  
  public XObject eval(Node paramNode1, Node paramNode2, String paramString, PrefixResolver paramPrefixResolver) throws TransformerException {
    if (paramString != this.xpathStr) {
      if (paramString.indexOf("here()") > 0) {
        this._context.reset();
        this._dtmManager = this._context.getDTMManager();
      } 
      try {
        this.xpath = createXPath(paramString, paramPrefixResolver);
      } catch (TransformerException transformerException) {
        Throwable throwable = transformerException.getCause();
        if (throwable instanceof ClassNotFoundException && throwable.getMessage().indexOf("FuncHere") > 0)
          throw new RuntimeException(I18n.translate("endorsed.jdk1.4.0") + transformerException); 
        throw transformerException;
      } 
      this.xpathStr = paramString;
    } 
    if (this._funcHereContext == null)
      this._funcHereContext = new FuncHereContext(paramNode2, this._dtmManager); 
    int i = this._funcHereContext.getDTMHandleFromNode(paramNode1);
    return this.xpath.execute((XPathContext)this._funcHereContext, i, paramPrefixResolver);
  }
  
  private XPath createXPath(String paramString, PrefixResolver paramPrefixResolver) throws TransformerException {
    XPath xPath = null;
    Class[] arrayOfClass = { String.class, SourceLocator.class, PrefixResolver.class, int.class, ErrorListener.class, FunctionTable.class };
    Object[] arrayOfObject = { paramString, null, paramPrefixResolver, new Integer(0), null, _funcTable };
    try {
      Constructor constructor = XPath.class.getConstructor(arrayOfClass);
      xPath = constructor.newInstance(arrayOfObject);
    } catch (Throwable throwable) {}
    if (xPath == null)
      xPath = new XPath(paramString, null, paramPrefixResolver, 0, null); 
    return xPath;
  }
  
  public static String getStrFromNode(Node paramNode) {
    if (paramNode.getNodeType() == 3) {
      StringBuffer stringBuffer = new StringBuffer();
      for (Node node = paramNode.getParentNode().getFirstChild(); node != null; node = node.getNextSibling()) {
        if (node.getNodeType() == 3)
          stringBuffer.append(((Text)node).getData()); 
      } 
      return stringBuffer.toString();
    } 
    return (paramNode.getNodeType() == 2) ? ((Attr)paramNode).getNodeValue() : ((paramNode.getNodeType() == 7) ? ((ProcessingInstruction)paramNode).getNodeValue() : null);
  }
  
  private static void fixupFunctionTable() {
    boolean bool = false;
    log.log(Level.INFO, "Registering Here function");
    try {
      Class[] arrayOfClass = { String.class, Expression.class };
      Method method = FunctionTable.class.getMethod("installFunction", arrayOfClass);
      if ((method.getModifiers() & 0x8) != 0) {
        Object[] arrayOfObject = { "here", new FuncHere() };
        method.invoke(null, arrayOfObject);
        bool = true;
      } 
    } catch (Throwable throwable) {
      log.log(Level.FINE, "Error installing function using the static installFunction method", throwable);
    } 
    if (!bool)
      try {
        _funcTable = new FunctionTable();
        Class[] arrayOfClass = { String.class, Class.class };
        Method method = FunctionTable.class.getMethod("installFunction", arrayOfClass);
        Object[] arrayOfObject = { "here", FuncHere.class };
        method.invoke(_funcTable, arrayOfObject);
        bool = true;
      } catch (Throwable throwable) {
        log.log(Level.FINE, "Error installing function using the static installFunction method", throwable);
      }  
    if (bool) {
      log.log(Level.FINE, "Registered class " + FuncHere.class.getName() + " for XPath function 'here()' function in internal table");
    } else {
      log.log(Level.FINE, "Unable to register class " + FuncHere.class.getName() + " for XPath function 'here()' function in internal table");
    } 
  }
  
  static {
    fixupFunctionTable();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\CachedXPathFuncHereAPI.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */