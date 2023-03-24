/*     */ package com.sun.xml.ws.security.trust.impl.elements.str;
/*     */ 
/*     */ import com.sun.xml.ws.security.secext10.KeyIdentifierType;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.ReferenceType;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.elements.str.Reference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import java.util.List;
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
/*     */ public class SecurityTokenReferenceImpl
/*     */   extends SecurityTokenReferenceType
/*     */   implements SecurityTokenReference
/*     */ {
/*     */   public SecurityTokenReferenceImpl(Reference ref) {
/*  72 */     setReference(ref);
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityTokenReferenceImpl(SecurityTokenReferenceType strType) {
/*  77 */     Reference ref = getReference(strType);
/*  78 */     setReference(ref);
/*  79 */     getOtherAttributes().putAll(strType.getOtherAttributes());
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setReference(Reference ref) {
/*  84 */     JAXBElement rElement = null;
/*  85 */     String type = ref.getType();
/*  86 */     ObjectFactory objFac = new ObjectFactory();
/*  87 */     if ("KeyIdentifier".equals(type)) {
/*  88 */       rElement = objFac.createKeyIdentifier((KeyIdentifierType)ref);
/*     */     }
/*  90 */     else if ("Reference".equals(type)) {
/*  91 */       rElement = objFac.createReference((ReferenceType)ref);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (rElement != null) {
/*  97 */       getAny().clear();
/*  98 */       getAny().add(rElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Reference getReference() {
/* 103 */     return getReference(this);
/*     */   }
/*     */   
/*     */   private Reference getReference(SecurityTokenReferenceType strType) {
/* 107 */     List<Object> list = strType.getAny();
/* 108 */     JAXBElement<ReferenceType> obj = (JAXBElement)list.get(0);
/* 109 */     String local = obj.getName().getLocalPart();
/*     */     
/* 111 */     if ("Reference".equals(local)) {
/* 112 */       return (Reference)new DirectReferenceImpl(obj.getValue());
/*     */     }
/*     */     
/* 115 */     if ("KeyIdentifier".equalsIgnoreCase(local)) {
/* 116 */       return (Reference)new KeyIdentifierImpl((KeyIdentifierType)obj.getValue());
/*     */     }
/*     */ 
/*     */     
/* 120 */     return null;
/*     */   }
/*     */   
/*     */   public void setTokenType(String tokenType) {
/* 124 */     getOtherAttributes().put(TOKEN_TYPE, tokenType);
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/* 128 */     return (String)getOtherAttributes().get(TOKEN_TYPE);
/*     */   }
/*     */   
/*     */   public String getType() {
/* 132 */     return "SecurityTokenReference";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/*     */     try {
/* 137 */       DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 138 */       dbf.setNamespaceAware(true);
/* 139 */       DocumentBuilder builder = dbf.newDocumentBuilder();
/* 140 */       Document doc = builder.newDocument();
/*     */       
/* 142 */       Marshaller marshaller = WSTrustElementFactory.getContext().createMarshaller();
/* 143 */       JAXBElement<SecurityTokenReferenceType> rstElement = (new ObjectFactory()).createSecurityTokenReference(this);
/* 144 */       marshaller.marshal(rstElement, doc);
/* 145 */       return doc.getDocumentElement();
/*     */     }
/* 147 */     catch (Exception ex) {
/* 148 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\str\SecurityTokenReferenceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */