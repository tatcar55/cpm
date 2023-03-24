/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.stax.StreamWriterBufferCreator;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.JAXBEncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.SAMLAssertion;
/*     */ import com.sun.xml.ws.security.opt.impl.message.GSHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.DirectReference;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.PrivateKeyBinding;
/*     */ import com.sun.xml.wss.logging.impl.opt.token.LogStringsMessages;
/*     */ import java.security.Key;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class SamlTokenBuilder
/*     */   extends TokenBuilder
/*     */ {
/*  76 */   private AuthenticationTokenPolicy.SAMLAssertionBinding keyBinding = null;
/*     */   private boolean forSign = false;
/*     */   private String id;
/*     */   private MutableXMLStreamBuffer buffer;
/*     */   private XMLStreamReader reader;
/*     */   
/*     */   public SamlTokenBuilder(JAXBFilterProcessingContext context, AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding, boolean forSign) {
/*  83 */     super(context);
/*  84 */     this.forSign = forSign;
/*  85 */     this.keyBinding = samlBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderResult process() throws XWSSecurityException {
/*     */     GSHeaderElement gSHeaderElement;
/*     */     SecurityHeaderElement securityHeaderElement1;
/*  94 */     BuilderResult result = new BuilderResult();
/*  95 */     String assertionId = null;
/*     */     
/*  97 */     SecurityHeaderElement she = null;
/*     */     
/*  99 */     Element samlAssertion = this.keyBinding.getAssertion();
/* 100 */     if (samlAssertion == null) {
/* 101 */       this.reader = this.keyBinding.getAssertionReader();
/* 102 */       if (this.reader != null) {
/*     */         try {
/* 104 */           this.reader.next();
/* 105 */           this.id = this.reader.getAttributeValue(null, "AssertionID");
/* 106 */           if (this.id == null) {
/* 107 */             this.id = this.reader.getAttributeValue(null, "ID");
/*     */           }
/*     */           
/* 110 */           this.buffer = new MutableXMLStreamBuffer();
/* 111 */           StreamWriterBufferCreator bCreator = new StreamWriterBufferCreator(this.buffer);
/* 112 */           StreamWriterBufferCreator streamWriterBufferCreator1 = bCreator;
/* 113 */           while (8 != this.reader.getEventType()) {
/* 114 */             StreamUtil.writeCurrentEvent(this.reader, (XMLStreamWriter)streamWriterBufferCreator1);
/* 115 */             this.reader.next();
/*     */           } 
/* 117 */         } catch (XMLStreamException ex) {
/* 118 */           throw new XWSSecurityException(ex);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 123 */     if (samlAssertion != null) {
/* 124 */       gSHeaderElement = new GSHeaderElement(samlAssertion);
/* 125 */     } else if (this.reader != null) {
/* 126 */       gSHeaderElement = new GSHeaderElement((XMLStreamBuffer)this.buffer);
/* 127 */       gSHeaderElement.setId(this.id);
/*     */     } 
/* 129 */     JAXBEncryptedKey ek = null;
/* 130 */     String asID = "";
/* 131 */     String id = "";
/* 132 */     String keyEncAlgo = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
/* 133 */     Key samlkey = null;
/* 134 */     if (samlAssertion != null) {
/* 135 */       asID = samlAssertion.getAttributeNS(null, "AssertionID");
/* 136 */       if (gSHeaderElement == null) {
/* 137 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1811_NULL_SAML_ASSERTION());
/* 138 */         throw new XWSSecurityException("SAML Assertion is NULL");
/*     */       } 
/* 140 */       if (asID == null || asID.length() == 0) {
/* 141 */         id = samlAssertion.getAttributeNS(null, "ID");
/* 142 */         gSHeaderElement.setId(id);
/*     */       } else {
/* 144 */         gSHeaderElement.setId(asID);
/*     */       } 
/*     */     } else {
/* 147 */       if (gSHeaderElement == null) {
/* 148 */         securityHeaderElement1 = (SecurityHeaderElement)this.context.getExtraneousProperty("incoming_saml_assertion");
/*     */       }
/* 150 */       if (securityHeaderElement1 == null) {
/* 151 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1811_NULL_SAML_ASSERTION());
/* 152 */         throw new XWSSecurityException("SAML Assertion is NULL");
/*     */       } 
/* 154 */       id = asID = securityHeaderElement1.getId();
/*     */     } 
/* 156 */     if (logger.isLoggable(Level.FINEST)) {
/* 157 */       logger.log(Level.FINEST, "SAML Assertion id:" + asID);
/*     */     }
/*     */     
/* 160 */     Key dataProtectionKey = null;
/* 161 */     if (this.forSign) {
/* 162 */       PrivateKeyBinding privKBinding = (PrivateKeyBinding)this.keyBinding.getKeyBinding();
/* 163 */       dataProtectionKey = privKBinding.getPrivateKey();
/* 164 */       if (dataProtectionKey == null) {
/* 165 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1810_NULL_PRIVATEKEY_SAML());
/* 166 */         throw new XWSSecurityException("PrivateKey null inside PrivateKeyBinding set for SAML Policy ");
/*     */       } 
/*     */       
/* 169 */       if (this.context.getSecurityHeader().getChildElement(securityHeaderElement1.getId()) == null) {
/* 170 */         this.context.getSecurityHeader().add(securityHeaderElement1);
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 176 */       if (securityHeaderElement1 == null) {
/* 177 */         SecurityHeaderElement assertion = (SecurityHeaderElement)this.context.getExtraneousProperty("incoming_saml_assertion");
/* 178 */         samlkey = ((SAMLAssertion)assertion).getKey();
/*     */       } else {
/* 180 */         samlkey = ((SAMLAssertion)securityHeaderElement1).getKey();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 190 */       if (!"".equals(this.keyBinding.getKeyAlgorithm())) {
/* 191 */         keyEncAlgo = this.keyBinding.getKeyAlgorithm();
/*     */       }
/* 193 */       String dataEncAlgo = SecurityUtil.getDataEncryptionAlgo(this.context);
/* 194 */       dataProtectionKey = SecurityUtil.generateSymmetricKey(dataEncAlgo);
/*     */     } 
/*     */     
/* 197 */     Element authorityBinding = this.keyBinding.getAuthorityBinding();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     String referenceType = this.keyBinding.getReferenceType();
/* 203 */     if (referenceType.equals("Embedded")) {
/* 204 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1813_UNSUPPORTED_EMBEDDEDREFERENCETYPE_SAML());
/* 205 */       throw new XWSSecurityException("Embedded Reference Type for SAML Assertions not supported yet");
/*     */     } 
/*     */     
/* 208 */     assertionId = securityHeaderElement1.getId();
/*     */ 
/*     */     
/* 211 */     SecurityTokenReference samlSTR = null;
/* 212 */     if (authorityBinding == null) {
/* 213 */       KeyIdentifier keyIdentifier = new KeyIdentifier(this.context.getSOAPVersion());
/* 214 */       keyIdentifier.setValue(assertionId);
/* 215 */       if ("urn:oasis:names:tc:SAML:2.0:assertion".equals(securityHeaderElement1.getNamespaceURI())) {
/* 216 */         keyIdentifier.setValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
/*     */       } else {
/* 218 */         keyIdentifier.setValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
/*     */       } 
/* 220 */       samlSTR = this.elementFactory.createSecurityTokenReference((Reference)keyIdentifier);
/* 221 */       if (id != null) {
/* 222 */         samlSTR.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
/*     */       } else {
/* 224 */         samlSTR.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
/*     */       } 
/*     */       
/* 227 */       ((NamespaceContextEx)this.context.getNamespaceContext()).addWSS11NS();
/* 228 */       buildKeyInfo(samlSTR);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     if (!this.forSign) {
/* 235 */       HashMap ekCache = this.context.getEncryptedKeyCache();
/* 236 */       ek = (JAXBEncryptedKey)this.elementFactory.createEncryptedKey(this.context.generateID(), keyEncAlgo, this.keyInfo, samlkey, dataProtectionKey);
/* 237 */       this.context.getSecurityHeader().add((SecurityHeaderElement)ek);
/* 238 */       String ekId = ek.getId();
/* 239 */       DirectReference dr = buildDirectReference(ekId, "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
/* 240 */       result.setKeyInfo(buildKeyInfo((Reference)dr, ""));
/*     */     } else {
/* 242 */       result.setKeyInfo(this.keyInfo);
/*     */     } 
/*     */     
/* 245 */     HashMap<Object, Object> sentSamlKeys = (HashMap)this.context.getExtraneousProperty("stored_saml_keys");
/* 246 */     if (sentSamlKeys == null)
/* 247 */       sentSamlKeys = new HashMap<Object, Object>(); 
/* 248 */     sentSamlKeys.put(assertionId, dataProtectionKey);
/* 249 */     this.context.setExtraneousProperty("stored_saml_keys", sentSamlKeys);
/*     */     
/* 251 */     result.setDataProtectionKey(dataProtectionKey);
/*     */     
/* 253 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\SamlTokenBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */