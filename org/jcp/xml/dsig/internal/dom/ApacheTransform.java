package org.jcp.xml.dsig.internal.dom;

import com.sun.org.apache.xml.internal.security.Init;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.Transform;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.NodeSetData;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.TransformService;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class ApacheTransform extends TransformService {
  private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
  
  private Transform apacheTransform;
  
  protected Document ownerDoc;
  
  protected Element transformElem;
  
  protected TransformParameterSpec params;
  
  public final AlgorithmParameterSpec getParameterSpec() {
    return this.params;
  }
  
  public void init(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws InvalidAlgorithmParameterException {
    if (paramXMLCryptoContext != null && !(paramXMLCryptoContext instanceof javax.xml.crypto.dom.DOMCryptoContext))
      throw new ClassCastException("context must be of type DOMCryptoContext"); 
    this.transformElem = (Element)((DOMStructure)paramXMLStructure).getNode();
    this.ownerDoc = DOMUtils.getOwnerDocument(this.transformElem);
  }
  
  public void marshalParams(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
    if (paramXMLCryptoContext != null && !(paramXMLCryptoContext instanceof javax.xml.crypto.dom.DOMCryptoContext))
      throw new ClassCastException("context must be of type DOMCryptoContext"); 
    this.transformElem = (Element)((DOMStructure)paramXMLStructure).getNode();
    this.ownerDoc = DOMUtils.getOwnerDocument(this.transformElem);
  }
  
  public Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext) throws TransformException {
    if (paramData == null)
      throw new NullPointerException("data must not be null"); 
    return transformIt(paramData, paramXMLCryptoContext, (OutputStream)null);
  }
  
  public Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext, OutputStream paramOutputStream) throws TransformException {
    if (paramData == null)
      throw new NullPointerException("data must not be null"); 
    if (paramOutputStream == null)
      throw new NullPointerException("output stream must not be null"); 
    return transformIt(paramData, paramXMLCryptoContext, paramOutputStream);
  }
  
  private Data transformIt(Data paramData, XMLCryptoContext paramXMLCryptoContext, OutputStream paramOutputStream) throws TransformException {
    XMLSignatureInput xMLSignatureInput;
    if (this.ownerDoc == null)
      throw new TransformException("transform must be marshalled"); 
    if (this.apacheTransform == null)
      try {
        this.apacheTransform = Transform.getInstance(this.ownerDoc, getAlgorithm(), this.transformElem.getChildNodes());
        this.apacheTransform.setElement(this.transformElem, paramXMLCryptoContext.getBaseURI());
        if (log.isLoggable(Level.FINE))
          log.log(Level.FINE, "Created transform for algorithm: " + getAlgorithm()); 
      } catch (Exception exception) {
        throw new TransformException("Couldn't find Transform for: " + getAlgorithm(), exception);
      }  
    if (paramData instanceof ApacheData) {
      if (log.isLoggable(Level.FINE))
        log.log(Level.FINE, "ApacheData = true"); 
      xMLSignatureInput = ((ApacheData)paramData).getXMLSignatureInput();
    } else if (paramData instanceof NodeSetData) {
      if (log.isLoggable(Level.FINE))
        log.log(Level.FINE, "isNodeSet() = true"); 
      if (paramData instanceof DOMSubTreeData) {
        if (log.isLoggable(Level.FINE))
          log.log(Level.FINE, "DOMSubTreeData = true"); 
        DOMSubTreeData dOMSubTreeData = (DOMSubTreeData)paramData;
        xMLSignatureInput = new XMLSignatureInput(dOMSubTreeData.getRoot());
        xMLSignatureInput.setExcludeComments(dOMSubTreeData.excludeComments());
      } else {
        Set set = Utils.toNodeSet(((NodeSetData)paramData).iterator());
        xMLSignatureInput = new XMLSignatureInput(set);
      } 
    } else {
      if (log.isLoggable(Level.FINE))
        log.log(Level.FINE, "isNodeSet() = false"); 
      try {
        xMLSignatureInput = new XMLSignatureInput(((OctetStreamData)paramData).getOctetStream());
      } catch (Exception exception) {
        throw new TransformException(exception);
      } 
    } 
    try {
      if (paramOutputStream != null) {
        xMLSignatureInput = this.apacheTransform.performTransform(xMLSignatureInput, paramOutputStream);
        if (!xMLSignatureInput.isNodeSet() && !xMLSignatureInput.isElement())
          return null; 
      } else {
        xMLSignatureInput = this.apacheTransform.performTransform(xMLSignatureInput);
      } 
      return (Data)(xMLSignatureInput.isOctetStream() ? new ApacheOctetStreamData(xMLSignatureInput) : new ApacheNodeSetData(xMLSignatureInput));
    } catch (Exception exception) {
      throw new TransformException(exception);
    } 
  }
  
  public final boolean isFeatureSupported(String paramString) {
    if (paramString == null)
      throw new NullPointerException(); 
    return false;
  }
  
  static {
    Init.init();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\ApacheTransform.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */