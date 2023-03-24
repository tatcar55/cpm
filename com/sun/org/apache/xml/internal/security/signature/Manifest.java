package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.I18n;
import com.sun.org.apache.xml.internal.security.utils.IdResolver;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Manifest extends SignatureElementProxy {
  static Logger log = Logger.getLogger(Manifest.class.getName());
  
  List _references;
  
  Element[] _referencesEl;
  
  private boolean[] verificationResults = null;
  
  HashMap _resolverProperties = null;
  
  List _perManifestResolvers = null;
  
  public Manifest(Document paramDocument) {
    super(paramDocument);
    XMLUtils.addReturnToElement(this._constructionElement);
    this._references = new ArrayList();
  }
  
  public Manifest(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
    this._referencesEl = XMLUtils.selectDsNodes(this._constructionElement.getFirstChild(), "Reference");
    int i = this._referencesEl.length;
    if (i == 0) {
      Object[] arrayOfObject = { "Reference", "Manifest" };
      throw new DOMException((short)4, I18n.translate("xml.WrongContent", arrayOfObject));
    } 
    this._references = new ArrayList(i);
    for (byte b = 0; b < i; b++)
      this._references.add(null); 
  }
  
  public void addDocument(String paramString1, String paramString2, Transforms paramTransforms, String paramString3, String paramString4, String paramString5) throws XMLSignatureException {
    Reference reference = new Reference(this._doc, paramString1, paramString2, this, paramTransforms, paramString3);
    if (paramString4 != null)
      reference.setId(paramString4); 
    if (paramString5 != null)
      reference.setType(paramString5); 
    this._references.add(reference);
    this._constructionElement.appendChild(reference.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void generateDigestValues() throws XMLSignatureException, ReferenceNotInitializedException {
    for (byte b = 0; b < getLength(); b++) {
      Reference reference = this._references.get(b);
      reference.generateDigestValue();
    } 
  }
  
  public int getLength() {
    return this._references.size();
  }
  
  public Reference item(int paramInt) throws XMLSecurityException {
    if (this._references.get(paramInt) == null) {
      Reference reference = new Reference(this._referencesEl[paramInt], this._baseURI, this);
      this._references.set(paramInt, reference);
    } 
    return this._references.get(paramInt);
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
  
  public boolean verifyReferences() throws MissingResourceFailureException, XMLSecurityException {
    return verifyReferences(false);
  }
  
  public boolean verifyReferences(boolean paramBoolean) throws MissingResourceFailureException, XMLSecurityException {
    if (this._referencesEl == null)
      this._referencesEl = XMLUtils.selectDsNodes(this._constructionElement.getFirstChild(), "Reference"); 
    log.log(Level.FINE, "verify " + this._referencesEl.length + " References");
    log.log(Level.FINE, "I am " + (paramBoolean ? "" : "not") + " requested to follow nested Manifests");
    boolean bool = true;
    if (this._referencesEl.length == 0)
      throw new XMLSecurityException("empty"); 
    this.verificationResults = new boolean[this._referencesEl.length];
    for (byte b = 0; b < this._referencesEl.length; b++) {
      Reference reference = new Reference(this._referencesEl[b], this._baseURI, this);
      this._references.set(b, reference);
      try {
        boolean bool1 = reference.verify();
        setVerificationResult(b, bool1);
        if (!bool1)
          bool = false; 
        log.log(Level.FINE, "The Reference has Type " + reference.getType());
        if (bool && paramBoolean && reference.typeIsReferenceToManifest()) {
          log.log(Level.FINE, "We have to follow a nested Manifest");
          try {
            XMLSignatureInput xMLSignatureInput = reference.dereferenceURIandPerformTransforms((OutputStream)null);
            Set set = xMLSignatureInput.getNodeSet();
            Manifest manifest = null;
            for (Node node : set) {
              if (node.getNodeType() == 1 && ((Element)node).getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#") && ((Element)node).getLocalName().equals("Manifest"))
                try {
                  manifest = new Manifest((Element)node, xMLSignatureInput.getSourceURI());
                  break;
                } catch (XMLSecurityException xMLSecurityException) {} 
            } 
            if (manifest == null)
              throw new MissingResourceFailureException("empty", reference); 
            manifest._perManifestResolvers = this._perManifestResolvers;
            manifest._resolverProperties = this._resolverProperties;
            boolean bool2 = manifest.verifyReferences(paramBoolean);
            if (!bool2) {
              bool = false;
              log.log(Level.WARNING, "The nested Manifest was invalid (bad)");
            } else {
              log.log(Level.FINE, "The nested Manifest was valid (good)");
            } 
          } catch (IOException iOException) {
            throw new ReferenceNotInitializedException("empty", iOException);
          } catch (ParserConfigurationException parserConfigurationException) {
            throw new ReferenceNotInitializedException("empty", parserConfigurationException);
          } catch (SAXException sAXException) {
            throw new ReferenceNotInitializedException("empty", sAXException);
          } 
        } 
      } catch (ReferenceNotInitializedException referenceNotInitializedException) {
        Object[] arrayOfObject = { reference.getURI() };
        throw new MissingResourceFailureException("signature.Verification.Reference.NoInput", arrayOfObject, referenceNotInitializedException, reference);
      } 
    } 
    return bool;
  }
  
  private void setVerificationResult(int paramInt, boolean paramBoolean) {
    if (this.verificationResults == null)
      this.verificationResults = new boolean[getLength()]; 
    this.verificationResults[paramInt] = paramBoolean;
  }
  
  public boolean getVerificationResult(int paramInt) throws XMLSecurityException {
    if (paramInt < 0 || paramInt > getLength() - 1) {
      Object[] arrayOfObject = { Integer.toString(paramInt), Integer.toString(getLength()) };
      IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException(I18n.translate("signature.Verification.IndexOutOfBounds", arrayOfObject));
      throw new XMLSecurityException("generic.EmptyMessage", indexOutOfBoundsException);
    } 
    if (this.verificationResults == null)
      try {
        verifyReferences();
      } catch (Exception exception) {
        throw new XMLSecurityException("generic.EmptyMessage", exception);
      }  
    return this.verificationResults[paramInt];
  }
  
  public void addResourceResolver(ResourceResolver paramResourceResolver) {
    if (paramResourceResolver == null)
      return; 
    if (this._perManifestResolvers == null)
      this._perManifestResolvers = new ArrayList(); 
    this._perManifestResolvers.add(paramResourceResolver);
  }
  
  public void addResourceResolver(ResourceResolverSpi paramResourceResolverSpi) {
    if (paramResourceResolverSpi == null)
      return; 
    if (this._perManifestResolvers == null)
      this._perManifestResolvers = new ArrayList(); 
    this._perManifestResolvers.add(new ResourceResolver(paramResourceResolverSpi));
  }
  
  public void setResolverProperty(String paramString1, String paramString2) {
    if (this._resolverProperties == null)
      this._resolverProperties = new HashMap(10); 
    this._resolverProperties.put(paramString1, paramString2);
  }
  
  public String getResolverProperty(String paramString) {
    return (String)this._resolverProperties.get(paramString);
  }
  
  public byte[] getSignedContentItem(int paramInt) throws XMLSignatureException {
    try {
      return getReferencedContentAfterTransformsItem(paramInt).getBytes();
    } catch (IOException iOException) {
      throw new XMLSignatureException("empty", iOException);
    } catch (CanonicalizationException canonicalizationException) {
      throw new XMLSignatureException("empty", canonicalizationException);
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      throw new XMLSignatureException("empty", invalidCanonicalizerException);
    } catch (XMLSecurityException xMLSecurityException) {
      throw new XMLSignatureException("empty", xMLSecurityException);
    } 
  }
  
  public XMLSignatureInput getReferencedContentBeforeTransformsItem(int paramInt) throws XMLSecurityException {
    return item(paramInt).getContentsBeforeTransformation();
  }
  
  public XMLSignatureInput getReferencedContentAfterTransformsItem(int paramInt) throws XMLSecurityException {
    return item(paramInt).getContentsAfterTransformation();
  }
  
  public int getSignedContentLength() {
    return getLength();
  }
  
  public String getBaseLocalName() {
    return "Manifest";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\signature\Manifest.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */