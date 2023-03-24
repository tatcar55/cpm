/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.security.core.ai.IdentityType;
/*     */ import com.sun.xml.security.core.ai.ObjectFactory;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.server.EndpointReferenceExtensionContributor;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.CertificateRetriever;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.secext10.BinarySecurityTokenType;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Result;
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
/*     */ public class IdentityEPRExtnContributor
/*     */   extends EndpointReferenceExtensionContributor
/*     */ {
/*  86 */   private Certificate cs = null;
/*  87 */   QName ID_QNAME = null;
/*  88 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getSPI(@NotNull Class<T> spiType) {
/*  97 */     if (spiType.isAssignableFrom(EndpointReferenceExtensionContributor.class)) {
/*  98 */       return (T)new IdentityEPRExtnContributor();
/*     */     }
/*     */     
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public WSEndpointReference.EPRExtension getEPRExtension(WSEndpoint wse, WSEndpointReference.EPRExtension extension) {
/* 106 */     if (extension != null) {
/* 107 */       return extension;
/*     */     }
/* 109 */     QName eprQName = new QName("http://schemas.sun.com/2006/03/wss/server", "EnableEPRIdentity");
/* 110 */     CertificateRetriever cr = new CertificateRetriever();
/* 111 */     boolean found = cr.checkforEPRIdentity(wse, eprQName);
/* 112 */     if (!found) {
/* 113 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 117 */       URL url = SecurityUtil.loadFromClasspath("META-INF/ServerCertificate.cert");
/* 118 */       if (url != null) {
/* 119 */         CertificateFactory certFact = CertificateFactory.getInstance("X.509");
/* 120 */         InputStream is = url.openStream();
/* 121 */         this.cs = certFact.generateCertificate(is);
/* 122 */         is.close();
/*     */       } else {
/* 124 */         this.cs = cr.getServerKeyStore(wse);
/* 125 */         if (this.cs == null) {
/* 126 */           return null;
/*     */         }
/*     */       } 
/* 129 */     } catch (CertificateException ex) {
/* 130 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/*     */       
/* 132 */       throw new RuntimeException(ex);
/* 133 */     } catch (IOException ex) {
/* 134 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 135 */       throw new RuntimeException(ex);
/* 136 */     } catch (XWSSecurityException ex) {
/* 137 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), (Throwable)ex);
/* 138 */       throw new RuntimeException(ex);
/*     */     } 
/*     */ 
/*     */     
/* 142 */     return new WSEndpointReference.EPRExtension()
/*     */       {
/*     */         public XMLStreamReader readAsXMLStreamReader() throws XMLStreamException {
/* 145 */           XMLStreamReader reader = null;
/*     */           
/*     */           try {
/* 148 */             String id = PolicyUtil.randomUUID();
/* 149 */             BinarySecurityTokenType bst = new BinarySecurityTokenType();
/* 150 */             bst.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
/* 151 */             bst.setId(id);
/* 152 */             bst.setEncodingType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
/* 153 */             if (IdentityEPRExtnContributor.this.cs != null) {
/* 154 */               bst.setValue(IdentityEPRExtnContributor.this.cs.getEncoded());
/*     */             }
/* 156 */             JAXBElement<BinarySecurityTokenType> bstElem = (new ObjectFactory()).createBinarySecurityToken(bst);
/* 157 */             IdentityType identityElement = new IdentityType();
/* 158 */             identityElement.getDnsOrSpnOrUpn().add(bstElem);
/*     */             
/* 160 */             reader = IdentityEPRExtnContributor.this.readHeader(identityElement);
/*     */           }
/* 162 */           catch (CertificateEncodingException ex) {
/* 163 */             IdentityEPRExtnContributor.log.log(Level.SEVERE, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 164 */             throw new RuntimeException(ex);
/*     */           } 
/* 166 */           return reader;
/*     */         }
/*     */         
/*     */         public QName getQName() {
/* 170 */           return new QName("http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", "Identity");
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public QName getQName() {
/* 176 */     return this.ID_QNAME;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader(IdentityType identityElem) throws XMLStreamException {
/* 180 */     XMLStreamBufferResult xbr = new XMLStreamBufferResult();
/* 181 */     JAXBElement<IdentityType> idElem = (new ObjectFactory()).createIdentity(identityElem);
/*     */     
/*     */     try {
/* 184 */       JAXBContext context = JAXBUtil.getCustomIdentityJAXBContext();
/* 185 */       Marshaller m = context.createMarshaller();
/* 186 */       m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.valueOf(false));
/* 187 */       m.marshal(idElem, (Result)xbr);
/* 188 */     } catch (JAXBException je) {
/* 189 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), je);
/* 190 */       throw new XMLStreamException(je);
/*     */     } 
/* 192 */     return (XMLStreamReader)xbr.getXMLStreamBuffer().readAsXMLStreamReader();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\IdentityEPRExtnContributor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */