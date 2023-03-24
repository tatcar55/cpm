/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.NameID;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.NameIDType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
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
/*     */ public class NameID
/*     */   extends NameIDType
/*     */   implements NameID
/*     */ {
/*  62 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameIDType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  80 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/*  82 */       Unmarshaller u = jc.createUnmarshaller();
/*  83 */       return (NameIDType)u.unmarshal(element);
/*  84 */     } catch (Exception ex) {
/*  85 */       throw new SAMLException(ex.getMessage());
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
/*     */   public NameID(String name, String nameQualifier, String format) {
/* 103 */     if (name != null) {
/* 104 */       setValue(name);
/*     */     }
/* 106 */     if (nameQualifier != null) {
/* 107 */       setNameQualifier(nameQualifier);
/*     */     }
/* 109 */     if (format != null)
/* 110 */       setFormat(format); 
/*     */   }
/*     */   
/*     */   public NameID(NameIDType nameIdType) {
/* 114 */     setValue(nameIdType.getValue());
/* 115 */     setNameQualifier(nameIdType.getNameQualifier());
/* 116 */     setFormat(nameIdType.getFormat());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\NameID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */