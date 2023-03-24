package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.transforms.InvalidTransformException;
import com.sun.org.apache.xml.internal.security.transforms.Transform;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.transforms.params.InclusiveNamespaces;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.org.apache.xml.internal.security.utils.DigesterOutputStream;
import com.sun.org.apache.xml.internal.security.utils.IdResolver;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.UnsyncBufferedOutputStream;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class Reference extends SignatureElementProxy {
  private static boolean useC14N11 = false;
  
  public static final boolean CacheSignedNodes = false;
  
  static Logger log = Logger.getLogger(Reference.class.getName());
  
  public static final String OBJECT_URI = "http://www.w3.org/2000/09/xmldsig#Object";
  
  public static final String MANIFEST_URI = "http://www.w3.org/2000/09/xmldsig#Manifest";
  
  Manifest _manifest = null;
  
  XMLSignatureInput _transformsOutput;
  
  private Transforms transforms;
  
  private Element digestMethodElem;
  
  private Element digestValueElement;
  
  protected Reference(Document paramDocument, String paramString1, String paramString2, Manifest paramManifest, Transforms paramTransforms, String paramString3) throws XMLSignatureException {
    super(paramDocument);
    XMLUtils.addReturnToElement(this._constructionElement);
    this._baseURI = paramString1;
    this._manifest = paramManifest;
    setURI(paramString2);
    if (paramTransforms != null) {
      this.transforms = paramTransforms;
      this._constructionElement.appendChild(paramTransforms.getElement());
      XMLUtils.addReturnToElement(this._constructionElement);
    } 
    MessageDigestAlgorithm messageDigestAlgorithm = MessageDigestAlgorithm.getInstance(this._doc, paramString3);
    this.digestMethodElem = messageDigestAlgorithm.getElement();
    this._constructionElement.appendChild(this.digestMethodElem);
    XMLUtils.addReturnToElement(this._constructionElement);
    this.digestValueElement = XMLUtils.createElementInSignatureSpace(this._doc, "DigestValue");
    this._constructionElement.appendChild(this.digestValueElement);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  protected Reference(Element paramElement, String paramString, Manifest paramManifest) throws XMLSecurityException {
    super(paramElement, paramString);
    this._baseURI = paramString;
    Element element = XMLUtils.getNextElement(paramElement.getFirstChild());
    if ("Transforms".equals(element.getLocalName()) && "http://www.w3.org/2000/09/xmldsig#".equals(element.getNamespaceURI())) {
      this.transforms = new Transforms(element, this._baseURI);
      element = XMLUtils.getNextElement(element.getNextSibling());
    } 
    this.digestMethodElem = element;
    this.digestValueElement = XMLUtils.getNextElement(this.digestMethodElem.getNextSibling());
    this._manifest = paramManifest;
  }
  
  public MessageDigestAlgorithm getMessageDigestAlgorithm() throws XMLSignatureException {
    if (this.digestMethodElem == null)
      return null; 
    String str = this.digestMethodElem.getAttributeNS((String)null, "Algorithm");
    return (str == null) ? null : MessageDigestAlgorithm.getInstance(this._doc, str);
  }
  
  public void setURI(String paramString) {
    if (paramString != null)
      this._constructionElement.setAttributeNS((String)null, "URI", paramString); 
  }
  
  public String getURI() {
    return this._constructionElement.getAttributeNS((String)null, "URI");
  }
  
  public void setId(String paramString) {
    if (paramString != null) {
      this._constructionElement.setAttributeNS((String)null, "Id", paramString);
      IdResolver.registerElementById(this._constructionElement, paramString);
    } 
  }
  
  public String getId() {
    return this._constructionElement.getAttributeNS((String)null, "Id");
  }
  
  public void setType(String paramString) {
    if (paramString != null)
      this._constructionElement.setAttributeNS((String)null, "Type", paramString); 
  }
  
  public String getType() {
    return this._constructionElement.getAttributeNS((String)null, "Type");
  }
  
  public boolean typeIsReferenceToObject() {
    return "http://www.w3.org/2000/09/xmldsig#Object".equals(getType());
  }
  
  public boolean typeIsReferenceToManifest() {
    return "http://www.w3.org/2000/09/xmldsig#Manifest".equals(getType());
  }
  
  private void setDigestValueElement(byte[] paramArrayOfbyte) {
    for (Node node = this.digestValueElement.getFirstChild(); node != null; node = node.getNextSibling())
      this.digestValueElement.removeChild(node); 
    String str = Base64.encode(paramArrayOfbyte);
    Text text = this._doc.createTextNode(str);
    this.digestValueElement.appendChild(text);
  }
  
  public void generateDigestValue() throws XMLSignatureException, ReferenceNotInitializedException {
    setDigestValueElement(calculateDigest(false));
  }
  
  public XMLSignatureInput getContentsBeforeTransformation() throws ReferenceNotInitializedException {
    try {
      String str;
      Attr attr = this._constructionElement.getAttributeNodeNS((String)null, "URI");
      if (attr == null) {
        str = null;
      } else {
        str = attr.getNodeValue();
      } 
      ResourceResolver resourceResolver = ResourceResolver.getInstance(attr, this._baseURI, this._manifest._perManifestResolvers);
      if (resourceResolver == null) {
        Object[] arrayOfObject = { str };
        throw new ReferenceNotInitializedException("signature.Verification.Reference.NoInput", arrayOfObject);
      } 
      resourceResolver.addProperties(this._manifest._resolverProperties);
      return resourceResolver.resolve(attr, this._baseURI);
    } catch (ResourceResolverException resourceResolverException) {
      throw new ReferenceNotInitializedException("empty", resourceResolverException);
    } catch (XMLSecurityException xMLSecurityException) {
      throw new ReferenceNotInitializedException("empty", xMLSecurityException);
    } 
  }
  
  public XMLSignatureInput getTransformsInput() throws ReferenceNotInitializedException {
    XMLSignatureInput xMLSignatureInput2;
    XMLSignatureInput xMLSignatureInput1 = getContentsBeforeTransformation();
    try {
      xMLSignatureInput2 = new XMLSignatureInput(xMLSignatureInput1.getBytes());
    } catch (CanonicalizationException canonicalizationException) {
      throw new ReferenceNotInitializedException("empty", canonicalizationException);
    } catch (IOException iOException) {
      throw new ReferenceNotInitializedException("empty", iOException);
    } 
    xMLSignatureInput2.setSourceURI(xMLSignatureInput1.getSourceURI());
    return xMLSignatureInput2;
  }
  
  private XMLSignatureInput getContentsAfterTransformation(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream) throws XMLSignatureException {
    try {
      Transforms transforms = getTransforms();
      XMLSignatureInput xMLSignatureInput = null;
      if (transforms != null) {
        xMLSignatureInput = transforms.performTransforms(paramXMLSignatureInput, paramOutputStream);
        this._transformsOutput = xMLSignatureInput;
      } else {
        xMLSignatureInput = paramXMLSignatureInput;
      } 
      return xMLSignatureInput;
    } catch (ResourceResolverException resourceResolverException) {
      throw new XMLSignatureException("empty", resourceResolverException);
    } catch (CanonicalizationException canonicalizationException) {
      throw new XMLSignatureException("empty", canonicalizationException);
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      throw new XMLSignatureException("empty", invalidCanonicalizerException);
    } catch (TransformationException transformationException) {
      throw new XMLSignatureException("empty", transformationException);
    } catch (XMLSecurityException xMLSecurityException) {
      throw new XMLSignatureException("empty", xMLSecurityException);
    } 
  }
  
  public XMLSignatureInput getContentsAfterTransformation() throws XMLSignatureException {
    XMLSignatureInput xMLSignatureInput = getContentsBeforeTransformation();
    return getContentsAfterTransformation(xMLSignatureInput, (OutputStream)null);
  }
  
  public XMLSignatureInput getNodesetBeforeFirstCanonicalization() throws XMLSignatureException {
    try {
      XMLSignatureInput xMLSignatureInput1 = getContentsBeforeTransformation();
      XMLSignatureInput xMLSignatureInput2 = xMLSignatureInput1;
      Transforms transforms = getTransforms();
      if (transforms != null) {
        for (byte b = 0; b < transforms.getLength(); b++) {
          Transform transform = transforms.item(b);
          String str = transform.getURI();
          if (str.equals("http://www.w3.org/2001/10/xml-exc-c14n#") || str.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments") || str.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315") || str.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments"))
            break; 
          xMLSignatureInput2 = transform.performTransform(xMLSignatureInput2, (OutputStream)null);
        } 
        xMLSignatureInput2.setSourceURI(xMLSignatureInput1.getSourceURI());
      } 
      return xMLSignatureInput2;
    } catch (IOException iOException) {
      throw new XMLSignatureException("empty", iOException);
    } catch (ResourceResolverException resourceResolverException) {
      throw new XMLSignatureException("empty", resourceResolverException);
    } catch (CanonicalizationException canonicalizationException) {
      throw new XMLSignatureException("empty", canonicalizationException);
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      throw new XMLSignatureException("empty", invalidCanonicalizerException);
    } catch (TransformationException transformationException) {
      throw new XMLSignatureException("empty", transformationException);
    } catch (XMLSecurityException xMLSecurityException) {
      throw new XMLSignatureException("empty", xMLSecurityException);
    } 
  }
  
  public String getHTMLRepresentation() throws XMLSignatureException {
    try {
      SortedSet sortedSet;
      XMLSignatureInput xMLSignatureInput = getNodesetBeforeFirstCanonicalization();
      HashSet hashSet = new HashSet();
      Transforms transforms = getTransforms();
      Transform transform = null;
      if (transforms != null)
        for (byte b = 0; b < transforms.getLength(); b++) {
          Transform transform1 = transforms.item(b);
          String str = transform1.getURI();
          if (str.equals("http://www.w3.org/2001/10/xml-exc-c14n#") || str.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments")) {
            transform = transform1;
            break;
          } 
        }  
      if (transform != null && transform.length("http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces") == 1) {
        InclusiveNamespaces inclusiveNamespaces = new InclusiveNamespaces(XMLUtils.selectNode(transform.getElement().getFirstChild(), "http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces", 0), getBaseURI());
        sortedSet = InclusiveNamespaces.prefixStr2Set(inclusiveNamespaces.getInclusiveNamespaces());
      } 
      return xMLSignatureInput.getHTMLRepresentation(sortedSet);
    } catch (TransformationException transformationException) {
      throw new XMLSignatureException("empty", transformationException);
    } catch (InvalidTransformException invalidTransformException) {
      throw new XMLSignatureException("empty", invalidTransformException);
    } catch (XMLSecurityException xMLSecurityException) {
      throw new XMLSignatureException("empty", xMLSecurityException);
    } 
  }
  
  public XMLSignatureInput getTransformsOutput() {
    return this._transformsOutput;
  }
  
  protected XMLSignatureInput dereferenceURIandPerformTransforms(OutputStream paramOutputStream) throws XMLSignatureException {
    try {
      XMLSignatureInput xMLSignatureInput1 = getContentsBeforeTransformation();
      XMLSignatureInput xMLSignatureInput2 = getContentsAfterTransformation(xMLSignatureInput1, paramOutputStream);
      this._transformsOutput = xMLSignatureInput2;
      return xMLSignatureInput2;
    } catch (XMLSecurityException xMLSecurityException) {
      throw new ReferenceNotInitializedException("empty", xMLSecurityException);
    } 
  }
  
  public Transforms getTransforms() throws XMLSignatureException, InvalidTransformException, TransformationException, XMLSecurityException {
    return this.transforms;
  }
  
  public byte[] getReferencedBytes() throws ReferenceNotInitializedException, XMLSignatureException {
    try {
      XMLSignatureInput xMLSignatureInput = dereferenceURIandPerformTransforms((OutputStream)null);
      return xMLSignatureInput.getBytes();
    } catch (IOException iOException) {
      throw new ReferenceNotInitializedException("empty", iOException);
    } catch (CanonicalizationException canonicalizationException) {
      throw new ReferenceNotInitializedException("empty", canonicalizationException);
    } 
  }
  
  private byte[] calculateDigest(boolean paramBoolean) throws ReferenceNotInitializedException, XMLSignatureException {
    try {
      MessageDigestAlgorithm messageDigestAlgorithm = getMessageDigestAlgorithm();
      messageDigestAlgorithm.reset();
      DigesterOutputStream digesterOutputStream = new DigesterOutputStream(messageDigestAlgorithm);
      UnsyncBufferedOutputStream unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(digesterOutputStream);
      XMLSignatureInput xMLSignatureInput = dereferenceURIandPerformTransforms(unsyncBufferedOutputStream);
      this;
      if (useC14N11 && !paramBoolean && !xMLSignatureInput.isOutputStreamSet() && !xMLSignatureInput.isOctetStream()) {
        if (this.transforms == null) {
          this.transforms = new Transforms(this._doc);
          this._constructionElement.insertBefore(this.transforms.getElement(), this.digestMethodElem);
        } 
        this.transforms.addTransform("http://www.w3.org/2006/12/xml-c14n11");
        xMLSignatureInput.updateOutputStream(unsyncBufferedOutputStream, true);
      } else {
        xMLSignatureInput.updateOutputStream(unsyncBufferedOutputStream);
      } 
      unsyncBufferedOutputStream.flush();
      return digesterOutputStream.getDigestValue();
    } catch (XMLSecurityException xMLSecurityException) {
      throw new ReferenceNotInitializedException("empty", xMLSecurityException);
    } catch (IOException iOException) {
      throw new ReferenceNotInitializedException("empty", iOException);
    } 
  }
  
  public byte[] getDigestValue() throws Base64DecodingException, XMLSecurityException {
    if (this.digestValueElement == null) {
      Object[] arrayOfObject = { "DigestValue", "http://www.w3.org/2000/09/xmldsig#" };
      throw new XMLSecurityException("signature.Verification.NoSignatureElement", arrayOfObject);
    } 
    return Base64.decode(this.digestValueElement);
  }
  
  public boolean verify() throws ReferenceNotInitializedException, XMLSecurityException {
    byte[] arrayOfByte1 = getDigestValue();
    byte[] arrayOfByte2 = calculateDigest(true);
    boolean bool = MessageDigestAlgorithm.isEqual(arrayOfByte1, arrayOfByte2);
    if (!bool) {
      log.log(Level.WARNING, "Verification failed for URI \"" + getURI() + "\"");
      log.log(Level.WARNING, "Expected Digest: " + Base64.encode(arrayOfByte1));
      log.log(Level.WARNING, "Actual Digest: " + Base64.encode(arrayOfByte2));
    } else {
      log.log(Level.INFO, "Verification successful for URI \"" + getURI() + "\"");
    } 
    return bool;
  }
  
  public String getBaseLocalName() {
    return "Reference";
  }
  
  static {
    try {
      useC14N11 = Boolean.getBoolean("com.sun.org.apache.xml.internal.security.useC14N11");
    } catch (Exception exception) {}
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\signature\Reference.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */