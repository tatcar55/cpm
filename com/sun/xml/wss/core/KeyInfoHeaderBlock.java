/*      */ package com.sun.xml.wss.core;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*      */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.KeyName;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.MgmtData;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.PGPData;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.RetrievalMethod;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.SPKIData;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.DSAKeyValue;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.RSAKeyValue;
/*      */ import com.sun.org.apache.xml.internal.security.transforms.Transforms;
/*      */ import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
/*      */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*      */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*      */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*      */ import java.security.PublicKey;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class KeyInfoHeaderBlock
/*      */   extends SecurityHeaderBlockImpl
/*      */ {
/*      */   public static final String SignatureSpecNS = "http://www.w3.org/2000/09/xmldsig#";
/*      */   public static final String SignatureSpecNSprefix = "ds";
/*      */   public static final String TAG_KEYINFO = "KeyInfo";
/*  111 */   KeyInfo delegateKeyInfo = null;
/*      */   
/*      */   boolean dirty = false;
/*      */   
/*  115 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  124 */   String baseURI = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Document document;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyInfoHeaderBlock(Document ownerDoc) throws XWSSecurityException {
/*      */     try {
/*  137 */       this.document = ownerDoc;
/*  138 */       this.delegateKeyInfo = new KeyInfo(ownerDoc);
/*  139 */       this.dirty = true;
/*  140 */       setSOAPElement(getAsSoapElement());
/*  141 */     } catch (Exception e) {
/*  142 */       log.log(Level.SEVERE, "WSS0318.exception.while.creating.keyinfoblock", e);
/*      */ 
/*      */ 
/*      */       
/*  146 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyInfoHeaderBlock(KeyInfo keyinfo) throws XWSSecurityException {
/*  155 */     this.document = keyinfo.getDocument();
/*  156 */     this.delegateKeyInfo = keyinfo;
/*  157 */     this.dirty = true;
/*  158 */     setSOAPElement(getAsSoapElement());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addKeyName(String keynameString) {
/*  167 */     this.delegateKeyInfo.addKeyName(keynameString);
/*  168 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBinarySecret(SOAPElement binarySecret) {
/*  177 */     this.delegateKeyInfo.addUnknownElement(binarySecret);
/*  178 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addKeyName(SOAPElement keyname) throws XWSSecurityException {
/*      */     try {
/*  188 */       KeyName keynm = new KeyName(keyname, null);
/*  189 */       this.delegateKeyInfo.add(keynm);
/*  190 */       this.dirty = true;
/*  191 */     } catch (XMLSecurityException e) {
/*  192 */       log.log(Level.SEVERE, "WSS0319.exception.adding.keyname", e);
/*  193 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addKeyValue(PublicKey pk) {
/*  203 */     this.delegateKeyInfo.addKeyValue(pk);
/*  204 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addUnknownKeyValue(SOAPElement unknownKeyValueElement) {
/*  213 */     this.delegateKeyInfo.addKeyValue(unknownKeyValueElement);
/*  214 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDSAKeyValue(SOAPElement dsakeyvalue) throws XWSSecurityException {
/*      */     try {
/*  225 */       DSAKeyValue dsaKval = new DSAKeyValue(dsakeyvalue, null);
/*  226 */       this.delegateKeyInfo.add(dsaKval);
/*  227 */       this.dirty = true;
/*  228 */     } catch (XMLSecurityException e) {
/*  229 */       log.log(Level.SEVERE, "WSS0355.error.creating.keyvalue", new Object[] { "DSA", e.getMessage() });
/*      */ 
/*      */       
/*  232 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRSAKeyValue(SOAPElement rsakeyvalue) throws XWSSecurityException {
/*      */     try {
/*  244 */       RSAKeyValue rsaKval = new RSAKeyValue(rsakeyvalue, null);
/*  245 */       this.delegateKeyInfo.add(rsaKval);
/*  246 */       this.dirty = true;
/*  247 */     } catch (XMLSecurityException e) {
/*  248 */       log.log(Level.SEVERE, "WSS0355.error.creating.keyvalue", new Object[] { "RSA", e.getMessage() });
/*      */ 
/*      */       
/*  251 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addKeyValue(SOAPElement keyvalue) throws XWSSecurityException {
/*      */     try {
/*  263 */       KeyValue kval = new KeyValue(keyvalue, null);
/*  264 */       this.delegateKeyInfo.add(kval);
/*  265 */       this.dirty = true;
/*  266 */     } catch (XMLSecurityException e) {
/*  267 */       log.log(Level.SEVERE, "WSS0355.error.creating.keyvalue", new Object[] { "", e.getMessage() });
/*      */ 
/*      */       
/*  270 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMgmtData(String mgmtdata) {
/*  280 */     this.delegateKeyInfo.addMgmtData(mgmtdata);
/*  281 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMgmtData(SOAPElement mgmtdata) throws XWSSecurityException {
/*      */     try {
/*  292 */       MgmtData mgmtData = new MgmtData(mgmtdata, null);
/*  293 */       this.delegateKeyInfo.add(mgmtData);
/*  294 */       this.dirty = true;
/*  295 */     } catch (XMLSecurityException e) {
/*  296 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPGPData(SOAPElement pgpdata) throws XWSSecurityException {
/*      */     try {
/*  308 */       PGPData pgpData = new PGPData(pgpdata, null);
/*  309 */       this.delegateKeyInfo.add(pgpData);
/*  310 */       this.dirty = true;
/*  311 */     } catch (XMLSecurityException e) {
/*  312 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRetrievalMethod(String URI, Transforms transforms, String type) {
/*  325 */     this.delegateKeyInfo.addRetrievalMethod(URI, transforms, type);
/*  326 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRetrievalMethod(SOAPElement retrievalmethod) throws XWSSecurityException {
/*      */     try {
/*  337 */       RetrievalMethod rm = new RetrievalMethod(retrievalmethod, null);
/*  338 */       this.delegateKeyInfo.add(rm);
/*  339 */       this.dirty = true;
/*  340 */     } catch (XMLSecurityException e) {
/*  341 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSPKIData(SOAPElement spkidata) throws XWSSecurityException {
/*      */     try {
/*  352 */       SPKIData spki = new SPKIData(spkidata, null);
/*  353 */       this.delegateKeyInfo.add(spki);
/*  354 */       this.dirty = true;
/*  355 */     } catch (XMLSecurityException e) {
/*  356 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addX509Data(SOAPElement x509data) throws XWSSecurityException {
/*      */     try {
/*  368 */       X509Data x509Data = new X509Data(x509data, null);
/*  369 */       this.delegateKeyInfo.add(x509Data);
/*  370 */       this.dirty = true;
/*  371 */     } catch (XMLSecurityException e) {
/*  372 */       log.log(Level.SEVERE, "WSS0356.error.creating.x509data", e.getMessage());
/*  373 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addUnknownElement(SOAPElement element) {
/*  383 */     this.delegateKeyInfo.addUnknownElement(element);
/*  384 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int keyNameCount() {
/*  393 */     return this.delegateKeyInfo.lengthKeyName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int keyValueCount() {
/*  402 */     return this.delegateKeyInfo.lengthKeyValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int mgmtDataCount() {
/*  411 */     return this.delegateKeyInfo.lengthMgmtData();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int pgpDataCount() {
/*  420 */     return this.delegateKeyInfo.lengthPGPData();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int retrievalMethodCount() {
/*  429 */     return this.delegateKeyInfo.lengthRetrievalMethod();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int spkiDataCount() {
/*  438 */     return this.delegateKeyInfo.lengthSPKIData();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int x509DataCount() {
/*  447 */     return this.delegateKeyInfo.lengthX509Data();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int unknownElementCount() {
/*  456 */     return this.delegateKeyInfo.lengthUnknownElement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement getKeyName(int index) throws XWSSecurityException {
/*      */     try {
/*  469 */       return convertToSoapElement(this.delegateKeyInfo.itemKeyName(index));
/*  470 */     } catch (XMLSecurityException e) {
/*  471 */       log.log(Level.SEVERE, "WSS0320.exception.getting.keyname", e);
/*  472 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyNameString(int index) throws XWSSecurityException {
/*      */     try {
/*  486 */       return this.delegateKeyInfo.itemKeyName(index).getKeyName();
/*  487 */     } catch (XMLSecurityException e) {
/*  488 */       log.log(Level.SEVERE, "WSS0320.exception.getting.keyname", e);
/*  489 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement getKeyValueElement(int index) throws XWSSecurityException {
/*      */     try {
/*  503 */       return convertToSoapElement(this.delegateKeyInfo.itemKeyValue(index));
/*  504 */     } catch (XMLSecurityException e) {
/*  505 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyValue getKeyValue(int index) throws XWSSecurityException {
/*      */     try {
/*  519 */       return this.delegateKeyInfo.itemKeyValue(index);
/*  520 */     } catch (XMLSecurityException e) {
/*  521 */       log.log(Level.SEVERE, "WSS0357.error.getting.keyvalue", new Object[] { Integer.valueOf(index), e.getMessage() });
/*      */ 
/*      */       
/*  524 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement getMgmtData(int index) throws XWSSecurityException {
/*      */     try {
/*  538 */       return convertToSoapElement(this.delegateKeyInfo.itemMgmtData(index));
/*  539 */     } catch (XMLSecurityException e) {
/*  540 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement getPGPData(int index) throws XWSSecurityException {
/*      */     try {
/*  554 */       return convertToSoapElement(this.delegateKeyInfo.itemPGPData(index));
/*  555 */     } catch (XMLSecurityException e) {
/*  556 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement getRetrievalMethod(int index) throws XWSSecurityException {
/*      */     try {
/*  571 */       return convertToSoapElement(this.delegateKeyInfo.itemRetrievalMethod(index));
/*      */     }
/*  573 */     catch (XMLSecurityException e) {
/*  574 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement getSPKIData(int index) throws XWSSecurityException {
/*      */     try {
/*  588 */       return convertToSoapElement(this.delegateKeyInfo.itemSPKIData(index));
/*  589 */     } catch (XMLSecurityException e) {
/*  590 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement getX509DataElement(int index) throws XWSSecurityException {
/*      */     try {
/*  604 */       return convertToSoapElement(this.delegateKeyInfo.itemX509Data(index));
/*  605 */     } catch (XMLSecurityException e) {
/*  606 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Data getX509Data(int index) throws XWSSecurityException {
/*      */     try {
/*  620 */       return this.delegateKeyInfo.itemX509Data(index);
/*  621 */     } catch (XMLSecurityException e) {
/*  622 */       log.log(Level.SEVERE, "WSS0358.error.getting.x509data", new Object[] { Integer.valueOf(index), e.getMessage() });
/*      */ 
/*      */       
/*  625 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement getUnknownElement(int index) throws XWSSecurityException {
/*      */     try {
/*  641 */       Element unknownElem = this.delegateKeyInfo.itemUnknownElement(index + 1);
/*  642 */       if (unknownElem instanceof SOAPElement) {
/*  643 */         return (SOAPElement)unknownElem;
/*      */       }
/*  645 */       return (SOAPElement)this.document.importNode(unknownElem, true);
/*  646 */     } catch (Exception e) {
/*  647 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKeyName() {
/*  657 */     return this.delegateKeyInfo.containsKeyName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKeyValue() {
/*  666 */     return this.delegateKeyInfo.containsKeyValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsMgmtData() {
/*  675 */     return this.delegateKeyInfo.containsMgmtData();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsPGPData() {
/*  684 */     return this.delegateKeyInfo.containsPGPData();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsRetrievalMethod() {
/*  693 */     return this.delegateKeyInfo.containsRetrievalMethod();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsSPKIData() {
/*  702 */     return this.delegateKeyInfo.containsSPKIData();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsUnknownElement() {
/*  711 */     return this.delegateKeyInfo.containsUnknownElement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsX509Data() {
/*  720 */     return this.delegateKeyInfo.containsX509Data();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSecurityTokenReference(SecurityTokenReference reference) throws XWSSecurityException {
/*  734 */     this.delegateKeyInfo.addUnknownElement(reference.getAsSoapElement());
/*  735 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SecurityTokenReference getSecurityTokenReference(int index) throws XWSSecurityException {
/*  750 */     Element delegateElement = this.delegateKeyInfo.getElement();
/*  751 */     int res = 0;
/*  752 */     NodeList nl = delegateElement.getChildNodes();
/*      */     
/*  754 */     for (int j = 0; j < nl.getLength(); j++) {
/*  755 */       Node current = nl.item(j);
/*  756 */       if (current.getNodeType() == 1) {
/*      */         
/*  758 */         String lName = current.getLocalName();
/*  759 */         String nspac = current.getNamespaceURI();
/*      */         
/*  761 */         if (lName.equals("SecurityTokenReference") && nspac.equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")) {
/*      */ 
/*      */           
/*  764 */           if (res == index) {
/*  765 */             return new SecurityTokenReference((SOAPElement)current);
/*      */           }
/*  767 */           res++;
/*      */         } 
/*      */       } 
/*      */     } 
/*  771 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int securityTokenReferenceCount() {
/*  780 */     Element delegateElement = this.delegateKeyInfo.getElement();
/*  781 */     int res = 0;
/*  782 */     NodeList nl = delegateElement.getChildNodes();
/*      */     
/*  784 */     for (int j = 0; j < nl.getLength(); j++) {
/*  785 */       Node current = nl.item(j);
/*  786 */       if (current.getNodeType() == 1 && "SecurityTokenReference".equals(current.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(current.getNamespaceURI()))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  791 */         res++;
/*      */       }
/*      */     } 
/*  794 */     return res;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsSecurityTokenReference() {
/*  803 */     return (securityTokenReferenceCount() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEncryptedKey(EncryptedKeyToken reference) throws XWSSecurityException {
/*  813 */     this.delegateKeyInfo.addUnknownElement(reference.getAsSoapElement());
/*  814 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptedKeyToken getEncryptedKey(int index) throws XWSSecurityException {
/*  829 */     Element delegateElement = this.delegateKeyInfo.getElement();
/*  830 */     int res = 0;
/*  831 */     NodeList nl = delegateElement.getChildNodes();
/*      */     
/*  833 */     for (int j = 0; j < nl.getLength(); j++) {
/*  834 */       Node current = nl.item(j);
/*  835 */       if (current.getNodeType() == 1) {
/*      */         
/*  837 */         String lName = current.getLocalName();
/*  838 */         String nspac = current.getNamespaceURI();
/*      */         
/*  840 */         if (lName.equals("EncryptedKey") && nspac.equals("http://www.w3.org/2001/04/xmlenc#")) {
/*      */ 
/*      */           
/*  843 */           if (res == index) {
/*  844 */             return new EncryptedKeyToken((SOAPElement)current);
/*      */           }
/*  846 */           res++;
/*      */         } 
/*      */       } 
/*      */     } 
/*  850 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int encryptedKeyTokenCount() {
/*  859 */     Element delegateElement = this.delegateKeyInfo.getElement();
/*  860 */     int res = 0;
/*  861 */     NodeList nl = delegateElement.getChildNodes();
/*      */     
/*  863 */     for (int j = 0; j < nl.getLength(); j++) {
/*  864 */       Node current = nl.item(j);
/*  865 */       if (current.getNodeType() == 1 && "EncryptedKey".equals(current.getLocalName()) && "http://www.w3.org/2001/04/xmlenc#".equals(current.getNamespaceURI()))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  870 */         res++;
/*      */       }
/*      */     } 
/*  873 */     return res;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsEncryptedKeyToken() {
/*  882 */     return (encryptedKeyTokenCount() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BinarySecret getBinarySecret(int index) throws XWSSecurityException {
/*  888 */     Element delegateElement = this.delegateKeyInfo.getElement();
/*  889 */     int res = 0;
/*  890 */     NodeList nl = delegateElement.getChildNodes();
/*      */     
/*  892 */     for (int j = 0; j < nl.getLength(); j++) {
/*  893 */       Node current = nl.item(j);
/*  894 */       if (current.getNodeType() == 1) {
/*      */         
/*  896 */         String lName = current.getLocalName();
/*  897 */         String nspac = current.getNamespaceURI();
/*      */         
/*  899 */         if (lName.equals("BinarySecret") && nspac.equals("http://schemas.xmlsoap.org/ws/2005/02/trust")) {
/*      */ 
/*      */           
/*  902 */           if (res == index) {
/*      */             try {
/*  904 */               return WSTrustElementFactory.newInstance().createBinarySecret((SOAPElement)current);
/*  905 */             } catch (WSTrustException ex) {
/*  906 */               throw new XWSSecurityException(ex);
/*      */             } 
/*      */           }
/*  909 */           res++;
/*      */         } 
/*      */       } 
/*      */     } 
/*  913 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int binarySecretCount() {
/*  922 */     Element delegateElement = this.delegateKeyInfo.getElement();
/*  923 */     int res = 0;
/*  924 */     NodeList nl = delegateElement.getChildNodes();
/*      */     
/*  926 */     for (int j = 0; j < nl.getLength(); j++) {
/*  927 */       Node current = nl.item(j);
/*  928 */       if (current.getNodeType() == 1 && "BinarySecret".equals(current.getLocalName()) && "http://schemas.xmlsoap.org/ws/2005/02/trust".equals(current.getNamespaceURI()))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  933 */         res++;
/*      */       }
/*      */     } 
/*  936 */     return res;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsBinarySecret() {
/*  945 */     return (binarySecretCount() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setId(String id) {
/*  953 */     this.delegateKeyInfo.setId(id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getId() {
/*  962 */     return this.delegateKeyInfo.getId();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final KeyInfo getKeyInfo() {
/*  971 */     return this.delegateKeyInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseURI(String uri) {
/*  979 */     this.baseURI = uri;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/*  991 */     if (this.document == null) {
/*  992 */       throw new XWSSecurityException("Document not set");
/*      */     }
/*  994 */     if (this.dirty) {
/*  995 */       setSOAPElement(convertToSoapElement(this.delegateKeyInfo));
/*  996 */       this.dirty = false;
/*      */     } 
/*  998 */     return this.delegateElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocument(Document doc) {
/* 1006 */     this.document = doc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyInfoHeaderBlock(SOAPElement element) throws XWSSecurityException {
/* 1019 */     super(element);
/*      */ 
/*      */     
/*      */     try {
/* 1023 */       this.document = element.getOwnerDocument();
/* 1024 */       this.delegateKeyInfo = new KeyInfo(element, this.baseURI);
/*      */     }
/* 1026 */     catch (XMLSecurityException e) {
/* 1027 */       log.log(Level.SEVERE, "WSS0318.exception.while.creating.keyinfoblock", e);
/*      */ 
/*      */ 
/*      */       
/* 1031 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveChanges() {
/* 1044 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/* 1049 */     return SecurityHeaderBlockImpl.fromSoapElement(element, KeyInfoHeaderBlock.class);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private SOAPElement convertToSoapElement(ElementProxy proxy) throws XWSSecurityException {
/*      */     try {
/* 1056 */       Element elem = proxy.getElement();
/* 1057 */       if (elem instanceof SOAPElement) {
/* 1058 */         return (SOAPElement)elem;
/*      */       }
/* 1060 */       return (SOAPElement)this.document.importNode(elem, true);
/* 1061 */     } catch (Exception e) {
/* 1062 */       log.log(Level.SEVERE, "WSS0321.exception.converting.keyinfo.tosoapelem", e);
/*      */ 
/*      */ 
/*      */       
/* 1066 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addX509Data(X509Data x509Data) throws XWSSecurityException {
/*      */     try {
/* 1072 */       this.delegateKeyInfo.add(x509Data);
/* 1073 */       this.dirty = true;
/* 1074 */     } catch (Exception e) {
/* 1075 */       log.log(Level.SEVERE, "WSS0359.error.adding.x509data", e.getMessage());
/* 1076 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\KeyInfoHeaderBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */