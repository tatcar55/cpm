/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Attribute;
/*     */ import com.sun.xml.wss.saml.AttributeStatement;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AttributeStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AttributeType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.SubjectType;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
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
/*     */ 
/*     */ 
/*     */ public class AttributeStatement
/*     */   extends AttributeStatementType
/*     */   implements AttributeStatement
/*     */ {
/*  69 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*  72 */   private List<Attribute> attValueList = null;
/*     */ 
/*     */   
/*     */   private void setAttributes(List attr) {
/*  76 */     this.attribute = attr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeStatement(Subject subj, List attr) {
/*  83 */     setSubject(subj);
/*  84 */     setAttributes(attr);
/*     */   }
/*     */   
/*     */   public AttributeStatement(AttributeStatementType attStmtType) {
/*  88 */     if (attStmtType.getSubject() != null) {
/*  89 */       Subject sub = new Subject(attStmtType.getSubject());
/*  90 */       setSubject(sub);
/*     */     } 
/*  92 */     setAttributes(attStmtType.getAttribute());
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
/* 104 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 106 */       Unmarshaller u = jc.createUnmarshaller();
/* 107 */       return (AttributeStatementType)u.unmarshal(element);
/* 108 */     } catch (Exception ex) {
/* 109 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Attribute> getAttributes() {
/* 114 */     if (this.attValueList == null) {
/* 115 */       this.attValueList = new ArrayList<Attribute>();
/*     */     } else {
/* 117 */       return this.attValueList;
/*     */     } 
/* 119 */     Iterator<AttributeType> it = getAttribute().iterator();
/* 120 */     while (it.hasNext()) {
/* 121 */       Attribute obj = new Attribute(it.next());
/*     */       
/* 123 */       this.attValueList.add(obj);
/*     */     } 
/* 125 */     return this.attValueList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Subject getSubject() {
/* 131 */     return (Subject)super.getSubject();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\AttributeStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */