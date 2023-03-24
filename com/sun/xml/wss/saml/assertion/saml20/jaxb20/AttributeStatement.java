/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Attribute;
/*     */ import com.sun.xml.wss.saml.AttributeStatement;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AttributeStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AttributeType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class AttributeStatement
/*     */   extends AttributeStatementType
/*     */   implements AttributeStatement
/*     */ {
/*  66 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*  69 */   private List<Attribute> attValueList = null;
/*     */   
/*     */   private void setAttributes(List attr) {
/*  72 */     this.attributeOrEncryptedAttribute = attr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeStatement(List attr) {
/*  79 */     setAttributes(attr);
/*     */   }
/*     */   
/*     */   public AttributeStatement(AttributeStatementType attStmtType) {
/*  83 */     setAttributes(attStmtType.getAttributeOrEncryptedAttribute());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttributeStatementType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  95 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/*  97 */       Unmarshaller u = jc.createUnmarshaller();
/*  98 */       return (AttributeStatementType)u.unmarshal(element);
/*  99 */     } catch (Exception ex) {
/* 100 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Attribute> getAttributes() {
/* 105 */     if (this.attValueList == null) {
/* 106 */       this.attValueList = new ArrayList<Attribute>();
/*     */     } else {
/* 108 */       return this.attValueList;
/*     */     } 
/* 110 */     Iterator<AttributeType> it = getAttributeOrEncryptedAttribute().iterator();
/* 111 */     while (it.hasNext()) {
/* 112 */       Attribute obj = new Attribute(it.next());
/*     */       
/* 114 */       this.attValueList.add(obj);
/*     */     } 
/* 116 */     return this.attValueList;
/*     */   }
/*     */   
/*     */   public Subject getSubject() {
/* 120 */     throw new UnsupportedOperationException("getSubject() on statement object is not supported for SAML 2.0 Make the direct call of getSubject() method on SAML2.0 assertion");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\AttributeStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */