/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.Attribute;
/*     */ import com.sun.xml.wss.saml.AttributeStatement;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeStatementImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeStatementTypeImpl;
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
/*     */ 
/*     */ public class AttributeStatement
/*     */   extends AttributeStatementImpl
/*     */   implements AttributeStatement
/*     */ {
/*  73 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*  76 */   private List<Attribute> attValueList = null;
/*     */   
/*     */   private void setAttributes(List attr) {
/*  79 */     this._Attribute = new ListImpl(attr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeStatement(Subject subj, List attr) {
/*  86 */     setSubject((SubjectType)subj);
/*  87 */     setAttributes(attr);
/*     */   }
/*     */   
/*     */   public AttributeStatement(AttributeStatementType attStmtType) {
/*  91 */     if (attStmtType.getSubject() != null) {
/*  92 */       Subject sub = new Subject(attStmtType.getSubject());
/*  93 */       setSubject((SubjectType)sub);
/*     */     } 
/*  95 */     setAttributes(attStmtType.getAttribute());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttributeStatementTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/* 107 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 109 */       Unmarshaller u = jc.createUnmarshaller();
/* 110 */       return (AttributeStatementTypeImpl)u.unmarshal(element);
/* 111 */     } catch (Exception ex) {
/* 112 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Attribute> getAttributes() {
/* 117 */     if (this.attValueList == null) {
/* 118 */       this.attValueList = new ArrayList<Attribute>();
/*     */     } else {
/* 120 */       return this.attValueList;
/*     */     } 
/* 122 */     Iterator<AttributeType> it = getAttribute().iterator();
/* 123 */     while (it.hasNext()) {
/* 124 */       Attribute obj = new Attribute(it.next());
/*     */       
/* 126 */       this.attValueList.add(obj);
/*     */     } 
/* 128 */     return this.attValueList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Subject getSubject() {
/* 134 */     return (Subject)super.getSubject();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\AttributeStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */