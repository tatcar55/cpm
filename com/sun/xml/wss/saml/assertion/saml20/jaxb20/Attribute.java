/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Attribute;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AttributeType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
/*     */ import java.util.List;
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
/*     */ public class Attribute
/*     */   extends AttributeType
/*     */   implements Attribute
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
/*     */   public static AttributeType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  75 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/*  77 */       Unmarshaller u = jc.createUnmarshaller();
/*  78 */       return (AttributeType)u.unmarshal(element);
/*  79 */     } catch (Exception ex) {
/*  80 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setAttributeValue(List values) {
/*  85 */     this.attributeValue = values;
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
/*     */   public Attribute(String name, List values) {
/* 101 */     setName(name);
/* 102 */     setAttributeValue(values);
/*     */   }
/*     */   
/*     */   public Attribute(String name, String nameFormat, List values) {
/* 106 */     setName(name);
/* 107 */     setNameFormat(nameFormat);
/* 108 */     setAttributeValue(values);
/*     */   }
/*     */   
/*     */   public Attribute(AttributeType attType) {
/* 112 */     setName(attType.getName());
/* 113 */     setNameFormat(attType.getNameFormat());
/* 114 */     setAttributeValue(attType.getAttributeValue());
/*     */   }
/*     */   
/*     */   public List<Object> getAttributes() {
/* 118 */     return getAttributeValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\Attribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */