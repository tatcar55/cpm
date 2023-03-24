/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.stream.buffer.AbstractCreatorProcessor;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferException;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferMark;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferProcessor;
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.PolicyBuilder;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.TokenValidator;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.X509BinarySecurityToken;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.LogStringsMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.Base64Data;
/*     */ import org.jvnet.staxex.XMLStreamReaderEx;
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
/*     */ public class X509BinarySecurityToken
/*     */   implements X509BinarySecurityToken, SecurityHeaderElement, PolicyBuilder, TokenValidator, NamespaceContextInfo, SecurityElementWriter
/*     */ {
/*  94 */   private String valueType = null;
/*  95 */   private String encodingType = null;
/*  96 */   private String id = "";
/*  97 */   private XMLStreamBuffer mark = null;
/*  98 */   private String namespaceURI = null;
/*  99 */   private String localPart = null;
/*     */   
/* 101 */   private AuthenticationTokenPolicy.X509CertificateBinding x509Policy = null;
/*     */   
/*     */   private HashMap<String, String> nsDecls;
/* 104 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt", "com.sun.xml.wss.logging.impl.opt.LogStrings");
/*     */ 
/*     */   
/* 107 */   private byte[] bstValue = null;
/* 108 */   private X509Certificate cert = null;
/*     */ 
/*     */   
/*     */   public X509BinarySecurityToken(XMLStreamReader reader, StreamReaderBufferCreator creator, HashMap<String, String> nsDecl, XMLInputFactory staxIF) throws XMLStreamException, XMLStreamBufferException {
/* 112 */     this.localPart = reader.getLocalName();
/* 113 */     this.namespaceURI = reader.getNamespaceURI();
/* 114 */     this.id = reader.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/* 115 */     this.valueType = reader.getAttributeValue(null, "ValueType");
/* 116 */     this.encodingType = reader.getAttributeValue(null, "EncodingType");
/* 117 */     this.mark = (XMLStreamBuffer)new XMLStreamBufferMark(nsDecl, (AbstractCreatorProcessor)creator);
/* 118 */     creator.createElementFragment(reader, true);
/* 119 */     this.x509Policy = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 120 */     this.x509Policy.setUUID(this.id);
/* 121 */     this.x509Policy.setValueType(this.valueType);
/* 122 */     this.x509Policy.setEncodingType(this.encodingType);
/* 123 */     this.nsDecls = nsDecl;
/* 124 */     StreamReaderBufferProcessor streamReaderBufferProcessor = this.mark.readAsXMLStreamReader();
/* 125 */     streamReaderBufferProcessor.next();
/* 126 */     digestBST((XMLStreamReader)streamReaderBufferProcessor);
/*     */   }
/*     */   
/*     */   public String getValueType() {
/* 130 */     return this.valueType;
/*     */   }
/*     */   
/*     */   public String getEncodingType() {
/* 134 */     return this.encodingType;
/*     */   }
/*     */   
/*     */   public byte[] getTokenValue() {
/* 138 */     return this.bstValue;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 142 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 146 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 150 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 154 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 158 */     return this.localPart;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 162 */     return (XMLStreamReader)this.mark.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 166 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 170 */     this.mark.writeToXMLStreamWriter(streamWriter);
/*     */   }
/*     */   
/*     */   public WSSPolicy getPolicy() {
/* 174 */     return (WSSPolicy)this.x509Policy;
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate(ProcessingContext context) throws XWSSecurityException {
/* 179 */     X509Certificate cert = getCertificate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     if (!context.getSecurityEnvironment().validateCertificate(cert, context.getExtraneousProperties()))
/*     */     {
/* 187 */       throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "Certificate validation failed", null);
/*     */     }
/*     */     
/* 190 */     context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject((FilterProcessingContext)context), cert);
/*     */   }
/*     */ 
/*     */   
/*     */   public HashMap<String, String> getInscopeNSContext() {
/* 195 */     return this.nsDecls;
/*     */   }
/*     */ 
/*     */   
/*     */   public X509Certificate getCertificate() {
/* 200 */     return this.cert;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 204 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private void digestBST(XMLStreamReader reader) throws XMLStreamException {
/* 208 */     if (reader.getEventType() == 1) {
/* 209 */       reader.next();
/*     */     }
/* 211 */     if (reader.getEventType() == 4) {
/* 212 */       if (reader instanceof XMLStreamReaderEx) {
/*     */         try {
/* 214 */           CharSequence data = ((XMLStreamReaderEx)reader).getPCDATA();
/* 215 */           if (data instanceof Base64Data) {
/* 216 */             Base64Data binaryData = (Base64Data)data;
/*     */             
/* 218 */             buildCertificate(binaryData.getInputStream());
/*     */             return;
/*     */           } 
/* 221 */         } catch (XMLStreamException ex) {
/* 222 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1603_ERROR_READING_STREAM(ex));
/* 223 */           throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1603_ERROR_READING_STREAM(ex));
/* 224 */         } catch (IOException ex) {
/* 225 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1603_ERROR_READING_STREAM(ex));
/* 226 */           throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1603_ERROR_READING_STREAM(ex));
/*     */         } 
/*     */       }
/*     */       
/*     */       try {
/* 231 */         this.bstValue = Base64.decode(StreamUtil.getCV(reader));
/* 232 */         buildCertificate(new ByteArrayInputStream(this.bstValue));
/*     */       }
/* 234 */       catch (Base64DecodingException ex) {
/* 235 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1604_ERROR_DECODING_BASE_64_DATA(ex));
/* 236 */         throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1604_ERROR_DECODING_BASE_64_DATA(ex));
/* 237 */       } catch (XMLStreamException ex) {
/* 238 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1604_ERROR_DECODING_BASE_64_DATA(ex));
/* 239 */         throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1604_ERROR_DECODING_BASE_64_DATA(ex));
/*     */       } 
/*     */     } else {
/* 242 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1603_ERROR_READING_STREAM(null));
/* 243 */       throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1603_ERROR_READING_STREAM(null));
/*     */     } 
/*     */     
/* 246 */     if (reader.getEventType() != 2) {
/* 247 */       reader.next();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildCertificate(InputStream certValue) {
/*     */     try {
/* 255 */       CertificateFactory certFact = CertificateFactory.getInstance("X.509");
/* 256 */       this.cert = (X509Certificate)certFact.generateCertificate(certValue);
/* 257 */     } catch (CertificateException ex) {
/* 258 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1605_ERROR_GENERATING_CERTIFICATE(ex));
/* 259 */       throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1605_ERROR_GENERATING_CERTIFICATE(ex));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\X509BinarySecurityToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */