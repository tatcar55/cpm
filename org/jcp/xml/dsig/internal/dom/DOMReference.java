package org.jcp.xml.dsig.internal.dom;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.org.apache.xml.internal.security.utils.UnsyncBufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.NodeSetData;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMURIReference;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.TransformService;
import javax.xml.crypto.dsig.XMLSignContext;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLValidateContext;
import org.jcp.xml.dsig.internal.DigesterOutputStream;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMReference extends DOMStructure implements Reference, DOMURIReference {
  private static boolean useC14N11 = false;
  
  private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
  
  private final DigestMethod digestMethod;
  
  private final String id;
  
  private final List transforms;
  
  private List allTransforms;
  
  private final Data appliedTransformData;
  
  private Attr here;
  
  private final String uri;
  
  private final String type;
  
  private byte[] digestValue;
  
  private byte[] calcDigestValue;
  
  private Element refElem;
  
  private boolean digested = false;
  
  private boolean validated = false;
  
  private boolean validationStatus;
  
  private Data derefData;
  
  private InputStream dis;
  
  private MessageDigest md;
  
  private Provider provider;
  
  static final boolean $assertionsDisabled;
  
  public DOMReference(String paramString1, String paramString2, DigestMethod paramDigestMethod, List paramList, String paramString3, Provider paramProvider) {
    this(paramString1, paramString2, paramDigestMethod, null, null, paramList, paramString3, null, paramProvider);
  }
  
  public DOMReference(String paramString1, String paramString2, DigestMethod paramDigestMethod, List paramList1, Data paramData, List paramList2, String paramString3, Provider paramProvider) {
    this(paramString1, paramString2, paramDigestMethod, paramList1, paramData, paramList2, paramString3, null, paramProvider);
  }
  
  public DOMReference(String paramString1, String paramString2, DigestMethod paramDigestMethod, List paramList1, Data paramData, List paramList2, String paramString3, byte[] paramArrayOfbyte, Provider paramProvider) {
    if (paramDigestMethod == null)
      throw new NullPointerException("DigestMethod must be non-null"); 
    this.allTransforms = new ArrayList();
    if (paramList1 != null) {
      ArrayList arrayList = new ArrayList(paramList1);
      byte b = 0;
      int i = arrayList.size();
      while (b < i) {
        if (!(arrayList.get(b) instanceof javax.xml.crypto.dsig.Transform))
          throw new ClassCastException("appliedTransforms[" + b + "] is not a valid type"); 
        b++;
      } 
      this.allTransforms = arrayList;
    } 
    if (paramList2 == null) {
      this.transforms = Collections.EMPTY_LIST;
    } else {
      ArrayList arrayList = new ArrayList(paramList2);
      byte b = 0;
      int i = arrayList.size();
      while (b < i) {
        if (!(arrayList.get(b) instanceof javax.xml.crypto.dsig.Transform))
          throw new ClassCastException("transforms[" + b + "] is not a valid type"); 
        b++;
      } 
      this.transforms = arrayList;
      this.allTransforms.addAll(arrayList);
    } 
    this.digestMethod = paramDigestMethod;
    this.uri = paramString1;
    if (paramString1 != null && !paramString1.equals(""))
      try {
        new URI(paramString1);
      } catch (URISyntaxException uRISyntaxException) {
        throw new IllegalArgumentException(uRISyntaxException.getMessage());
      }  
    this.type = paramString2;
    this.id = paramString3;
    if (paramArrayOfbyte != null) {
      this.digestValue = (byte[])paramArrayOfbyte.clone();
      this.digested = true;
    } 
    this.appliedTransformData = paramData;
    this.provider = paramProvider;
  }
  
  public DOMReference(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
    Element element1 = DOMUtils.getFirstChildElement(paramElement);
    ArrayList arrayList = new ArrayList(5);
    if (element1.getLocalName().equals("Transforms")) {
      for (Element element = DOMUtils.getFirstChildElement(element1); element != null; element = DOMUtils.getNextSiblingElement(element))
        arrayList.add(new DOMTransform(element, paramXMLCryptoContext, paramProvider)); 
      element1 = DOMUtils.getNextSiblingElement(element1);
    } 
    Element element2 = element1;
    this.digestMethod = DOMDigestMethod.unmarshal(element2);
    try {
      Element element = DOMUtils.getNextSiblingElement(element2);
      this.digestValue = Base64.decode(element);
    } catch (Base64DecodingException base64DecodingException) {
      throw new MarshalException(base64DecodingException);
    } 
    this.uri = DOMUtils.getAttributeValue(paramElement, "URI");
    this.id = DOMUtils.getAttributeValue(paramElement, "Id");
    this.type = DOMUtils.getAttributeValue(paramElement, "Type");
    this.here = paramElement.getAttributeNodeNS((String)null, "URI");
    this.refElem = paramElement;
    this.transforms = arrayList;
    this.allTransforms = arrayList;
    this.appliedTransformData = null;
    this.provider = paramProvider;
  }
  
  public DigestMethod getDigestMethod() {
    return this.digestMethod;
  }
  
  public String getId() {
    return this.id;
  }
  
  public String getURI() {
    return this.uri;
  }
  
  public String getType() {
    return this.type;
  }
  
  public List getTransforms() {
    return Collections.unmodifiableList(this.allTransforms);
  }
  
  public byte[] getDigestValue() {
    return (this.digestValue == null) ? null : (byte[])this.digestValue.clone();
  }
  
  public byte[] getCalculatedDigestValue() {
    return (this.calcDigestValue == null) ? null : (byte[])this.calcDigestValue.clone();
  }
  
  public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    if (log.isLoggable(Level.FINE))
      log.log(Level.FINE, "Marshalling Reference"); 
    Document document = DOMUtils.getOwnerDocument(paramNode);
    this.refElem = DOMUtils.createElement(document, "Reference", "http://www.w3.org/2000/09/xmldsig#", paramString);
    DOMUtils.setAttributeID(this.refElem, "Id", this.id);
    DOMUtils.setAttribute(this.refElem, "URI", this.uri);
    DOMUtils.setAttribute(this.refElem, "Type", this.type);
    if (!this.allTransforms.isEmpty()) {
      Element element1 = DOMUtils.createElement(document, "Transforms", "http://www.w3.org/2000/09/xmldsig#", paramString);
      this.refElem.appendChild(element1);
      byte b = 0;
      int i = this.allTransforms.size();
      while (b < i) {
        DOMStructure dOMStructure = this.allTransforms.get(b);
        dOMStructure.marshal(element1, paramString, paramDOMCryptoContext);
        b++;
      } 
    } 
    ((DOMDigestMethod)this.digestMethod).marshal(this.refElem, paramString, paramDOMCryptoContext);
    if (log.isLoggable(Level.FINE))
      log.log(Level.FINE, "Adding digestValueElem"); 
    Element element = DOMUtils.createElement(document, "DigestValue", "http://www.w3.org/2000/09/xmldsig#", paramString);
    if (this.digestValue != null)
      element.appendChild(document.createTextNode(Base64.encode(this.digestValue))); 
    this.refElem.appendChild(element);
    paramNode.appendChild(this.refElem);
    this.here = this.refElem.getAttributeNodeNS((String)null, "URI");
  }
  
  public void digest(XMLSignContext paramXMLSignContext) throws XMLSignatureException {
    Data data = null;
    if (this.appliedTransformData == null) {
      data = dereference(paramXMLSignContext);
    } else {
      data = this.appliedTransformData;
    } 
    this.digestValue = transform(data, paramXMLSignContext);
    String str = Base64.encode(this.digestValue);
    if (log.isLoggable(Level.FINE))
      log.log(Level.FINE, "Reference object uri = " + this.uri); 
    Element element = DOMUtils.getLastChildElement(this.refElem);
    if (element == null)
      throw new XMLSignatureException("DigestValue element expected"); 
    DOMUtils.removeAllChildren(element);
    element.appendChild(this.refElem.getOwnerDocument().createTextNode(str));
    this.digested = true;
    if (log.isLoggable(Level.FINE))
      log.log(Level.FINE, "Reference digesting completed"); 
  }
  
  public boolean validate(XMLValidateContext paramXMLValidateContext) throws XMLSignatureException {
    if (paramXMLValidateContext == null)
      throw new NullPointerException("validateContext cannot be null"); 
    if (this.validated)
      return this.validationStatus; 
    Data data = dereference(paramXMLValidateContext);
    this.calcDigestValue = transform(data, paramXMLValidateContext);
    if (log.isLoggable(Level.FINE)) {
      log.log(Level.FINE, "Expected digest: " + Base64.encode(this.digestValue));
      log.log(Level.FINE, "Actual digest: " + Base64.encode(this.calcDigestValue));
    } 
    this.validationStatus = Arrays.equals(this.digestValue, this.calcDigestValue);
    this.validated = true;
    return this.validationStatus;
  }
  
  public Data getDereferencedData() {
    return this.derefData;
  }
  
  public InputStream getDigestInputStream() {
    return this.dis;
  }
  
  private Data dereference(XMLCryptoContext paramXMLCryptoContext) throws XMLSignatureException {
    Data data = null;
    URIDereferencer uRIDereferencer = paramXMLCryptoContext.getURIDereferencer();
    if (uRIDereferencer == null)
      uRIDereferencer = DOMURIDereferencer.INSTANCE; 
    try {
      data = uRIDereferencer.dereference(this, paramXMLCryptoContext);
      if (log.isLoggable(Level.FINE)) {
        log.log(Level.FINE, "URIDereferencer class name: " + uRIDereferencer.getClass().getName());
        log.log(Level.FINE, "Data class name: " + data.getClass().getName());
      } 
    } catch (URIReferenceException uRIReferenceException) {
      throw new XMLSignatureException(uRIReferenceException);
    } 
    return data;
  }
  
  private byte[] transform(Data paramData, XMLCryptoContext paramXMLCryptoContext) throws XMLSignatureException {
    DigesterOutputStream digesterOutputStream;
    if (this.md == null)
      try {
        this.md = MessageDigest.getInstance(((DOMDigestMethod)this.digestMethod).getMessageDigestAlgorithm());
      } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        throw new XMLSignatureException(noSuchAlgorithmException);
      }  
    this.md.reset();
    Boolean bool = (Boolean)paramXMLCryptoContext.getProperty("javax.xml.crypto.dsig.cacheReference");
    if (bool != null && bool.booleanValue() == true) {
      this.derefData = copyDerefData(paramData);
      digesterOutputStream = new DigesterOutputStream(this.md, true);
    } else {
      digesterOutputStream = new DigesterOutputStream(this.md);
    } 
    UnsyncBufferedOutputStream unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(digesterOutputStream);
    Data data = paramData;
    byte b = 0;
    int i = this.transforms.size();
    while (b < i) {
      DOMTransform dOMTransform = this.transforms.get(b);
      try {
        if (b < i - 1) {
          data = dOMTransform.transform(data, paramXMLCryptoContext);
        } else {
          data = dOMTransform.transform(data, paramXMLCryptoContext, unsyncBufferedOutputStream);
        } 
      } catch (TransformException transformException) {
        throw new XMLSignatureException(transformException);
      } 
      b++;
    } 
    try {
      if (data != null) {
        XMLSignatureInput xMLSignatureInput;
        boolean bool1 = useC14N11;
        String str = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
        if (paramXMLCryptoContext instanceof XMLSignContext)
          if (!bool1) {
            Boolean bool2 = (Boolean)paramXMLCryptoContext.getProperty("com.sun.org.apache.xml.internal.security.useC14N11");
            bool1 = (bool2 != null && bool2.booleanValue() == true);
            if (bool1)
              str = "http://www.w3.org/2006/12/xml-c14n11"; 
          } else {
            str = "http://www.w3.org/2006/12/xml-c14n11";
          }  
        if (data instanceof ApacheData) {
          xMLSignatureInput = ((ApacheData)data).getXMLSignatureInput();
        } else if (data instanceof OctetStreamData) {
          xMLSignatureInput = new XMLSignatureInput(((OctetStreamData)data).getOctetStream());
        } else if (data instanceof NodeSetData) {
          TransformService transformService = null;
          try {
            transformService = TransformService.getInstance(str, "DOM");
          } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            transformService = TransformService.getInstance(str, "DOM", this.provider);
          } 
          data = transformService.transform(data, paramXMLCryptoContext);
          xMLSignatureInput = new XMLSignatureInput(((OctetStreamData)data).getOctetStream());
        } else {
          throw new XMLSignatureException("unrecognized Data type");
        } 
        if (paramXMLCryptoContext instanceof XMLSignContext && bool1 && !xMLSignatureInput.isOctetStream() && !xMLSignatureInput.isOutputStreamSet()) {
          DOMTransform dOMTransform = new DOMTransform(TransformService.getInstance(str, "DOM"));
          Element element = null;
          String str1 = DOMUtils.getSignaturePrefix(paramXMLCryptoContext);
          if (this.allTransforms.isEmpty()) {
            element = DOMUtils.createElement(this.refElem.getOwnerDocument(), "Transforms", "http://www.w3.org/2000/09/xmldsig#", str1);
            this.refElem.insertBefore(element, DOMUtils.getFirstChildElement(this.refElem));
          } else {
            element = DOMUtils.getFirstChildElement(this.refElem);
          } 
          dOMTransform.marshal(element, str1, (DOMCryptoContext)paramXMLCryptoContext);
          this.allTransforms.add(dOMTransform);
          xMLSignatureInput.updateOutputStream(unsyncBufferedOutputStream, true);
        } else {
          xMLSignatureInput.updateOutputStream(unsyncBufferedOutputStream);
        } 
      } 
      unsyncBufferedOutputStream.flush();
      if (bool != null && bool.booleanValue() == true)
        this.dis = digesterOutputStream.getInputStream(); 
      return digesterOutputStream.getDigestValue();
    } catch (Exception exception) {
      throw new XMLSignatureException(exception);
    } 
  }
  
  public Node getHere() {
    return this.here;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof Reference))
      return false; 
    Reference reference = (Reference)paramObject;
    boolean bool1 = (this.id == null) ? ((reference.getId() == null) ? true : false) : this.id.equals(reference.getId());
    boolean bool2 = (this.uri == null) ? ((reference.getURI() == null) ? true : false) : this.uri.equals(reference.getURI());
    boolean bool3 = (this.type == null) ? ((reference.getType() == null) ? true : false) : this.type.equals(reference.getType());
    boolean bool = Arrays.equals(this.digestValue, reference.getDigestValue());
    return (this.digestMethod.equals(reference.getDigestMethod()) && bool1 && bool2 && bool3 && this.allTransforms.equals(reference.getTransforms()));
  }
  
  public int hashCode() {
    assert false : "hashCode not designed";
    return 47;
  }
  
  boolean isDigested() {
    return this.digested;
  }
  
  private static Data copyDerefData(Data paramData) {
    if (paramData instanceof ApacheData) {
      ApacheData apacheData = (ApacheData)paramData;
      XMLSignatureInput xMLSignatureInput = apacheData.getXMLSignatureInput();
      if (xMLSignatureInput.isNodeSet())
        try {
          final Set s = xMLSignatureInput.getNodeSet();
          return new NodeSetData() {
              private final Set val$s;
              
              public Iterator iterator() {
                return s.iterator();
              }
            };
        } catch (Exception exception) {
          log.log(Level.WARNING, "cannot cache dereferenced data: " + exception);
          return null;
        }  
      if (xMLSignatureInput.isElement())
        return new DOMSubTreeData(xMLSignatureInput.getSubNode(), xMLSignatureInput.isExcludeComments()); 
      if (xMLSignatureInput.isOctetStream() || xMLSignatureInput.isByteArray())
        try {
          return new OctetStreamData(xMLSignatureInput.getOctetStream(), xMLSignatureInput.getSourceURI(), xMLSignatureInput.getMIMEType());
        } catch (IOException iOException) {
          log.log(Level.WARNING, "cannot cache dereferenced data: " + iOException);
          return null;
        }  
    } 
    return paramData;
  }
  
  static {
    try {
      useC14N11 = Boolean.getBoolean("com.sun.org.apache.xml.internal.security.useC14N11");
    } catch (Exception exception) {}
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMReference.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */