package com.sun.org.apache.xml.internal.security.encryption;

import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.EncryptedKeyResolver;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import com.sun.org.apache.xml.internal.security.transforms.InvalidTransformException;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import com.sun.org.apache.xml.internal.utils.URI;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLCipher {
  private static Logger logger = Logger.getLogger(XMLCipher.class.getName());
  
  public static final String TRIPLEDES = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
  
  public static final String AES_128 = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
  
  public static final String AES_256 = "http://www.w3.org/2001/04/xmlenc#aes256-cbc";
  
  public static final String AES_192 = "http://www.w3.org/2001/04/xmlenc#aes192-cbc";
  
  public static final String RSA_v1dot5 = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
  
  public static final String RSA_OAEP = "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
  
  public static final String DIFFIE_HELLMAN = "http://www.w3.org/2001/04/xmlenc#dh";
  
  public static final String TRIPLEDES_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-tripledes";
  
  public static final String AES_128_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-aes128";
  
  public static final String AES_256_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-aes256";
  
  public static final String AES_192_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-aes192";
  
  public static final String SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";
  
  public static final String SHA256 = "http://www.w3.org/2001/04/xmlenc#sha256";
  
  public static final String SHA512 = "http://www.w3.org/2001/04/xmlenc#sha512";
  
  public static final String RIPEMD_160 = "http://www.w3.org/2001/04/xmlenc#ripemd160";
  
  public static final String XML_DSIG = "http://www.w3.org/2000/09/xmldsig#";
  
  public static final String N14C_XML = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
  
  public static final String N14C_XML_WITH_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
  
  public static final String EXCL_XML_N14C = "http://www.w3.org/2001/10/xml-exc-c14n#";
  
  public static final String EXCL_XML_N14C_WITH_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
  
  public static final String BASE64_ENCODING = "http://www.w3.org/2000/09/xmldsig#base64";
  
  public static final int ENCRYPT_MODE = 1;
  
  public static final int DECRYPT_MODE = 2;
  
  public static final int UNWRAP_MODE = 4;
  
  public static final int WRAP_MODE = 3;
  
  private static final String ENC_ALGORITHMS = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes128-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes256-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes192-cbc\nhttp://www.w3.org/2001/04/xmlenc#rsa-1_5\nhttp://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p\nhttp://www.w3.org/2001/04/xmlenc#kw-tripledes\nhttp://www.w3.org/2001/04/xmlenc#kw-aes128\nhttp://www.w3.org/2001/04/xmlenc#kw-aes256\nhttp://www.w3.org/2001/04/xmlenc#kw-aes192\n";
  
  private Cipher _contextCipher;
  
  private int _cipherMode = Integer.MIN_VALUE;
  
  private String _algorithm = null;
  
  private String _requestedJCEProvider = null;
  
  private Canonicalizer _canon;
  
  private Document _contextDocument;
  
  private Factory _factory;
  
  private Serializer _serializer;
  
  private Key _key;
  
  private Key _kek;
  
  private EncryptedKey _ek;
  
  private EncryptedData _ed;
  
  static Class class$com$sun$org$apache$xml$internal$security$encryption$XMLCipher$Factory$ReferenceListImpl$DataReference;
  
  static Class class$com$sun$org$apache$xml$internal$security$encryption$XMLCipher$Factory$ReferenceListImpl$KeyReference;
  
  private XMLCipher() {
    logger.log(Level.FINE, "Constructing XMLCipher...");
    this._factory = new Factory();
    this._serializer = new Serializer();
  }
  
  private static boolean isValidEncryptionAlgorithm(String paramString) {
    return (paramString.equals("http://www.w3.org/2001/04/xmlenc#tripledes-cbc") || paramString.equals("http://www.w3.org/2001/04/xmlenc#aes128-cbc") || paramString.equals("http://www.w3.org/2001/04/xmlenc#aes256-cbc") || paramString.equals("http://www.w3.org/2001/04/xmlenc#aes192-cbc") || paramString.equals("http://www.w3.org/2001/04/xmlenc#rsa-1_5") || paramString.equals("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p") || paramString.equals("http://www.w3.org/2001/04/xmlenc#kw-tripledes") || paramString.equals("http://www.w3.org/2001/04/xmlenc#kw-aes128") || paramString.equals("http://www.w3.org/2001/04/xmlenc#kw-aes256") || paramString.equals("http://www.w3.org/2001/04/xmlenc#kw-aes192"));
  }
  
  public static XMLCipher getInstance(String paramString) throws XMLEncryptionException {
    logger.log(Level.FINE, "Getting XMLCipher...");
    if (null == paramString)
      logger.log(Level.SEVERE, "Transformation unexpectedly null..."); 
    if (!isValidEncryptionAlgorithm(paramString))
      logger.log(Level.WARNING, "Algorithm non-standard, expected one of http://www.w3.org/2001/04/xmlenc#tripledes-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes128-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes256-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes192-cbc\nhttp://www.w3.org/2001/04/xmlenc#rsa-1_5\nhttp://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p\nhttp://www.w3.org/2001/04/xmlenc#kw-tripledes\nhttp://www.w3.org/2001/04/xmlenc#kw-aes128\nhttp://www.w3.org/2001/04/xmlenc#kw-aes256\nhttp://www.w3.org/2001/04/xmlenc#kw-aes192\n"); 
    XMLCipher xMLCipher = new XMLCipher();
    xMLCipher._algorithm = paramString;
    xMLCipher._key = null;
    xMLCipher._kek = null;
    try {
      xMLCipher._canon = Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      throw new XMLEncryptionException("empty", invalidCanonicalizerException);
    } 
    String str = JCEMapper.translateURItoJCEID(paramString);
    try {
      xMLCipher._contextCipher = Cipher.getInstance(str);
      logger.log(Level.FINE, "cihper.algoritm = " + xMLCipher._contextCipher.getAlgorithm());
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new XMLEncryptionException("empty", noSuchAlgorithmException);
    } catch (NoSuchPaddingException noSuchPaddingException) {
      throw new XMLEncryptionException("empty", noSuchPaddingException);
    } 
    return xMLCipher;
  }
  
  public static XMLCipher getInstance(String paramString1, String paramString2) throws XMLEncryptionException {
    XMLCipher xMLCipher = getInstance(paramString1);
    if (paramString2 != null)
      try {
        xMLCipher._canon = Canonicalizer.getInstance(paramString2);
      } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
        throw new XMLEncryptionException("empty", invalidCanonicalizerException);
      }  
    return xMLCipher;
  }
  
  public static XMLCipher getInstance(String paramString, Cipher paramCipher) throws XMLEncryptionException {
    logger.log(Level.FINE, "Getting XMLCipher...");
    if (null == paramString)
      logger.log(Level.SEVERE, "Transformation unexpectedly null..."); 
    if (!isValidEncryptionAlgorithm(paramString))
      logger.log(Level.WARNING, "Algorithm non-standard, expected one of http://www.w3.org/2001/04/xmlenc#tripledes-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes128-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes256-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes192-cbc\nhttp://www.w3.org/2001/04/xmlenc#rsa-1_5\nhttp://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p\nhttp://www.w3.org/2001/04/xmlenc#kw-tripledes\nhttp://www.w3.org/2001/04/xmlenc#kw-aes128\nhttp://www.w3.org/2001/04/xmlenc#kw-aes256\nhttp://www.w3.org/2001/04/xmlenc#kw-aes192\n"); 
    XMLCipher xMLCipher = new XMLCipher();
    xMLCipher._algorithm = paramString;
    xMLCipher._key = null;
    xMLCipher._kek = null;
    try {
      xMLCipher._canon = Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      throw new XMLEncryptionException("empty", invalidCanonicalizerException);
    } 
    String str = JCEMapper.translateURItoJCEID(paramString);
    try {
      xMLCipher._contextCipher = paramCipher;
      logger.log(Level.FINE, "cihper.algoritm = " + xMLCipher._contextCipher.getAlgorithm());
    } catch (Exception exception) {
      throw new XMLEncryptionException("empty", exception);
    } 
    return xMLCipher;
  }
  
  public static XMLCipher getProviderInstance(String paramString1, String paramString2) throws XMLEncryptionException {
    logger.log(Level.FINE, "Getting XMLCipher...");
    if (null == paramString1)
      logger.log(Level.SEVERE, "Transformation unexpectedly null..."); 
    if (null == paramString2)
      logger.log(Level.SEVERE, "Provider unexpectedly null.."); 
    if ("" == paramString2)
      logger.log(Level.SEVERE, "Provider's value unexpectedly not specified..."); 
    if (!isValidEncryptionAlgorithm(paramString1))
      logger.log(Level.WARNING, "Algorithm non-standard, expected one of http://www.w3.org/2001/04/xmlenc#tripledes-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes128-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes256-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes192-cbc\nhttp://www.w3.org/2001/04/xmlenc#rsa-1_5\nhttp://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p\nhttp://www.w3.org/2001/04/xmlenc#kw-tripledes\nhttp://www.w3.org/2001/04/xmlenc#kw-aes128\nhttp://www.w3.org/2001/04/xmlenc#kw-aes256\nhttp://www.w3.org/2001/04/xmlenc#kw-aes192\n"); 
    XMLCipher xMLCipher = new XMLCipher();
    xMLCipher._algorithm = paramString1;
    xMLCipher._requestedJCEProvider = paramString2;
    xMLCipher._key = null;
    xMLCipher._kek = null;
    try {
      xMLCipher._canon = Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      throw new XMLEncryptionException("empty", invalidCanonicalizerException);
    } 
    try {
      String str = JCEMapper.translateURItoJCEID(paramString1);
      xMLCipher._contextCipher = Cipher.getInstance(str, paramString2);
      logger.log(Level.FINE, "cipher._algorithm = " + xMLCipher._contextCipher.getAlgorithm());
      logger.log(Level.FINE, "provider.name = " + paramString2);
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new XMLEncryptionException("empty", noSuchAlgorithmException);
    } catch (NoSuchProviderException noSuchProviderException) {
      throw new XMLEncryptionException("empty", noSuchProviderException);
    } catch (NoSuchPaddingException noSuchPaddingException) {
      throw new XMLEncryptionException("empty", noSuchPaddingException);
    } 
    return xMLCipher;
  }
  
  public static XMLCipher getProviderInstance(String paramString1, String paramString2, String paramString3) throws XMLEncryptionException {
    XMLCipher xMLCipher = getProviderInstance(paramString1, paramString2);
    if (paramString3 != null)
      try {
        xMLCipher._canon = Canonicalizer.getInstance(paramString3);
      } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
        throw new XMLEncryptionException("empty", invalidCanonicalizerException);
      }  
    return xMLCipher;
  }
  
  public static XMLCipher getInstance() throws XMLEncryptionException {
    logger.log(Level.FINE, "Getting XMLCipher for no transformation...");
    XMLCipher xMLCipher = new XMLCipher();
    xMLCipher._algorithm = null;
    xMLCipher._requestedJCEProvider = null;
    xMLCipher._key = null;
    xMLCipher._kek = null;
    xMLCipher._contextCipher = null;
    try {
      xMLCipher._canon = Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      throw new XMLEncryptionException("empty", invalidCanonicalizerException);
    } 
    return xMLCipher;
  }
  
  public static XMLCipher getProviderInstance(String paramString) throws XMLEncryptionException {
    logger.log(Level.FINE, "Getting XMLCipher, provider but no transformation");
    if (null == paramString)
      logger.log(Level.SEVERE, "Provider unexpectedly null.."); 
    if ("" == paramString)
      logger.log(Level.SEVERE, "Provider's value unexpectedly not specified..."); 
    XMLCipher xMLCipher = new XMLCipher();
    xMLCipher._algorithm = null;
    xMLCipher._requestedJCEProvider = paramString;
    xMLCipher._key = null;
    xMLCipher._kek = null;
    xMLCipher._contextCipher = null;
    try {
      xMLCipher._canon = Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      throw new XMLEncryptionException("empty", invalidCanonicalizerException);
    } 
    return xMLCipher;
  }
  
  public void init(int paramInt, Key paramKey) throws XMLEncryptionException {
    logger.log(Level.FINE, "Initializing XMLCipher...");
    this._ek = null;
    this._ed = null;
    switch (paramInt) {
      case 1:
        logger.log(Level.FINE, "opmode = ENCRYPT_MODE");
        this._ed = createEncryptedData(1, "NO VALUE YET");
        break;
      case 2:
        logger.log(Level.FINE, "opmode = DECRYPT_MODE");
        break;
      case 3:
        logger.log(Level.FINE, "opmode = WRAP_MODE");
        this._ek = createEncryptedKey(1, "NO VALUE YET");
        break;
      case 4:
        logger.log(Level.FINE, "opmode = UNWRAP_MODE");
        break;
      default:
        logger.log(Level.SEVERE, "Mode unexpectedly invalid");
        throw new XMLEncryptionException("Invalid mode in init");
    } 
    this._cipherMode = paramInt;
    this._key = paramKey;
  }
  
  public EncryptedData getEncryptedData() {
    logger.log(Level.FINE, "Returning EncryptedData");
    return this._ed;
  }
  
  public EncryptedKey getEncryptedKey() {
    logger.log(Level.FINE, "Returning EncryptedKey");
    return this._ek;
  }
  
  public void setKEK(Key paramKey) {
    this._kek = paramKey;
  }
  
  public Element martial(EncryptedData paramEncryptedData) {
    return this._factory.toElement(paramEncryptedData);
  }
  
  public Element martial(EncryptedKey paramEncryptedKey) {
    return this._factory.toElement(paramEncryptedKey);
  }
  
  public Element martial(Document paramDocument, EncryptedData paramEncryptedData) {
    this._contextDocument = paramDocument;
    return this._factory.toElement(paramEncryptedData);
  }
  
  public Element martial(Document paramDocument, EncryptedKey paramEncryptedKey) {
    this._contextDocument = paramDocument;
    return this._factory.toElement(paramEncryptedKey);
  }
  
  private Document encryptElement(Element paramElement) throws Exception {
    logger.log(Level.FINE, "Encrypting element...");
    if (null == paramElement)
      logger.log(Level.SEVERE, "Element unexpectedly null..."); 
    if (this._cipherMode != 1)
      logger.log(Level.FINE, "XMLCipher unexpectedly not in ENCRYPT_MODE..."); 
    if (this._algorithm == null)
      throw new XMLEncryptionException("XMLCipher instance without transformation specified"); 
    encryptData(this._contextDocument, paramElement, false);
    Element element = this._factory.toElement(this._ed);
    Node node = paramElement.getParentNode();
    node.replaceChild(element, paramElement);
    return this._contextDocument;
  }
  
  private Document encryptElementContent(Element paramElement) throws Exception {
    logger.log(Level.FINE, "Encrypting element content...");
    if (null == paramElement)
      logger.log(Level.SEVERE, "Element unexpectedly null..."); 
    if (this._cipherMode != 1)
      logger.log(Level.FINE, "XMLCipher unexpectedly not in ENCRYPT_MODE..."); 
    if (this._algorithm == null)
      throw new XMLEncryptionException("XMLCipher instance without transformation specified"); 
    encryptData(this._contextDocument, paramElement, true);
    Element element = this._factory.toElement(this._ed);
    removeContent(paramElement);
    paramElement.appendChild(element);
    return this._contextDocument;
  }
  
  public Document doFinal(Document paramDocument1, Document paramDocument2) throws Exception {
    logger.log(Level.FINE, "Processing source document...");
    if (null == paramDocument1)
      logger.log(Level.SEVERE, "Context document unexpectedly null..."); 
    if (null == paramDocument2)
      logger.log(Level.SEVERE, "Source document unexpectedly null..."); 
    this._contextDocument = paramDocument1;
    Document document = null;
    switch (this._cipherMode) {
      case 2:
        document = decryptElement(paramDocument2.getDocumentElement());
      case 1:
        document = encryptElement(paramDocument2.getDocumentElement());
      case 4:
      case 3:
        return document;
    } 
    throw new XMLEncryptionException("empty", new IllegalStateException());
  }
  
  public Document doFinal(Document paramDocument, Element paramElement) throws Exception {
    logger.log(Level.FINE, "Processing source element...");
    if (null == paramDocument)
      logger.log(Level.SEVERE, "Context document unexpectedly null..."); 
    if (null == paramElement)
      logger.log(Level.SEVERE, "Source element unexpectedly null..."); 
    this._contextDocument = paramDocument;
    Document document = null;
    switch (this._cipherMode) {
      case 2:
        document = decryptElement(paramElement);
      case 1:
        document = encryptElement(paramElement);
      case 4:
      case 3:
        return document;
    } 
    throw new XMLEncryptionException("empty", new IllegalStateException());
  }
  
  public Document doFinal(Document paramDocument, Element paramElement, boolean paramBoolean) throws Exception {
    logger.log(Level.FINE, "Processing source element...");
    if (null == paramDocument)
      logger.log(Level.SEVERE, "Context document unexpectedly null..."); 
    if (null == paramElement)
      logger.log(Level.SEVERE, "Source element unexpectedly null..."); 
    this._contextDocument = paramDocument;
    Document document = null;
    switch (this._cipherMode) {
      case 2:
        if (paramBoolean) {
          document = decryptElementContent(paramElement);
        } else {
          document = decryptElement(paramElement);
        } 
      case 1:
        if (paramBoolean) {
          document = encryptElementContent(paramElement);
        } else {
          document = encryptElement(paramElement);
        } 
      case 4:
      case 3:
        return document;
    } 
    throw new XMLEncryptionException("empty", new IllegalStateException());
  }
  
  public EncryptedData encryptData(Document paramDocument, Element paramElement) throws Exception {
    return encryptData(paramDocument, paramElement, false);
  }
  
  public EncryptedData encryptData(Document paramDocument, String paramString, InputStream paramInputStream) throws Exception {
    logger.log(Level.FINE, "Encrypting element...");
    if (null == paramDocument)
      logger.log(Level.SEVERE, "Context document unexpectedly null..."); 
    if (null == paramInputStream)
      logger.log(Level.SEVERE, "Serialized data unexpectedly null..."); 
    if (this._cipherMode != 1)
      logger.log(Level.FINE, "XMLCipher unexpectedly not in ENCRYPT_MODE..."); 
    return encryptData(paramDocument, null, paramString, paramInputStream);
  }
  
  public EncryptedData encryptData(Document paramDocument, Element paramElement, boolean paramBoolean) throws Exception {
    logger.log(Level.FINE, "Encrypting element...");
    if (null == paramDocument)
      logger.log(Level.SEVERE, "Context document unexpectedly null..."); 
    if (null == paramElement)
      logger.log(Level.SEVERE, "Element unexpectedly null..."); 
    if (this._cipherMode != 1)
      logger.log(Level.FINE, "XMLCipher unexpectedly not in ENCRYPT_MODE..."); 
    return paramBoolean ? encryptData(paramDocument, paramElement, "http://www.w3.org/2001/04/xmlenc#Content", null) : encryptData(paramDocument, paramElement, "http://www.w3.org/2001/04/xmlenc#Element", null);
  }
  
  private EncryptedData encryptData(Document paramDocument, Element paramElement, String paramString, InputStream paramInputStream) throws Exception {
    Cipher cipher;
    this._contextDocument = paramDocument;
    if (this._algorithm == null)
      throw new XMLEncryptionException("XMLCipher instance without transformation specified"); 
    String str1 = null;
    if (paramInputStream == null) {
      if (paramString == "http://www.w3.org/2001/04/xmlenc#Content") {
        NodeList nodeList = paramElement.getChildNodes();
        if (null != nodeList) {
          str1 = this._serializer.serialize(nodeList);
        } else {
          Object[] arrayOfObject = { "Element has no content." };
          throw new XMLEncryptionException("empty", arrayOfObject);
        } 
      } else {
        str1 = this._serializer.serialize(paramElement);
      } 
      logger.log(Level.FINE, "Serialized octets:\n" + str1);
    } 
    byte[] arrayOfByte1 = null;
    if (this._contextCipher == null) {
      String str = JCEMapper.translateURItoJCEID(this._algorithm);
      logger.log(Level.FINE, "alg = " + str);
      try {
        if (this._requestedJCEProvider == null) {
          cipher = Cipher.getInstance(str);
        } else {
          cipher = Cipher.getInstance(str, this._requestedJCEProvider);
        } 
      } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        throw new XMLEncryptionException("empty", noSuchAlgorithmException);
      } catch (NoSuchProviderException noSuchProviderException) {
        throw new XMLEncryptionException("empty", noSuchProviderException);
      } catch (NoSuchPaddingException noSuchPaddingException) {
        throw new XMLEncryptionException("empty", noSuchPaddingException);
      } 
    } else {
      cipher = this._contextCipher;
    } 
    try {
      cipher.init(this._cipherMode, this._key);
    } catch (InvalidKeyException invalidKeyException) {
      throw new XMLEncryptionException("empty", invalidKeyException);
    } 
    try {
      if (paramInputStream != null) {
        byte[] arrayOfByte = new byte[8192];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        while ((i = paramInputStream.read(arrayOfByte)) != -1) {
          byte[] arrayOfByte4 = cipher.update(arrayOfByte, 0, i);
          byteArrayOutputStream.write(arrayOfByte4);
        } 
        byteArrayOutputStream.write(cipher.doFinal());
        arrayOfByte1 = byteArrayOutputStream.toByteArray();
      } else {
        arrayOfByte1 = cipher.doFinal(str1.getBytes("UTF-8"));
        logger.log(Level.FINE, "Expected cipher.outputSize = " + Integer.toString(cipher.getOutputSize((str1.getBytes()).length)));
      } 
      logger.log(Level.FINE, "Actual cipher.outputSize = " + Integer.toString(arrayOfByte1.length));
    } catch (IllegalStateException illegalStateException) {
      throw new XMLEncryptionException("empty", illegalStateException);
    } catch (IllegalBlockSizeException illegalBlockSizeException) {
      throw new XMLEncryptionException("empty", illegalBlockSizeException);
    } catch (BadPaddingException badPaddingException) {
      throw new XMLEncryptionException("empty", badPaddingException);
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw new XMLEncryptionException("empty", unsupportedEncodingException);
    } 
    byte[] arrayOfByte2 = cipher.getIV();
    byte[] arrayOfByte3 = new byte[arrayOfByte2.length + arrayOfByte1.length];
    System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, arrayOfByte2.length);
    System.arraycopy(arrayOfByte1, 0, arrayOfByte3, arrayOfByte2.length, arrayOfByte1.length);
    String str2 = Base64.encode(arrayOfByte3);
    logger.log(Level.FINE, "Encrypted octets:\n" + str2);
    logger.log(Level.FINE, "Encrypted octets length = " + str2.length());
    try {
      CipherData cipherData = this._ed.getCipherData();
      CipherValue cipherValue = cipherData.getCipherValue();
      cipherValue.setValue(str2);
      if (paramString != null)
        this._ed.setType((new URI(paramString)).toString()); 
      EncryptionMethod encryptionMethod = this._factory.newEncryptionMethod((new URI(this._algorithm)).toString());
      this._ed.setEncryptionMethod(encryptionMethod);
    } catch (com.sun.org.apache.xml.internal.utils.URI.MalformedURIException malformedURIException) {
      throw new XMLEncryptionException("empty", malformedURIException);
    } 
    return this._ed;
  }
  
  public EncryptedData loadEncryptedData(Document paramDocument, Element paramElement) throws XMLEncryptionException {
    logger.log(Level.FINE, "Loading encrypted element...");
    if (null == paramDocument)
      logger.log(Level.SEVERE, "Context document unexpectedly null..."); 
    if (null == paramElement)
      logger.log(Level.SEVERE, "Element unexpectedly null..."); 
    if (this._cipherMode != 2)
      logger.log(Level.SEVERE, "XMLCipher unexpectedly not in DECRYPT_MODE..."); 
    this._contextDocument = paramDocument;
    this._ed = this._factory.newEncryptedData(paramElement);
    return this._ed;
  }
  
  public EncryptedKey loadEncryptedKey(Document paramDocument, Element paramElement) throws XMLEncryptionException {
    logger.log(Level.FINE, "Loading encrypted key...");
    if (null == paramDocument)
      logger.log(Level.SEVERE, "Context document unexpectedly null..."); 
    if (null == paramElement)
      logger.log(Level.SEVERE, "Element unexpectedly null..."); 
    if (this._cipherMode != 4 && this._cipherMode != 2)
      logger.log(Level.FINE, "XMLCipher unexpectedly not in UNWRAP_MODE or DECRYPT_MODE..."); 
    this._contextDocument = paramDocument;
    this._ek = this._factory.newEncryptedKey(paramElement);
    return this._ek;
  }
  
  public EncryptedKey loadEncryptedKey(Element paramElement) throws XMLEncryptionException {
    return loadEncryptedKey(paramElement.getOwnerDocument(), paramElement);
  }
  
  public EncryptedKey encryptKey(Document paramDocument, Key paramKey) throws XMLEncryptionException {
    Cipher cipher;
    logger.log(Level.FINE, "Encrypting key ...");
    if (null == paramKey)
      logger.log(Level.SEVERE, "Key unexpectedly null..."); 
    if (this._cipherMode != 3)
      logger.log(Level.FINE, "XMLCipher unexpectedly not in WRAP_MODE..."); 
    if (this._algorithm == null)
      throw new XMLEncryptionException("XMLCipher instance without transformation specified"); 
    this._contextDocument = paramDocument;
    byte[] arrayOfByte = null;
    if (this._contextCipher == null) {
      String str1 = JCEMapper.translateURItoJCEID(this._algorithm);
      logger.log(Level.FINE, "alg = " + str1);
      try {
        if (this._requestedJCEProvider == null) {
          cipher = Cipher.getInstance(str1);
        } else {
          cipher = Cipher.getInstance(str1, this._requestedJCEProvider);
        } 
      } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        throw new XMLEncryptionException("empty", noSuchAlgorithmException);
      } catch (NoSuchProviderException noSuchProviderException) {
        throw new XMLEncryptionException("empty", noSuchProviderException);
      } catch (NoSuchPaddingException noSuchPaddingException) {
        throw new XMLEncryptionException("empty", noSuchPaddingException);
      } 
    } else {
      cipher = this._contextCipher;
    } 
    try {
      cipher.init(3, this._key);
      arrayOfByte = cipher.wrap(paramKey);
    } catch (InvalidKeyException invalidKeyException) {
      throw new XMLEncryptionException("empty", invalidKeyException);
    } catch (IllegalBlockSizeException illegalBlockSizeException) {
      throw new XMLEncryptionException("empty", illegalBlockSizeException);
    } 
    String str = Base64.encode(arrayOfByte);
    logger.log(Level.FINE, "Encrypted key octets:\n" + str);
    logger.log(Level.FINE, "Encrypted key octets length = " + str.length());
    CipherValue cipherValue = this._ek.getCipherData().getCipherValue();
    cipherValue.setValue(str);
    try {
      EncryptionMethod encryptionMethod = this._factory.newEncryptionMethod((new URI(this._algorithm)).toString());
      this._ek.setEncryptionMethod(encryptionMethod);
    } catch (com.sun.org.apache.xml.internal.utils.URI.MalformedURIException malformedURIException) {
      throw new XMLEncryptionException("empty", malformedURIException);
    } 
    return this._ek;
  }
  
  public Key decryptKey(EncryptedKey paramEncryptedKey, String paramString) throws XMLEncryptionException {
    Cipher cipher;
    Key key;
    logger.log(Level.FINE, "Decrypting key from previously loaded EncryptedKey...");
    if (this._cipherMode != 4)
      logger.log(Level.FINE, "XMLCipher unexpectedly not in UNWRAP_MODE..."); 
    if (paramString == null)
      throw new XMLEncryptionException("Cannot decrypt a key without knowing the algorithm"); 
    if (this._key == null) {
      logger.log(Level.FINE, "Trying to find a KEK via key resolvers");
      KeyInfo keyInfo = paramEncryptedKey.getKeyInfo();
      if (keyInfo != null)
        try {
          this._key = keyInfo.getSecretKey();
        } catch (Exception exception) {} 
      if (this._key == null) {
        logger.log(Level.SEVERE, "XMLCipher::decryptKey called without a KEK and cannot resolve");
        throw new XMLEncryptionException("Unable to decrypt without a KEK");
      } 
    } 
    XMLCipherInput xMLCipherInput = new XMLCipherInput(paramEncryptedKey);
    byte[] arrayOfByte = xMLCipherInput.getBytes();
    String str = JCEMapper.getJCEKeyAlgorithmFromURI(paramString);
    if (this._contextCipher == null) {
      String str1 = JCEMapper.translateURItoJCEID(paramEncryptedKey.getEncryptionMethod().getAlgorithm());
      logger.log(Level.FINE, "JCE Algorithm = " + str1);
      try {
        if (this._requestedJCEProvider == null) {
          cipher = Cipher.getInstance(str1);
        } else {
          cipher = Cipher.getInstance(str1, this._requestedJCEProvider);
        } 
      } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        throw new XMLEncryptionException("empty", noSuchAlgorithmException);
      } catch (NoSuchProviderException noSuchProviderException) {
        throw new XMLEncryptionException("empty", noSuchProviderException);
      } catch (NoSuchPaddingException noSuchPaddingException) {
        throw new XMLEncryptionException("empty", noSuchPaddingException);
      } 
    } else {
      cipher = this._contextCipher;
    } 
    try {
      cipher.init(4, this._key);
      key = cipher.unwrap(arrayOfByte, str, 3);
    } catch (InvalidKeyException invalidKeyException) {
      throw new XMLEncryptionException("empty", invalidKeyException);
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new XMLEncryptionException("empty", noSuchAlgorithmException);
    } 
    logger.log(Level.FINE, "Decryption of key type " + paramString + " OK");
    return key;
  }
  
  public Key decryptKey(EncryptedKey paramEncryptedKey) throws XMLEncryptionException {
    return decryptKey(paramEncryptedKey, this._ed.getEncryptionMethod().getAlgorithm());
  }
  
  private static void removeContent(Node paramNode) {
    while (paramNode.hasChildNodes())
      paramNode.removeChild(paramNode.getFirstChild()); 
  }
  
  private Document decryptElement(Element paramElement) throws XMLEncryptionException {
    String str;
    logger.log(Level.FINE, "Decrypting element...");
    if (this._cipherMode != 2)
      logger.log(Level.SEVERE, "XMLCipher unexpectedly not in DECRYPT_MODE..."); 
    try {
      str = new String(decryptToByteArray(paramElement), "UTF-8");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw new XMLEncryptionException("empty", unsupportedEncodingException);
    } 
    logger.log(Level.FINE, "Decrypted octets:\n" + str);
    Node node = paramElement.getParentNode();
    DocumentFragment documentFragment = this._serializer.deserialize(str, node);
    if (node instanceof Document) {
      this._contextDocument.removeChild(this._contextDocument.getDocumentElement());
      this._contextDocument.appendChild(documentFragment);
    } else {
      node.replaceChild(documentFragment, paramElement);
    } 
    return this._contextDocument;
  }
  
  private Document decryptElementContent(Element paramElement) throws XMLEncryptionException {
    Element element = (Element)paramElement.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptedData").item(0);
    if (null == element)
      throw new XMLEncryptionException("No EncryptedData child element."); 
    return decryptElement(element);
  }
  
  public byte[] decryptToByteArray(Element paramElement) throws XMLEncryptionException {
    Cipher cipher;
    byte[] arrayOfByte3;
    logger.log(Level.FINE, "Decrypting to ByteArray...");
    if (this._cipherMode != 2)
      logger.log(Level.SEVERE, "XMLCipher unexpectedly not in DECRYPT_MODE..."); 
    EncryptedData encryptedData = this._factory.newEncryptedData(paramElement);
    if (this._key == null) {
      KeyInfo keyInfo = encryptedData.getKeyInfo();
      if (keyInfo != null)
        try {
          keyInfo.registerInternalKeyResolver((KeyResolverSpi)new EncryptedKeyResolver(encryptedData.getEncryptionMethod().getAlgorithm(), this._kek));
          this._key = keyInfo.getSecretKey();
        } catch (KeyResolverException keyResolverException) {} 
      if (this._key == null) {
        logger.log(Level.SEVERE, "XMLCipher::decryptElement called without a key and unable to resolve");
        throw new XMLEncryptionException("encryption.nokey");
      } 
    } 
    XMLCipherInput xMLCipherInput = new XMLCipherInput(encryptedData);
    byte[] arrayOfByte1 = xMLCipherInput.getBytes();
    String str = JCEMapper.translateURItoJCEID(encryptedData.getEncryptionMethod().getAlgorithm());
    try {
      if (this._requestedJCEProvider == null) {
        cipher = Cipher.getInstance(str);
      } else {
        cipher = Cipher.getInstance(str, this._requestedJCEProvider);
      } 
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new XMLEncryptionException("empty", noSuchAlgorithmException);
    } catch (NoSuchProviderException noSuchProviderException) {
      throw new XMLEncryptionException("empty", noSuchProviderException);
    } catch (NoSuchPaddingException noSuchPaddingException) {
      throw new XMLEncryptionException("empty", noSuchPaddingException);
    } 
    int i = cipher.getBlockSize();
    byte[] arrayOfByte2 = new byte[i];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
    IvParameterSpec ivParameterSpec = new IvParameterSpec(arrayOfByte2);
    try {
      cipher.init(this._cipherMode, this._key, ivParameterSpec);
    } catch (InvalidKeyException invalidKeyException) {
      throw new XMLEncryptionException("empty", invalidKeyException);
    } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
      throw new XMLEncryptionException("empty", invalidAlgorithmParameterException);
    } 
    try {
      arrayOfByte3 = cipher.doFinal(arrayOfByte1, i, arrayOfByte1.length - i);
    } catch (IllegalBlockSizeException illegalBlockSizeException) {
      throw new XMLEncryptionException("empty", illegalBlockSizeException);
    } catch (BadPaddingException badPaddingException) {
      throw new XMLEncryptionException("empty", badPaddingException);
    } 
    return arrayOfByte3;
  }
  
  public EncryptedData createEncryptedData(int paramInt, String paramString) throws XMLEncryptionException {
    CipherReference cipherReference;
    CipherValue cipherValue;
    EncryptedData encryptedData = null;
    CipherData cipherData = null;
    switch (paramInt) {
      case 2:
        cipherReference = this._factory.newCipherReference(paramString);
        cipherData = this._factory.newCipherData(paramInt);
        cipherData.setCipherReference(cipherReference);
        encryptedData = this._factory.newEncryptedData(cipherData);
        break;
      case 1:
        cipherValue = this._factory.newCipherValue(paramString);
        cipherData = this._factory.newCipherData(paramInt);
        cipherData.setCipherValue(cipherValue);
        encryptedData = this._factory.newEncryptedData(cipherData);
        break;
    } 
    return encryptedData;
  }
  
  public EncryptedKey createEncryptedKey(int paramInt, String paramString) throws XMLEncryptionException {
    CipherReference cipherReference;
    CipherValue cipherValue;
    EncryptedKey encryptedKey = null;
    CipherData cipherData = null;
    switch (paramInt) {
      case 2:
        cipherReference = this._factory.newCipherReference(paramString);
        cipherData = this._factory.newCipherData(paramInt);
        cipherData.setCipherReference(cipherReference);
        encryptedKey = this._factory.newEncryptedKey(cipherData);
        break;
      case 1:
        cipherValue = this._factory.newCipherValue(paramString);
        cipherData = this._factory.newCipherData(paramInt);
        cipherData.setCipherValue(cipherValue);
        encryptedKey = this._factory.newEncryptedKey(cipherData);
        break;
    } 
    return encryptedKey;
  }
  
  public AgreementMethod createAgreementMethod(String paramString) {
    return this._factory.newAgreementMethod(paramString);
  }
  
  public CipherData createCipherData(int paramInt) {
    return this._factory.newCipherData(paramInt);
  }
  
  public CipherReference createCipherReference(String paramString) {
    return this._factory.newCipherReference(paramString);
  }
  
  public CipherValue createCipherValue(String paramString) {
    return this._factory.newCipherValue(paramString);
  }
  
  public EncryptionMethod createEncryptionMethod(String paramString) {
    return this._factory.newEncryptionMethod(paramString);
  }
  
  public EncryptionProperties createEncryptionProperties() {
    return this._factory.newEncryptionProperties();
  }
  
  public EncryptionProperty createEncryptionProperty() {
    return this._factory.newEncryptionProperty();
  }
  
  public ReferenceList createReferenceList(int paramInt) {
    return this._factory.newReferenceList(paramInt);
  }
  
  public Transforms createTransforms() {
    return this._factory.newTransforms();
  }
  
  public Transforms createTransforms(Document paramDocument) {
    return this._factory.newTransforms(paramDocument);
  }
  
  private class Factory {
    private final XMLCipher this$0;
    
    private Factory() {}
    
    AgreementMethod newAgreementMethod(String param1String) {
      return new AgreementMethodImpl(param1String);
    }
    
    CipherData newCipherData(int param1Int) {
      return new CipherDataImpl(param1Int);
    }
    
    CipherReference newCipherReference(String param1String) {
      return new CipherReferenceImpl(param1String);
    }
    
    CipherValue newCipherValue(String param1String) {
      return new CipherValueImpl(param1String);
    }
    
    EncryptedData newEncryptedData(CipherData param1CipherData) {
      return new EncryptedDataImpl(param1CipherData);
    }
    
    EncryptedKey newEncryptedKey(CipherData param1CipherData) {
      return new EncryptedKeyImpl(param1CipherData);
    }
    
    EncryptionMethod newEncryptionMethod(String param1String) {
      return new EncryptionMethodImpl(param1String);
    }
    
    EncryptionProperties newEncryptionProperties() {
      return new EncryptionPropertiesImpl();
    }
    
    EncryptionProperty newEncryptionProperty() {
      return new EncryptionPropertyImpl();
    }
    
    ReferenceList newReferenceList(int param1Int) {
      return new ReferenceListImpl(param1Int);
    }
    
    Transforms newTransforms() {
      return new TransformsImpl();
    }
    
    Transforms newTransforms(Document param1Document) {
      return new TransformsImpl(param1Document);
    }
    
    AgreementMethod newAgreementMethod(Element param1Element) throws XMLEncryptionException {
      if (null == param1Element)
        throw new NullPointerException("element is null"); 
      String str = param1Element.getAttributeNS((String)null, "Algorithm");
      AgreementMethod agreementMethod = newAgreementMethod(str);
      Element element1 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "KA-Nonce").item(0);
      if (null != element1)
        agreementMethod.setKANonce(element1.getNodeValue().getBytes()); 
      Element element2 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "OriginatorKeyInfo").item(0);
      if (null != element2)
        try {
          agreementMethod.setOriginatorKeyInfo(new KeyInfo(element2, null));
        } catch (XMLSecurityException xMLSecurityException) {
          throw new XMLEncryptionException("empty", xMLSecurityException);
        }  
      Element element3 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "RecipientKeyInfo").item(0);
      if (null != element3)
        try {
          agreementMethod.setRecipientKeyInfo(new KeyInfo(element3, null));
        } catch (XMLSecurityException xMLSecurityException) {
          throw new XMLEncryptionException("empty", xMLSecurityException);
        }  
      return agreementMethod;
    }
    
    CipherData newCipherData(Element param1Element) throws XMLEncryptionException {
      if (null == param1Element)
        throw new NullPointerException("element is null"); 
      byte b = 0;
      Element element = null;
      if (param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").getLength() > 0) {
        b = 1;
        element = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0);
      } else if (param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherReference").getLength() > 0) {
        b = 2;
        element = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherReference").item(0);
      } 
      CipherData cipherData = newCipherData(b);
      if (b == 1) {
        cipherData.setCipherValue(newCipherValue(element));
      } else if (b == 2) {
        cipherData.setCipherReference(newCipherReference(element));
      } 
      return cipherData;
    }
    
    CipherReference newCipherReference(Element param1Element) throws XMLEncryptionException {
      Attr attr = param1Element.getAttributeNodeNS((String)null, "URI");
      CipherReferenceImpl cipherReferenceImpl = new CipherReferenceImpl(attr);
      NodeList nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "Transforms");
      Element element = (Element)nodeList.item(0);
      if (element != null) {
        XMLCipher.logger.log(Level.FINE, "Creating a DSIG based Transforms element");
        try {
          cipherReferenceImpl.setTransforms(new TransformsImpl(element));
        } catch (XMLSignatureException xMLSignatureException) {
          throw new XMLEncryptionException("empty", xMLSignatureException);
        } catch (InvalidTransformException invalidTransformException) {
          throw new XMLEncryptionException("empty", invalidTransformException);
        } catch (XMLSecurityException xMLSecurityException) {
          throw new XMLEncryptionException("empty", xMLSecurityException);
        } 
      } 
      return cipherReferenceImpl;
    }
    
    CipherValue newCipherValue(Element param1Element) {
      String str = XMLUtils.getFullTextChildrenFromElement(param1Element);
      return newCipherValue(str);
    }
    
    EncryptedData newEncryptedData(Element param1Element) throws XMLEncryptionException {
      EncryptedData encryptedData = null;
      NodeList nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherData");
      Element element1 = (Element)nodeList.item(nodeList.getLength() - 1);
      CipherData cipherData = newCipherData(element1);
      encryptedData = newEncryptedData(cipherData);
      encryptedData.setId(param1Element.getAttributeNS((String)null, "Id"));
      encryptedData.setType(param1Element.getAttributeNS((String)null, "Type"));
      encryptedData.setMimeType(param1Element.getAttributeNS((String)null, "MimeType"));
      encryptedData.setEncoding(param1Element.getAttributeNS((String)null, "Encoding"));
      Element element2 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod").item(0);
      if (null != element2)
        encryptedData.setEncryptionMethod(newEncryptionMethod(element2)); 
      Element element3 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "KeyInfo").item(0);
      if (null != element3)
        try {
          encryptedData.setKeyInfo(new KeyInfo(element3, null));
        } catch (XMLSecurityException xMLSecurityException) {
          throw new XMLEncryptionException("Error loading Key Info", xMLSecurityException);
        }  
      Element element4 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties").item(0);
      if (null != element4)
        encryptedData.setEncryptionProperties(newEncryptionProperties(element4)); 
      return encryptedData;
    }
    
    EncryptedKey newEncryptedKey(Element param1Element) throws XMLEncryptionException {
      EncryptedKey encryptedKey = null;
      NodeList nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherData");
      Element element1 = (Element)nodeList.item(nodeList.getLength() - 1);
      CipherData cipherData = newCipherData(element1);
      encryptedKey = newEncryptedKey(cipherData);
      encryptedKey.setId(param1Element.getAttributeNS((String)null, "Id"));
      encryptedKey.setType(param1Element.getAttributeNS((String)null, "Type"));
      encryptedKey.setMimeType(param1Element.getAttributeNS((String)null, "MimeType"));
      encryptedKey.setEncoding(param1Element.getAttributeNS((String)null, "Encoding"));
      encryptedKey.setRecipient(param1Element.getAttributeNS((String)null, "Recipient"));
      Element element2 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod").item(0);
      if (null != element2)
        encryptedKey.setEncryptionMethod(newEncryptionMethod(element2)); 
      Element element3 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "KeyInfo").item(0);
      if (null != element3)
        try {
          encryptedKey.setKeyInfo(new KeyInfo(element3, null));
        } catch (XMLSecurityException xMLSecurityException) {
          throw new XMLEncryptionException("Error loading Key Info", xMLSecurityException);
        }  
      Element element4 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties").item(0);
      if (null != element4)
        encryptedKey.setEncryptionProperties(newEncryptionProperties(element4)); 
      Element element5 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "ReferenceList").item(0);
      if (null != element5)
        encryptedKey.setReferenceList(newReferenceList(element5)); 
      Element element6 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CarriedKeyName").item(0);
      if (null != element6)
        encryptedKey.setCarriedName(element6.getFirstChild().getNodeValue()); 
      return encryptedKey;
    }
    
    EncryptionMethod newEncryptionMethod(Element param1Element) {
      String str = param1Element.getAttributeNS((String)null, "Algorithm");
      EncryptionMethod encryptionMethod = newEncryptionMethod(str);
      Element element1 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "KeySize").item(0);
      if (null != element1)
        encryptionMethod.setKeySize(Integer.valueOf(element1.getFirstChild().getNodeValue()).intValue()); 
      Element element2 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "OAEPparams").item(0);
      if (null != element2)
        encryptionMethod.setOAEPparams(element2.getNodeValue().getBytes()); 
      return encryptionMethod;
    }
    
    EncryptionProperties newEncryptionProperties(Element param1Element) {
      EncryptionProperties encryptionProperties = newEncryptionProperties();
      encryptionProperties.setId(param1Element.getAttributeNS((String)null, "Id"));
      NodeList nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty");
      for (byte b = 0; b < nodeList.getLength(); b++) {
        Node node = nodeList.item(b);
        if (null != node)
          encryptionProperties.addEncryptionProperty(newEncryptionProperty((Element)node)); 
      } 
      return encryptionProperties;
    }
    
    EncryptionProperty newEncryptionProperty(Element param1Element) {
      EncryptionProperty encryptionProperty = newEncryptionProperty();
      encryptionProperty.setTarget(param1Element.getAttributeNS((String)null, "Target"));
      encryptionProperty.setId(param1Element.getAttributeNS((String)null, "Id"));
      return encryptionProperty;
    }
    
    ReferenceList newReferenceList(Element param1Element) {
      byte b2;
      byte b1 = 0;
      if (null != param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "DataReference").item(0)) {
        b1 = 1;
      } else if (null != param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "KeyReference").item(0)) {
        b1 = 2;
      } 
      ReferenceListImpl referenceListImpl = new ReferenceListImpl(b1);
      NodeList nodeList = null;
      switch (b1) {
        case 1:
          nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "DataReference");
          for (b2 = 0; b2 < nodeList.getLength(); b2++) {
            String str = ((Element)nodeList.item(b2)).getAttribute("URI");
            referenceListImpl.add(referenceListImpl.newDataReference(str));
          } 
          break;
        case 2:
          nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "KeyReference");
          for (b2 = 0; b2 < nodeList.getLength(); b2++) {
            String str = ((Element)nodeList.item(b2)).getAttribute("URI");
            referenceListImpl.add(referenceListImpl.newKeyReference(str));
          } 
          break;
      } 
      return referenceListImpl;
    }
    
    Transforms newTransforms(Element param1Element) {
      return null;
    }
    
    Element toElement(AgreementMethod param1AgreementMethod) {
      return ((AgreementMethodImpl)param1AgreementMethod).toElement();
    }
    
    Element toElement(CipherData param1CipherData) {
      return ((CipherDataImpl)param1CipherData).toElement();
    }
    
    Element toElement(CipherReference param1CipherReference) {
      return ((CipherReferenceImpl)param1CipherReference).toElement();
    }
    
    Element toElement(CipherValue param1CipherValue) {
      return ((CipherValueImpl)param1CipherValue).toElement();
    }
    
    Element toElement(EncryptedData param1EncryptedData) {
      return ((EncryptedDataImpl)param1EncryptedData).toElement();
    }
    
    Element toElement(EncryptedKey param1EncryptedKey) {
      return ((EncryptedKeyImpl)param1EncryptedKey).toElement();
    }
    
    Element toElement(EncryptionMethod param1EncryptionMethod) {
      return ((EncryptionMethodImpl)param1EncryptionMethod).toElement();
    }
    
    Element toElement(EncryptionProperties param1EncryptionProperties) {
      return ((EncryptionPropertiesImpl)param1EncryptionProperties).toElement();
    }
    
    Element toElement(EncryptionProperty param1EncryptionProperty) {
      return ((EncryptionPropertyImpl)param1EncryptionProperty).toElement();
    }
    
    Element toElement(ReferenceList param1ReferenceList) {
      return ((ReferenceListImpl)param1ReferenceList).toElement();
    }
    
    Element toElement(Transforms param1Transforms) {
      return ((TransformsImpl)param1Transforms).toElement();
    }
    
    private class ReferenceListImpl implements ReferenceList {
      private Class sentry;
      
      private List references;
      
      private final XMLCipher.Factory this$1;
      
      public ReferenceListImpl(int param2Int) {
        if (param2Int == 1) {
          this.sentry = (XMLCipher.class$com$sun$org$apache$xml$internal$security$encryption$XMLCipher$Factory$ReferenceListImpl$DataReference == null) ? (XMLCipher.class$com$sun$org$apache$xml$internal$security$encryption$XMLCipher$Factory$ReferenceListImpl$DataReference = XMLCipher.class$("com.sun.org.apache.xml.internal.security.encryption.XMLCipher$Factory$ReferenceListImpl$DataReference")) : XMLCipher.class$com$sun$org$apache$xml$internal$security$encryption$XMLCipher$Factory$ReferenceListImpl$DataReference;
        } else if (param2Int == 2) {
          this.sentry = (XMLCipher.class$com$sun$org$apache$xml$internal$security$encryption$XMLCipher$Factory$ReferenceListImpl$KeyReference == null) ? (XMLCipher.class$com$sun$org$apache$xml$internal$security$encryption$XMLCipher$Factory$ReferenceListImpl$KeyReference = XMLCipher.class$("com.sun.org.apache.xml.internal.security.encryption.XMLCipher$Factory$ReferenceListImpl$KeyReference")) : XMLCipher.class$com$sun$org$apache$xml$internal$security$encryption$XMLCipher$Factory$ReferenceListImpl$KeyReference;
        } else {
          throw new IllegalArgumentException();
        } 
        this.references = new LinkedList();
      }
      
      public void add(Reference param2Reference) {
        if (!param2Reference.getClass().equals(this.sentry))
          throw new IllegalArgumentException(); 
        this.references.add(param2Reference);
      }
      
      public void remove(Reference param2Reference) {
        if (!param2Reference.getClass().equals(this.sentry))
          throw new IllegalArgumentException(); 
        this.references.remove(param2Reference);
      }
      
      public int size() {
        return this.references.size();
      }
      
      public boolean isEmpty() {
        return this.references.isEmpty();
      }
      
      public Iterator getReferences() {
        return this.references.iterator();
      }
      
      Element toElement() {
        Element element = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "ReferenceList");
        for (Reference reference : this.references)
          element.appendChild(((ReferenceImpl)reference).toElement()); 
        return element;
      }
      
      public Reference newDataReference(String param2String) {
        return new DataReference(param2String);
      }
      
      public Reference newKeyReference(String param2String) {
        return new KeyReference(param2String);
      }
      
      private class KeyReference extends ReferenceImpl {
        private final XMLCipher.Factory.ReferenceListImpl this$2;
        
        KeyReference(String param3String) {
          super(param3String);
        }
        
        public Element toElement() {
          return toElement("KeyReference");
        }
      }
      
      private class DataReference extends ReferenceImpl {
        private final XMLCipher.Factory.ReferenceListImpl this$2;
        
        DataReference(String param3String) {
          super(param3String);
        }
        
        public Element toElement() {
          return toElement("DataReference");
        }
      }
      
      private abstract class ReferenceImpl implements Reference {
        private String uri;
        
        private List referenceInformation;
        
        private final XMLCipher.Factory.ReferenceListImpl this$2;
        
        ReferenceImpl(String param3String) {
          this.uri = param3String;
          this.referenceInformation = new LinkedList();
        }
        
        public String getURI() {
          return this.uri;
        }
        
        public Iterator getElementRetrievalInformation() {
          return this.referenceInformation.iterator();
        }
        
        public void setURI(String param3String) {
          this.uri = param3String;
        }
        
        public void removeElementRetrievalInformation(Element param3Element) {
          this.referenceInformation.remove(param3Element);
        }
        
        public void addElementRetrievalInformation(Element param3Element) {
          this.referenceInformation.add(param3Element);
        }
        
        public abstract Element toElement();
        
        Element toElement(String param3String) {
          Element element = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.ReferenceListImpl.access$500(XMLCipher.Factory.ReferenceListImpl.this)))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", param3String);
          element.setAttribute("URI", this.uri);
          return element;
        }
      }
    }
    
    private class TransformsImpl extends Transforms implements Transforms {
      private final XMLCipher.Factory this$1;
      
      public TransformsImpl() {
        super((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument);
      }
      
      public TransformsImpl(Document param2Document) {
        if (param2Document == null)
          throw new RuntimeException("Document is null"); 
        this._doc = param2Document;
        this._constructionElement = createElementForFamilyLocal(this._doc, getBaseNamespace(), getBaseLocalName());
      }
      
      public TransformsImpl(Element param2Element) throws XMLSignatureException, InvalidTransformException, XMLSecurityException, TransformationException {
        super(param2Element, "");
      }
      
      public Element toElement() {
        if (this._doc == null)
          this._doc = (XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument; 
        return getElement();
      }
      
      public Transforms getDSTransforms() {
        return this;
      }
      
      public String getBaseNamespace() {
        return "http://www.w3.org/2001/04/xmlenc#";
      }
    }
    
    private class EncryptionPropertyImpl implements EncryptionProperty {
      private String target = null;
      
      private String id = null;
      
      private HashMap attributeMap = new HashMap();
      
      private List encryptionInformation = null;
      
      private final XMLCipher.Factory this$1;
      
      public EncryptionPropertyImpl() {
        this.encryptionInformation = new LinkedList();
      }
      
      public String getTarget() {
        return this.target;
      }
      
      public void setTarget(String param2String) {
        if (param2String == null || param2String.length() == 0) {
          this.target = null;
        } else if (param2String.startsWith("#")) {
          this.target = param2String;
        } else {
          URI uRI = null;
          try {
            uRI = new URI(param2String);
          } catch (com.sun.org.apache.xml.internal.utils.URI.MalformedURIException malformedURIException) {}
          this.target = uRI.toString();
        } 
      }
      
      public String getId() {
        return this.id;
      }
      
      public void setId(String param2String) {
        this.id = param2String;
      }
      
      public String getAttribute(String param2String) {
        return (String)this.attributeMap.get(param2String);
      }
      
      public void setAttribute(String param2String1, String param2String2) {
        this.attributeMap.put(param2String1, param2String2);
      }
      
      public Iterator getEncryptionInformation() {
        return this.encryptionInformation.iterator();
      }
      
      public void addEncryptionInformation(Element param2Element) {
        this.encryptionInformation.add(param2Element);
      }
      
      public void removeEncryptionInformation(Element param2Element) {
        this.encryptionInformation.remove(param2Element);
      }
      
      Element toElement() {
        Element element = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty");
        if (null != this.target)
          element.setAttributeNS((String)null, "Target", this.target); 
        if (null != this.id)
          element.setAttributeNS((String)null, "Id", this.id); 
        return element;
      }
    }
    
    private class EncryptionPropertiesImpl implements EncryptionProperties {
      private String id = null;
      
      private List encryptionProperties = null;
      
      private final XMLCipher.Factory this$1;
      
      public EncryptionPropertiesImpl() {
        this.encryptionProperties = new LinkedList();
      }
      
      public String getId() {
        return this.id;
      }
      
      public void setId(String param2String) {
        this.id = param2String;
      }
      
      public Iterator getEncryptionProperties() {
        return this.encryptionProperties.iterator();
      }
      
      public void addEncryptionProperty(EncryptionProperty param2EncryptionProperty) {
        this.encryptionProperties.add(param2EncryptionProperty);
      }
      
      public void removeEncryptionProperty(EncryptionProperty param2EncryptionProperty) {
        this.encryptionProperties.remove(param2EncryptionProperty);
      }
      
      Element toElement() {
        Element element = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties");
        if (null != this.id)
          element.setAttributeNS((String)null, "Id", this.id); 
        Iterator iterator = getEncryptionProperties();
        while (iterator.hasNext())
          element.appendChild(((XMLCipher.Factory.EncryptionPropertyImpl)iterator.next()).toElement()); 
        return element;
      }
    }
    
    private class EncryptionMethodImpl implements EncryptionMethod {
      private String algorithm = null;
      
      private int keySize = Integer.MIN_VALUE;
      
      private byte[] oaepParams = null;
      
      private List encryptionMethodInformation = null;
      
      private final XMLCipher.Factory this$1;
      
      public EncryptionMethodImpl(String param2String) {
        URI uRI = null;
        try {
          uRI = new URI(param2String);
        } catch (com.sun.org.apache.xml.internal.utils.URI.MalformedURIException malformedURIException) {}
        this.algorithm = uRI.toString();
        this.encryptionMethodInformation = new LinkedList();
      }
      
      public String getAlgorithm() {
        return this.algorithm;
      }
      
      public int getKeySize() {
        return this.keySize;
      }
      
      public void setKeySize(int param2Int) {
        this.keySize = param2Int;
      }
      
      public byte[] getOAEPparams() {
        return this.oaepParams;
      }
      
      public void setOAEPparams(byte[] param2ArrayOfbyte) {
        this.oaepParams = param2ArrayOfbyte;
      }
      
      public Iterator getEncryptionMethodInformation() {
        return this.encryptionMethodInformation.iterator();
      }
      
      public void addEncryptionMethodInformation(Element param2Element) {
        this.encryptionMethodInformation.add(param2Element);
      }
      
      public void removeEncryptionMethodInformation(Element param2Element) {
        this.encryptionMethodInformation.remove(param2Element);
      }
      
      Element toElement() {
        Element element = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod");
        element.setAttributeNS((String)null, "Algorithm", this.algorithm);
        if (this.keySize > 0)
          element.appendChild(ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "KeySize").appendChild((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument.createTextNode(String.valueOf(this.keySize)))); 
        if (null != this.oaepParams)
          element.appendChild(ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "OAEPparams").appendChild((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument.createTextNode(new String(this.oaepParams)))); 
        if (!this.encryptionMethodInformation.isEmpty()) {
          Iterator iterator = this.encryptionMethodInformation.iterator();
          element.appendChild(iterator.next());
        } 
        return element;
      }
    }
    
    private abstract class EncryptedTypeImpl {
      private String id = null;
      
      private String type = null;
      
      private String mimeType = null;
      
      private String encoding = null;
      
      private EncryptionMethod encryptionMethod = null;
      
      private KeyInfo keyInfo = null;
      
      private CipherData cipherData = null;
      
      private EncryptionProperties encryptionProperties = null;
      
      private final XMLCipher.Factory this$1;
      
      protected EncryptedTypeImpl(CipherData param2CipherData) {
        this.cipherData = param2CipherData;
      }
      
      public String getId() {
        return this.id;
      }
      
      public void setId(String param2String) {
        this.id = param2String;
      }
      
      public String getType() {
        return this.type;
      }
      
      public void setType(String param2String) {
        if (param2String == null || param2String.length() == 0) {
          this.type = null;
        } else {
          URI uRI = null;
          try {
            uRI = new URI(param2String);
          } catch (com.sun.org.apache.xml.internal.utils.URI.MalformedURIException malformedURIException) {}
          this.type = uRI.toString();
        } 
      }
      
      public String getMimeType() {
        return this.mimeType;
      }
      
      public void setMimeType(String param2String) {
        this.mimeType = param2String;
      }
      
      public String getEncoding() {
        return this.encoding;
      }
      
      public void setEncoding(String param2String) {
        if (param2String == null || param2String.length() == 0) {
          this.encoding = null;
        } else {
          URI uRI = null;
          try {
            uRI = new URI(param2String);
          } catch (com.sun.org.apache.xml.internal.utils.URI.MalformedURIException malformedURIException) {}
          this.encoding = uRI.toString();
        } 
      }
      
      public EncryptionMethod getEncryptionMethod() {
        return this.encryptionMethod;
      }
      
      public void setEncryptionMethod(EncryptionMethod param2EncryptionMethod) {
        this.encryptionMethod = param2EncryptionMethod;
      }
      
      public KeyInfo getKeyInfo() {
        return this.keyInfo;
      }
      
      public void setKeyInfo(KeyInfo param2KeyInfo) {
        this.keyInfo = param2KeyInfo;
      }
      
      public CipherData getCipherData() {
        return this.cipherData;
      }
      
      public EncryptionProperties getEncryptionProperties() {
        return this.encryptionProperties;
      }
      
      public void setEncryptionProperties(EncryptionProperties param2EncryptionProperties) {
        this.encryptionProperties = param2EncryptionProperties;
      }
    }
    
    private class EncryptedKeyImpl extends EncryptedTypeImpl implements EncryptedKey {
      private String keyRecipient = null;
      
      private ReferenceList referenceList = null;
      
      private String carriedName = null;
      
      private final XMLCipher.Factory this$1;
      
      public EncryptedKeyImpl(CipherData param2CipherData) {
        super(param2CipherData);
      }
      
      public String getRecipient() {
        return this.keyRecipient;
      }
      
      public void setRecipient(String param2String) {
        this.keyRecipient = param2String;
      }
      
      public ReferenceList getReferenceList() {
        return this.referenceList;
      }
      
      public void setReferenceList(ReferenceList param2ReferenceList) {
        this.referenceList = param2ReferenceList;
      }
      
      public String getCarriedName() {
        return this.carriedName;
      }
      
      public void setCarriedName(String param2String) {
        this.carriedName = param2String;
      }
      
      Element toElement() {
        Element element = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "EncryptedKey");
        if (null != getId())
          element.setAttributeNS((String)null, "Id", getId()); 
        if (null != getType())
          element.setAttributeNS((String)null, "Type", getType()); 
        if (null != getMimeType())
          element.setAttributeNS((String)null, "MimeType", getMimeType()); 
        if (null != getEncoding())
          element.setAttributeNS((String)null, "Encoding", getEncoding()); 
        if (null != getRecipient())
          element.setAttributeNS((String)null, "Recipient", getRecipient()); 
        if (null != getEncryptionMethod())
          element.appendChild(((XMLCipher.Factory.EncryptionMethodImpl)getEncryptionMethod()).toElement()); 
        if (null != getKeyInfo())
          element.appendChild(getKeyInfo().getElement()); 
        element.appendChild(((XMLCipher.Factory.CipherDataImpl)getCipherData()).toElement());
        if (null != getEncryptionProperties())
          element.appendChild(((XMLCipher.Factory.EncryptionPropertiesImpl)getEncryptionProperties()).toElement()); 
        if (this.referenceList != null && !this.referenceList.isEmpty())
          element.appendChild(((XMLCipher.Factory.ReferenceListImpl)getReferenceList()).toElement()); 
        if (null != this.carriedName) {
          Element element1 = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "CarriedKeyName");
          Text text = (XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument.createTextNode(this.carriedName);
          element1.appendChild(text);
          element.appendChild(element1);
        } 
        return element;
      }
    }
    
    private class EncryptedDataImpl extends EncryptedTypeImpl implements EncryptedData {
      private final XMLCipher.Factory this$1;
      
      public EncryptedDataImpl(CipherData param2CipherData) {
        super(param2CipherData);
      }
      
      Element toElement() {
        Element element = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "EncryptedData");
        if (null != getId())
          element.setAttributeNS((String)null, "Id", getId()); 
        if (null != getType())
          element.setAttributeNS((String)null, "Type", getType()); 
        if (null != getMimeType())
          element.setAttributeNS((String)null, "MimeType", getMimeType()); 
        if (null != getEncoding())
          element.setAttributeNS((String)null, "Encoding", getEncoding()); 
        if (null != getEncryptionMethod())
          element.appendChild(((XMLCipher.Factory.EncryptionMethodImpl)getEncryptionMethod()).toElement()); 
        if (null != getKeyInfo())
          element.appendChild(getKeyInfo().getElement()); 
        element.appendChild(((XMLCipher.Factory.CipherDataImpl)getCipherData()).toElement());
        if (null != getEncryptionProperties())
          element.appendChild(((XMLCipher.Factory.EncryptionPropertiesImpl)getEncryptionProperties()).toElement()); 
        return element;
      }
    }
    
    private class CipherValueImpl implements CipherValue {
      private String cipherValue = null;
      
      private final XMLCipher.Factory this$1;
      
      public CipherValueImpl(String param2String) {
        this.cipherValue = param2String;
      }
      
      public String getValue() {
        return this.cipherValue;
      }
      
      public void setValue(String param2String) {
        this.cipherValue = param2String;
      }
      
      Element toElement() {
        Element element = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "CipherValue");
        element.appendChild((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument.createTextNode(this.cipherValue));
        return element;
      }
    }
    
    private class CipherReferenceImpl implements CipherReference {
      private String referenceURI = null;
      
      private Transforms referenceTransforms = null;
      
      private Attr referenceNode = null;
      
      private final XMLCipher.Factory this$1;
      
      public CipherReferenceImpl(String param2String) {
        this.referenceURI = param2String;
        this.referenceNode = null;
      }
      
      public CipherReferenceImpl(Attr param2Attr) {
        this.referenceURI = param2Attr.getNodeValue();
        this.referenceNode = param2Attr;
      }
      
      public String getURI() {
        return this.referenceURI;
      }
      
      public Attr getURIAsAttr() {
        return this.referenceNode;
      }
      
      public Transforms getTransforms() {
        return this.referenceTransforms;
      }
      
      public void setTransforms(Transforms param2Transforms) {
        this.referenceTransforms = param2Transforms;
      }
      
      Element toElement() {
        Element element = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "CipherReference");
        element.setAttributeNS((String)null, "URI", this.referenceURI);
        if (null != this.referenceTransforms)
          element.appendChild(((XMLCipher.Factory.TransformsImpl)this.referenceTransforms).toElement()); 
        return element;
      }
    }
    
    private class CipherDataImpl implements CipherData {
      private static final String valueMessage = "Data type is reference type.";
      
      private static final String referenceMessage = "Data type is value type.";
      
      private CipherValue cipherValue = null;
      
      private CipherReference cipherReference = null;
      
      private int cipherType = Integer.MIN_VALUE;
      
      private final XMLCipher.Factory this$1;
      
      public CipherDataImpl(int param2Int) {
        this.cipherType = param2Int;
      }
      
      public CipherValue getCipherValue() {
        return this.cipherValue;
      }
      
      public void setCipherValue(CipherValue param2CipherValue) throws XMLEncryptionException {
        if (this.cipherType == 2)
          throw new XMLEncryptionException("empty", new UnsupportedOperationException("Data type is reference type.")); 
        this.cipherValue = param2CipherValue;
      }
      
      public CipherReference getCipherReference() {
        return this.cipherReference;
      }
      
      public void setCipherReference(CipherReference param2CipherReference) throws XMLEncryptionException {
        if (this.cipherType == 1)
          throw new XMLEncryptionException("empty", new UnsupportedOperationException("Data type is value type.")); 
        this.cipherReference = param2CipherReference;
      }
      
      public int getDataType() {
        return this.cipherType;
      }
      
      Element toElement() {
        Element element = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "CipherData");
        if (this.cipherType == 1) {
          element.appendChild(((XMLCipher.Factory.CipherValueImpl)this.cipherValue).toElement());
        } else if (this.cipherType == 2) {
          element.appendChild(((XMLCipher.Factory.CipherReferenceImpl)this.cipherReference).toElement());
        } 
        return element;
      }
    }
    
    private class AgreementMethodImpl implements AgreementMethod {
      private byte[] kaNonce = null;
      
      private List agreementMethodInformation = null;
      
      private KeyInfo originatorKeyInfo = null;
      
      private KeyInfo recipientKeyInfo = null;
      
      private String algorithmURI = null;
      
      private final XMLCipher.Factory this$1;
      
      public AgreementMethodImpl(String param2String) {
        this.agreementMethodInformation = new LinkedList();
        URI uRI = null;
        try {
          uRI = new URI(param2String);
        } catch (com.sun.org.apache.xml.internal.utils.URI.MalformedURIException malformedURIException) {}
        this.algorithmURI = uRI.toString();
      }
      
      public byte[] getKANonce() {
        return this.kaNonce;
      }
      
      public void setKANonce(byte[] param2ArrayOfbyte) {
        this.kaNonce = param2ArrayOfbyte;
      }
      
      public Iterator getAgreementMethodInformation() {
        return this.agreementMethodInformation.iterator();
      }
      
      public void addAgreementMethodInformation(Element param2Element) {
        this.agreementMethodInformation.add(param2Element);
      }
      
      public void revoveAgreementMethodInformation(Element param2Element) {
        this.agreementMethodInformation.remove(param2Element);
      }
      
      public KeyInfo getOriginatorKeyInfo() {
        return this.originatorKeyInfo;
      }
      
      public void setOriginatorKeyInfo(KeyInfo param2KeyInfo) {
        this.originatorKeyInfo = param2KeyInfo;
      }
      
      public KeyInfo getRecipientKeyInfo() {
        return this.recipientKeyInfo;
      }
      
      public void setRecipientKeyInfo(KeyInfo param2KeyInfo) {
        this.recipientKeyInfo = param2KeyInfo;
      }
      
      public String getAlgorithm() {
        return this.algorithmURI;
      }
      
      public void setAlgorithm(String param2String) {
        URI uRI = null;
        try {
          uRI = new URI(param2String);
        } catch (com.sun.org.apache.xml.internal.utils.URI.MalformedURIException malformedURIException) {}
        this.algorithmURI = uRI.toString();
      }
      
      Element toElement() {
        Element element = ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "AgreementMethod");
        element.setAttributeNS((String)null, "Algorithm", this.algorithmURI);
        if (null != this.kaNonce)
          element.appendChild(ElementProxy.createElementForFamily((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument, "http://www.w3.org/2001/04/xmlenc#", "KA-Nonce")).appendChild((XMLCipher.Factory.access$400(XMLCipher.Factory.this))._contextDocument.createTextNode(new String(this.kaNonce))); 
        if (!this.agreementMethodInformation.isEmpty()) {
          Iterator iterator = this.agreementMethodInformation.iterator();
          while (iterator.hasNext())
            element.appendChild(iterator.next()); 
        } 
        if (null != this.originatorKeyInfo)
          element.appendChild(this.originatorKeyInfo.getElement()); 
        if (null != this.recipientKeyInfo)
          element.appendChild(this.recipientKeyInfo.getElement()); 
        return element;
      }
    }
  }
  
  private class Serializer {
    private final XMLCipher this$0;
    
    String serialize(Document param1Document) throws Exception {
      return canonSerialize(param1Document);
    }
    
    String serialize(Element param1Element) throws Exception {
      return canonSerialize(param1Element);
    }
    
    String serialize(NodeList param1NodeList) throws Exception {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      XMLCipher.this._canon.setWriter(byteArrayOutputStream);
      XMLCipher.this._canon.notReset();
      for (byte b = 0; b < param1NodeList.getLength(); b++)
        XMLCipher.this._canon.canonicalizeSubtree(param1NodeList.item(b)); 
      byteArrayOutputStream.close();
      return byteArrayOutputStream.toString("UTF-8");
    }
    
    String canonSerialize(Node param1Node) throws Exception {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      XMLCipher.this._canon.setWriter(byteArrayOutputStream);
      XMLCipher.this._canon.notReset();
      XMLCipher.this._canon.canonicalizeSubtree(param1Node);
      byteArrayOutputStream.close();
      return byteArrayOutputStream.toString("UTF-8");
    }
    
    DocumentFragment deserialize(String param1String, Node param1Node) throws XMLEncryptionException {
      DocumentFragment documentFragment;
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><fragment");
      for (Node node = param1Node; node != null; node = node.getParentNode()) {
        byte b1;
        NamedNodeMap namedNodeMap = node.getAttributes();
        if (namedNodeMap != null) {
          b1 = namedNodeMap.getLength();
        } else {
          b1 = 0;
        } 
        for (byte b2 = 0; b2 < b1; b2++) {
          Node node1 = namedNodeMap.item(b2);
          if (node1.getNodeName().startsWith("xmlns:") || node1.getNodeName().equals("xmlns")) {
            Node node2 = param1Node;
            boolean bool = false;
            while (node2 != node) {
              NamedNodeMap namedNodeMap1 = node2.getAttributes();
              if (namedNodeMap1 != null && namedNodeMap1.getNamedItem(node1.getNodeName()) != null) {
                bool = true;
                break;
              } 
              node2 = node2.getParentNode();
            } 
            if (!bool)
              stringBuffer.append(" " + node1.getNodeName() + "=\"" + node1.getNodeValue() + "\""); 
          } 
        } 
      } 
      stringBuffer.append(">" + param1String + "</" + "fragment" + ">");
      String str = stringBuffer.toString();
      try {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(str)));
        Element element = (Element)XMLCipher.this._contextDocument.importNode(document.getDocumentElement(), true);
        documentFragment = XMLCipher.this._contextDocument.createDocumentFragment();
        for (Node node1 = element.getFirstChild(); node1 != null; node1 = element.getFirstChild()) {
          element.removeChild(node1);
          documentFragment.appendChild(node1);
        } 
      } catch (SAXException sAXException) {
        throw new XMLEncryptionException("empty", sAXException);
      } catch (ParserConfigurationException parserConfigurationException) {
        throw new XMLEncryptionException("empty", parserConfigurationException);
      } catch (IOException iOException) {
        throw new XMLEncryptionException("empty", iOException);
      } 
      return documentFragment;
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\encryption\XMLCipher.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */