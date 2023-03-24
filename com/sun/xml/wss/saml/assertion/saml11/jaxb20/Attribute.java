/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Attribute;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AttributeType;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Attribute
/*     */   extends AttributeType
/*     */   implements Attribute
/*     */ {
/*  67 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
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
/*  81 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  83 */       Unmarshaller u = jc.createUnmarshaller();
/*  84 */       return (AttributeType)u.unmarshal(element);
/*  85 */     } catch (Exception ex) {
/*  86 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setAttributeValue(List values) {
/*  91 */     this.attributeValue = values;
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
/*     */   public Attribute(String name, String nameSpace, List values) {
/* 107 */     setAttributeName(name);
/* 108 */     setAttributeNamespace(nameSpace);
/* 109 */     setAttributeValue(values);
/*     */   }
/*     */   
/*     */   public Attribute(AttributeType attType) {
/* 113 */     setAttributeName(attType.getAttributeName());
/* 114 */     setAttributeNamespace(attType.getAttributeNamespace());
/* 115 */     setAttributeValue(attType.getAttributeValue());
/*     */   }
/*     */   
/*     */   public String getFriendlyName() {
/* 119 */     return null;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 123 */     return getAttributeName();
/*     */   }
/*     */   
/*     */   public String getNameFormat() {
/* 127 */     return getAttributeNamespace();
/*     */   }
/*     */   
/*     */   public List<Object> getAttributes() {
/* 131 */     return getAttributeValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\Attribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */