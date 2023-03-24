/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EncryptedTypeHeaderBlock
/*     */   extends SecurityHeaderBlockImpl
/*     */ {
/*     */   SOAPElement encryptionMethod;
/*     */   KeyInfoHeaderBlock keyInfo;
/*     */   SOAPElement cipherData;
/*     */   SOAPElement encryptionProperties;
/*     */   boolean updateRequired = false;
/*  96 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 105 */     String id = getAttribute("Id");
/* 106 */     if (id.equals(""))
/* 107 */       return null; 
/* 108 */     return id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 112 */     setAttribute("Id", id);
/* 113 */     setIdAttribute("Id", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 120 */     String type = getAttribute("Type");
/* 121 */     if (type.equals(""))
/* 122 */       return null; 
/* 123 */     return type;
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/* 127 */     setAttribute("Type", type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/* 134 */     String mimeType = getAttribute("MimeType");
/* 135 */     if (mimeType.equals(""))
/* 136 */       return null; 
/* 137 */     return mimeType;
/*     */   }
/*     */   
/*     */   public void setMimeType(String mimeType) {
/* 141 */     setAttribute("MimeType", mimeType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 148 */     String encoding = getAttribute("Encoding");
/* 149 */     if (encoding.equals(""))
/* 150 */       return null; 
/* 151 */     return encoding;
/*     */   }
/*     */   
/*     */   public void setEncoding(String encoding) {
/* 155 */     setAttribute("Encoding", encoding);
/*     */   }
/*     */   
/*     */   public SOAPElement getEncryptionMethod() {
/* 159 */     return this.encryptionMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncryptionMethodURI() {
/* 166 */     if (this.encryptionMethod == null)
/* 167 */       return null; 
/* 168 */     return this.encryptionMethod.getAttribute("Algorithm");
/*     */   }
/*     */   
/*     */   public void setEncryptionMethod(SOAPElement encryptionMethod) {
/* 172 */     this.encryptionMethod = encryptionMethod;
/* 173 */     this.updateRequired = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncryptionMethod(String algorithmURI) throws XWSSecurityException {
/*     */     try {
/* 180 */       this.encryptionMethod = getSoapFactory().createElement("EncryptionMethod", "xenc", "http://www.w3.org/2001/04/xmlenc#");
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 185 */     catch (SOAPException e) {
/* 186 */       log.log(Level.SEVERE, "WSS0351.error.setting.encryption.method", e.getMessage());
/* 187 */       throw new XWSSecurityException(e);
/*     */     } 
/* 189 */     this.encryptionMethod.setAttribute("Algorithm", algorithmURI);
/* 190 */     this.updateRequired = true;
/*     */   }
/*     */   
/*     */   public KeyInfoHeaderBlock getKeyInfo() {
/* 194 */     return this.keyInfo;
/*     */   }
/*     */   
/*     */   public void setKeyInfo(KeyInfoHeaderBlock keyInfo) {
/* 198 */     this.keyInfo = keyInfo;
/* 199 */     this.updateRequired = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCipherValue() throws XWSSecurityException {
/* 209 */     if (this.cipherData == null) {
/* 210 */       log.log(Level.SEVERE, "WSS0347.missing.cipher.data");
/* 211 */       throw new XWSSecurityException("Cipher data has not been set");
/*     */     } 
/*     */     
/* 214 */     Iterator<SOAPElement> cipherValues = null;
/*     */     try {
/* 216 */       cipherValues = this.cipherData.getChildElements(getSoapFactory().createName("CipherValue", "xenc", "http://www.w3.org/2001/04/xmlenc#"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 222 */     catch (SOAPException e) {
/* 223 */       log.log(Level.SEVERE, "WSS0352.error.getting.cipherValue", e.getMessage());
/* 224 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */     
/* 227 */     if (!cipherValues.hasNext()) {
/* 228 */       log.log(Level.SEVERE, "WSS0353.missing.cipherValue");
/* 229 */       throw new XWSSecurityException("Cipher Value not present");
/*     */     } 
/*     */     
/* 232 */     return getFullTextChildrenFromElement(cipherValues.next());
/*     */   }
/*     */   
/*     */   public SOAPElement getCipherData(boolean create) throws XWSSecurityException {
/* 236 */     if (this.cipherData == null && create) {
/*     */       try {
/* 238 */         this.cipherData = getSoapFactory().createElement("CipherData", "xenc", "http://www.w3.org/2001/04/xmlenc#");
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 243 */       catch (SOAPException e) {
/* 244 */         log.log(Level.SEVERE, "WSS0395.creating.cipherData");
/* 245 */         throw new XWSSecurityException(e);
/*     */       } 
/*     */     }
/*     */     
/* 249 */     return this.cipherData;
/*     */   }
/*     */   
/*     */   public SOAPElement getCipherReference(boolean create, String uri) throws XWSSecurityException {
/* 253 */     SOAPElement cipherReference = null;
/* 254 */     if (create) {
/*     */       try {
/* 256 */         cipherReference = getSoapFactory().createElement("CipherReference", "xenc", "http://www.w3.org/2001/04/xmlenc#");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 262 */         cipherReference.setAttribute("URI", uri);
/*     */         
/* 264 */         getCipherData(create).addChildElement(cipherReference);
/* 265 */       } catch (SOAPException e) {
/*     */         
/* 267 */         throw new XWSSecurityException(e);
/*     */       } 
/*     */     } else {
/* 270 */       if (this.cipherData == null)
/*     */       {
/* 272 */         throw new XWSSecurityException("CipherData is not present");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 278 */       NodeList nl = this.cipherData.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherReference");
/* 279 */       if (nl.getLength() > 0) return (SOAPElement)nl.item(0);
/*     */     
/*     */     } 
/* 282 */     return cipherReference;
/*     */   }
/*     */   
/*     */   public void addTransform(String algorithmURI) throws XWSSecurityException {
/* 286 */     SOAPElement cipherReference = getCipherReference(false, (String)null);
/*     */     
/*     */     try {
/* 289 */       SOAPElement dsTransform = getSoapFactory().createElement("Transform", "ds", "http://www.w3.org/2000/09/xmldsig#");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 295 */       dsTransform.setAttribute("Algorithm", algorithmURI);
/*     */ 
/*     */       
/* 298 */       SOAPElement xencTransforms = null;
/* 299 */       Iterator<SOAPElement> i = cipherReference.getChildElements();
/* 300 */       if (i == null || !i.hasNext()) {
/* 301 */         xencTransforms = getSoapFactory().createElement("Transforms", "xenc", "http://www.w3.org/2001/04/xmlenc#");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 307 */         xencTransforms = cipherReference.addChildElement(xencTransforms);
/*     */       } else {
/* 309 */         xencTransforms = i.next();
/*     */       } 
/* 311 */       xencTransforms.addChildElement(dsTransform);
/*     */     }
/* 313 */     catch (SOAPException e) {
/*     */       
/* 315 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Iterator getTransforms() throws XWSSecurityException {
/* 320 */     SOAPElement cr = getCipherReference(false, (String)null);
/* 321 */     if (cr != null) {
/* 322 */       Iterator<SOAPElement> it = cr.getChildElements();
/* 323 */       if (it.hasNext()) {
/* 324 */         SOAPElement transforms = it.next();
/* 325 */         return transforms.getChildElements();
/*     */       } 
/*     */     } 
/* 328 */     return null;
/*     */   }
/*     */   
/*     */   public SOAPElement getEncryptionProperties() {
/* 332 */     return this.encryptionProperties;
/*     */   }
/*     */   
/*     */   public void setEncryptionProperties(SOAPElement encryptionProperties) {
/* 336 */     this.encryptionProperties = encryptionProperties;
/* 337 */     this.updateRequired = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveChanges() {
/* 348 */     this.updateRequired = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeEncryptedType(SOAPElement element) throws XWSSecurityException {
/*     */     try {
/* 354 */       Iterator<Node> cnodes = element.getChildElements();
/* 355 */       while (cnodes.hasNext()) {
/* 356 */         Node se = cnodes.next();
/*     */         
/* 358 */         for (; cnodes.hasNext() && se.getNodeType() != 1; se = cnodes.next());
/*     */         
/* 360 */         if (se == null || se.getNodeType() != 1)
/*     */           break; 
/* 362 */         if (((SOAPElement)se).getLocalName().equals("EncryptionMethod")) {
/* 363 */           this.encryptionMethod = (SOAPElement)se; continue;
/*     */         } 
/* 365 */         if (((SOAPElement)se).getLocalName().equals("CipherData")) {
/* 366 */           this.cipherData = (SOAPElement)se; continue;
/*     */         } 
/* 368 */         if (((SOAPElement)se).getLocalName().equals("KeyInfo")) {
/* 369 */           this.keyInfo = new KeyInfoHeaderBlock(new KeyInfo((Element)se, null));
/*     */         }
/*     */       }
/*     */     
/* 373 */     } catch (Exception e) {
/* 374 */       log.log(Level.SEVERE, "WSS0354.error.initializing.encryptedType", e.getMessage());
/* 375 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getFullTextChildrenFromElement(Element element) {
/* 445 */     StringBuffer sb = new StringBuffer();
/* 446 */     NodeList children = element.getChildNodes();
/* 447 */     int iMax = children.getLength();
/*     */     
/* 449 */     for (int i = 0; i < iMax; i++) {
/* 450 */       Node curr = children.item(i);
/*     */       
/* 452 */       if (curr.getNodeType() == 3) {
/* 453 */         sb.append(((Text)curr).getData());
/*     */       }
/*     */     } 
/*     */     
/* 457 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\EncryptedTypeHeaderBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */