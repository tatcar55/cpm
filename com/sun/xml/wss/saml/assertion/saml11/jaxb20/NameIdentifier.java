/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.NameIdentifier;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.NameIdentifierType;
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
/*     */ 
/*     */ public class NameIdentifier
/*     */   extends NameIdentifierType
/*     */   implements NameIdentifier
/*     */ {
/*  63 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameIdentifierType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  81 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  83 */       Unmarshaller u = jc.createUnmarshaller();
/*  84 */       return (NameIdentifierType)u.unmarshal(element);
/*  85 */     } catch (Exception ex) {
/*  86 */       throw new SAMLException(ex.getMessage());
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
/* 104 */     if (name != null) {
/* 105 */       setValue(name);
/*     */     }
/* 107 */     if (nameQualifier != null) {
/* 108 */       setNameQualifier(nameQualifier);
/*     */     }
/* 110 */     if (format != null)
/* 111 */       setFormat(format); 
/*     */   }
/*     */   
/*     */   public NameIdentifier(NameIdentifierType nameIdType) {
/* 115 */     setValue(nameIdType.getValue());
/* 116 */     setNameQualifier(nameIdType.getNameQualifier());
/* 117 */     setFormat(nameIdType.getFormat());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 122 */     return super.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormat() {
/* 127 */     return super.getFormat();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameQualifier() {
/* 132 */     return super.getNameQualifier();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\NameIdentifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */