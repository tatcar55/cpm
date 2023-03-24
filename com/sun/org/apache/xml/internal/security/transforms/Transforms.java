package com.sun.org.apache.xml.internal.security.transforms;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Transforms extends SignatureElementProxy {
  static Logger log = Logger.getLogger(Transforms.class.getName());
  
  public static final String TRANSFORM_C14N_OMIT_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
  
  public static final String TRANSFORM_C14N_WITH_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
  
  public static final String TRANSFORM_C14N11_OMIT_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11";
  
  public static final String TRANSFORM_C14N11_WITH_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11#WithComments";
  
  public static final String TRANSFORM_C14N_EXCL_OMIT_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#";
  
  public static final String TRANSFORM_C14N_EXCL_WITH_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
  
  public static final String TRANSFORM_XSLT = "http://www.w3.org/TR/1999/REC-xslt-19991116";
  
  public static final String TRANSFORM_BASE64_DECODE = "http://www.w3.org/2000/09/xmldsig#base64";
  
  public static final String TRANSFORM_XPATH = "http://www.w3.org/TR/1999/REC-xpath-19991116";
  
  public static final String TRANSFORM_ENVELOPED_SIGNATURE = "http://www.w3.org/2000/09/xmldsig#enveloped-signature";
  
  public static final String TRANSFORM_XPOINTER = "http://www.w3.org/TR/2001/WD-xptr-20010108";
  
  public static final String TRANSFORM_XPATH2FILTER04 = "http://www.w3.org/2002/04/xmldsig-filter2";
  
  public static final String TRANSFORM_XPATH2FILTER = "http://www.w3.org/2002/06/xmldsig-filter2";
  
  public static final String TRANSFORM_XPATHFILTERCHGP = "http://www.nue.et-inf.uni-siegen.de/~geuer-pollmann/#xpathFilter";
  
  Element[] transforms;
  
  protected Transforms() {}
  
  public Transforms(Document paramDocument) {
    super(paramDocument);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public Transforms(Element paramElement, String paramString) throws DOMException, XMLSignatureException, InvalidTransformException, TransformationException, XMLSecurityException {
    super(paramElement, paramString);
    int i = getLength();
    if (i == 0) {
      Object[] arrayOfObject = { "Transform", "Transforms" };
      throw new TransformationException("xml.WrongContent", arrayOfObject);
    } 
  }
  
  public void addTransform(String paramString) throws TransformationException {
    try {
      log.log(Level.FINE, "Transforms.addTransform(" + paramString + ")");
      Transform transform = Transform.getInstance(this._doc, paramString);
      addTransform(transform);
    } catch (InvalidTransformException invalidTransformException) {
      throw new TransformationException("empty", invalidTransformException);
    } 
  }
  
  public void addTransform(String paramString, Element paramElement) throws TransformationException {
    try {
      log.log(Level.FINE, "Transforms.addTransform(" + paramString + ")");
      Transform transform = Transform.getInstance(this._doc, paramString, paramElement);
      addTransform(transform);
    } catch (InvalidTransformException invalidTransformException) {
      throw new TransformationException("empty", invalidTransformException);
    } 
  }
  
  public void addTransform(String paramString, NodeList paramNodeList) throws TransformationException {
    try {
      Transform transform = Transform.getInstance(this._doc, paramString, paramNodeList);
      addTransform(transform);
    } catch (InvalidTransformException invalidTransformException) {
      throw new TransformationException("empty", invalidTransformException);
    } 
  }
  
  private void addTransform(Transform paramTransform) {
    log.log(Level.FINE, "Transforms.addTransform(" + paramTransform.getURI() + ")");
    Element element = paramTransform.getElement();
    this._constructionElement.appendChild(element);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public XMLSignatureInput performTransforms(XMLSignatureInput paramXMLSignatureInput) throws TransformationException {
    return performTransforms(paramXMLSignatureInput, (OutputStream)null);
  }
  
  public XMLSignatureInput performTransforms(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream) throws TransformationException {
    try {
      int i = getLength() - 1;
      for (byte b = 0; b < i; b++) {
        Transform transform = item(b);
        log.log(Level.FINE, "Perform the (" + b + ")th " + transform.getURI() + " transform");
        paramXMLSignatureInput = transform.performTransform(paramXMLSignatureInput);
      } 
      if (i >= 0) {
        Transform transform = item(i);
        paramXMLSignatureInput = transform.performTransform(paramXMLSignatureInput, paramOutputStream);
      } 
      return paramXMLSignatureInput;
    } catch (IOException iOException) {
      throw new TransformationException("empty", iOException);
    } catch (CanonicalizationException canonicalizationException) {
      throw new TransformationException("empty", canonicalizationException);
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      throw new TransformationException("empty", invalidCanonicalizerException);
    } 
  }
  
  public int getLength() {
    if (this.transforms == null)
      this.transforms = XMLUtils.selectDsNodes(this._constructionElement.getFirstChild(), "Transform"); 
    return this.transforms.length;
  }
  
  public Transform item(int paramInt) throws TransformationException {
    try {
      if (this.transforms == null)
        this.transforms = XMLUtils.selectDsNodes(this._constructionElement.getFirstChild(), "Transform"); 
      return new Transform(this.transforms[paramInt], this._baseURI);
    } catch (XMLSecurityException xMLSecurityException) {
      throw new TransformationException("empty", xMLSecurityException);
    } 
  }
  
  public String getBaseLocalName() {
    return "Transforms";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\Transforms.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */