/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.wss.saml.NameIdentifier;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.NameIdentifierType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.NameIdentifierImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.NameIdentifierTypeImpl;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameIdentifier
/*     */   extends NameIdentifierImpl
/*     */   implements NameIdentifier
/*     */ {
/*  64 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameIdentifierTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/*  82 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  84 */       Unmarshaller u = jc.createUnmarshaller();
/*  85 */       return (NameIdentifierTypeImpl)u.unmarshal(element);
/*  86 */     } catch (Exception ex) {
/*  87 */       throw new SAMLException(ex.getMessage());
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
/*     */   public NameIdentifier(String name, String nameQualifier, String format) {
/* 105 */     if (name != null) {
/* 106 */       setValue(name);
/*     */     }
/* 108 */     if (nameQualifier != null) {
/* 109 */       setNameQualifier(nameQualifier);
/*     */     }
/* 111 */     if (format != null)
/* 112 */       setFormat(format); 
/*     */   }
/*     */   
/*     */   public NameIdentifier(NameIdentifierType nameIdType) {
/* 116 */     setValue(nameIdType.getValue());
/* 117 */     setNameQualifier(nameIdType.getNameQualifier());
/* 118 */     setFormat(nameIdType.getFormat());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 123 */     return super.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormat() {
/* 128 */     return super.getFormat();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameQualifier() {
/* 133 */     return super.getNameQualifier();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\NameIdentifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */