package com.sun.org.apache.xml.internal.security.transforms;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.exceptions.AlgorithmAlreadyRegisteredException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class Transform extends SignatureElementProxy {
  static Logger log = Logger.getLogger(Transform.class.getName());
  
  static boolean _alreadyInitialized = false;
  
  static Map _transformHash = null;
  
  static HashMap classesHash = new HashMap();
  
  protected TransformSpi transformSpi = null;
  
  public Transform(Document paramDocument, String paramString, NodeList paramNodeList) throws InvalidTransformException {
    super(paramDocument);
    this._constructionElement.setAttributeNS((String)null, "Algorithm", paramString);
    this.transformSpi = getImplementingClass(paramString);
    if (this.transformSpi == null) {
      Object[] arrayOfObject = { paramString };
      throw new InvalidTransformException("signature.Transform.UnknownTransform", arrayOfObject);
    } 
    log.log(Level.FINE, "Create URI \"" + paramString + "\" class \"" + this.transformSpi.getClass() + "\"");
    log.log(Level.FINE, "The NodeList is " + paramNodeList);
    if (paramNodeList != null)
      for (byte b = 0; b < paramNodeList.getLength(); b++)
        this._constructionElement.appendChild(paramNodeList.item(b).cloneNode(true));  
  }
  
  public Transform(Element paramElement, String paramString) throws InvalidTransformException, TransformationException, XMLSecurityException {
    super(paramElement, paramString);
    String str = paramElement.getAttributeNS((String)null, "Algorithm");
    if (str == null || str.length() == 0) {
      Object[] arrayOfObject = { "Algorithm", "Transform" };
      throw new TransformationException("xml.WrongContent", arrayOfObject);
    } 
    this.transformSpi = getImplementingClass(str);
    if (this.transformSpi == null) {
      Object[] arrayOfObject = { str };
      throw new InvalidTransformException("signature.Transform.UnknownTransform", arrayOfObject);
    } 
  }
  
  public static final Transform getInstance(Document paramDocument, String paramString) throws InvalidTransformException {
    return getInstance(paramDocument, paramString, (NodeList)null);
  }
  
  public static final Transform getInstance(Document paramDocument, String paramString, Element paramElement) throws InvalidTransformException {
    HelperNodeList helperNodeList = new HelperNodeList();
    XMLUtils.addReturnToElement(paramDocument, helperNodeList);
    helperNodeList.appendChild(paramElement);
    XMLUtils.addReturnToElement(paramDocument, helperNodeList);
    return getInstance(paramDocument, paramString, helperNodeList);
  }
  
  public static final Transform getInstance(Document paramDocument, String paramString, NodeList paramNodeList) throws InvalidTransformException {
    return new Transform(paramDocument, paramString, paramNodeList);
  }
  
  public static void init() {
    if (!_alreadyInitialized) {
      _transformHash = new HashMap(10);
      _alreadyInitialized = true;
    } 
  }
  
  public static void register(String paramString1, String paramString2) throws AlgorithmAlreadyRegisteredException {
    TransformSpi transformSpi = null;
    try {
      transformSpi = getImplementingClass(paramString1);
    } catch (InvalidTransformException invalidTransformException) {
      Object[] arrayOfObject = { paramString1, transformSpi };
      throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", arrayOfObject);
    } 
    if (transformSpi != null) {
      Object[] arrayOfObject = { paramString1, transformSpi };
      throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", arrayOfObject);
    } 
    try {
      _transformHash.put(paramString1, Class.forName(paramString2));
    } catch (ClassNotFoundException classNotFoundException) {
      classNotFoundException.printStackTrace();
    } 
  }
  
  public final String getURI() {
    return this._constructionElement.getAttributeNS((String)null, "Algorithm");
  }
  
  public XMLSignatureInput performTransform(XMLSignatureInput paramXMLSignatureInput) throws IOException, CanonicalizationException, InvalidCanonicalizerException, TransformationException {
    XMLSignatureInput xMLSignatureInput = null;
    try {
      xMLSignatureInput = this.transformSpi.enginePerformTransform(paramXMLSignatureInput, this);
    } catch (ParserConfigurationException parserConfigurationException) {
      Object[] arrayOfObject = { getURI(), "ParserConfigurationException" };
      throw new CanonicalizationException("signature.Transform.ErrorDuringTransform", arrayOfObject, parserConfigurationException);
    } catch (SAXException sAXException) {
      Object[] arrayOfObject = { getURI(), "SAXException" };
      throw new CanonicalizationException("signature.Transform.ErrorDuringTransform", arrayOfObject, sAXException);
    } 
    return xMLSignatureInput;
  }
  
  public XMLSignatureInput performTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream) throws IOException, CanonicalizationException, InvalidCanonicalizerException, TransformationException {
    XMLSignatureInput xMLSignatureInput = null;
    try {
      xMLSignatureInput = this.transformSpi.enginePerformTransform(paramXMLSignatureInput, paramOutputStream, this);
    } catch (ParserConfigurationException parserConfigurationException) {
      Object[] arrayOfObject = { getURI(), "ParserConfigurationException" };
      throw new CanonicalizationException("signature.Transform.ErrorDuringTransform", arrayOfObject, parserConfigurationException);
    } catch (SAXException sAXException) {
      Object[] arrayOfObject = { getURI(), "SAXException" };
      throw new CanonicalizationException("signature.Transform.ErrorDuringTransform", arrayOfObject, sAXException);
    } 
    return xMLSignatureInput;
  }
  
  private static TransformSpi getImplementingClass(String paramString) throws InvalidTransformException {
    try {
      Object object = classesHash.get(paramString);
      if (object != null)
        return (TransformSpi)object; 
      Class clazz = (Class)_transformHash.get(paramString);
      if (clazz != null) {
        TransformSpi transformSpi = clazz.newInstance();
        classesHash.put(paramString, transformSpi);
        return transformSpi;
      } 
    } catch (InstantiationException instantiationException) {
      Object[] arrayOfObject = { paramString };
      throw new InvalidTransformException("signature.Transform.UnknownTransform", arrayOfObject, instantiationException);
    } catch (IllegalAccessException illegalAccessException) {
      Object[] arrayOfObject = { paramString };
      throw new InvalidTransformException("signature.Transform.UnknownTransform", arrayOfObject, illegalAccessException);
    } 
    return null;
  }
  
  public String getBaseLocalName() {
    return "Transform";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\Transform.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */