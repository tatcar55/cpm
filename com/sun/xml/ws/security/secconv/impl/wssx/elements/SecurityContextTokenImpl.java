/*     */ package com.sun.xml.ws.security.secconv.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.secconv.impl.wssx.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secconv.impl.wssx.bindings.SecurityContextTokenType;
/*     */ import com.sun.xml.ws.security.secconv.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Document;
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
/*     */ public class SecurityContextTokenImpl
/*     */   extends SecurityContextTokenType
/*     */   implements SecurityContextToken
/*     */ {
/*  74 */   private String instance = null;
/*  75 */   private URI identifier = null;
/*  76 */   private List<Object> extElements = null;
/*     */   
/*  78 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.secconv", "com.sun.xml.ws.security.secconv.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityContextTokenImpl() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityContextTokenImpl(URI identifier, String instance, String wsuId) {
/*  88 */     if (identifier != null) {
/*  89 */       setIdentifier(identifier);
/*     */     }
/*  91 */     if (instance != null) {
/*  92 */       setInstance(instance);
/*     */     }
/*     */     
/*  95 */     if (wsuId != null) {
/*  96 */       setWsuId(wsuId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityContextTokenImpl(SecurityContextTokenType sTokenType) {
/* 102 */     List<Object> list = sTokenType.getAny();
/* 103 */     for (int i = 0; i < list.size(); i++) {
/* 104 */       Object object = list.get(i);
/* 105 */       if (object instanceof JAXBElement) {
/* 106 */         JAXBElement<String> obj = (JAXBElement)object;
/*     */         
/* 108 */         String local = obj.getName().getLocalPart();
/* 109 */         if (local.equalsIgnoreCase("Instance")) {
/* 110 */           setInstance(obj.getValue());
/* 111 */         } else if (local.equalsIgnoreCase("Identifier")) {
/* 112 */           setIdentifier(URI.create(obj.getValue()));
/*     */         } 
/*     */       } else {
/* 115 */         getAny().add(object);
/* 116 */         if (this.extElements == null) {
/* 117 */           this.extElements = new ArrayList();
/* 118 */           this.extElements.add(object);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     setWsuId(sTokenType.getId());
/*     */   }
/*     */   
/*     */   public URI getIdentifier() {
/* 127 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public final void setIdentifier(URI identifier) {
/* 131 */     this.identifier = identifier;
/* 132 */     JAXBElement<String> iElement = (new ObjectFactory()).createIdentifier(identifier.toString());
/*     */     
/* 134 */     getAny().add(iElement);
/* 135 */     if (log.isLoggable(Level.FINE)) {
/* 136 */       log.log(Level.FINE, LogStringsMessages.WSSC_1004_SECCTX_TOKEN_ID_VALUE(identifier.toString()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInstance() {
/* 142 */     return this.instance;
/*     */   }
/*     */   
/*     */   public final void setInstance(String instance) {
/* 146 */     this.instance = instance;
/* 147 */     JAXBElement<String> iElement = (new ObjectFactory()).createInstance(instance);
/*     */     
/* 149 */     getAny().add(iElement);
/*     */   }
/*     */   
/*     */   public final void setWsuId(String wsuId) {
/* 153 */     setId(wsuId);
/* 154 */     if (log.isLoggable(Level.FINE)) {
/* 155 */       log.log(Level.FINE, LogStringsMessages.WSSC_1005_SECCTX_TOKEN_WSUID_VALUE(wsuId));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWsuId() {
/* 161 */     return getId();
/*     */   }
/*     */   
/*     */   public String getType() {
/* 165 */     return "SecurityContextToken";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/*     */     try {
/* 170 */       DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 171 */       dbf.setNamespaceAware(true);
/* 172 */       DocumentBuilder builder = dbf.newDocumentBuilder();
/* 173 */       Document doc = builder.newDocument();
/*     */       
/* 175 */       Marshaller marshaller = WSTrustElementFactory.getContext().createMarshaller();
/* 176 */       JAXBElement<SecurityContextTokenType> tElement = (new ObjectFactory()).createSecurityContextToken(this);
/* 177 */       marshaller.marshal(tElement, doc);
/* 178 */       return doc.getDocumentElement();
/*     */     }
/* 180 */     catch (Exception ex) {
/* 181 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0019_ERR_TOKEN_VALUE(), ex);
/*     */       
/* 183 */       throw new RuntimeException(LogStringsMessages.WSSC_0019_ERR_TOKEN_VALUE(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getExtElements() {
/* 188 */     return this.extElements;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\impl\wssx\elements\SecurityContextTokenImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */