/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.wss.saml.NameID;
/*     */ import com.sun.xml.wss.saml.NameIdentifier;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.NameIdentifierType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectConfirmationType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectTypeImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Subject
/*     */   extends SubjectImpl
/*     */   implements Subject
/*     */ {
/*  76 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Subject(NameIdentifier nameIdentifier, SubjectConfirmation subjectConfirmation) {
/*  96 */     if (nameIdentifier != null) {
/*  97 */       setNameIdentifier((NameIdentifierType)nameIdentifier);
/*     */     }
/*  99 */     if (subjectConfirmation != null) {
/* 100 */       setSubjectConfirmation((SubjectConfirmationType)subjectConfirmation);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Subject(SubjectType subjectType) {}
/*     */   
/*     */   public NameIdentifier getNameIdentifier() {
/* 108 */     return (NameIdentifier)super.getNameIdentifier();
/*     */   }
/*     */   
/*     */   public NameID getNameId() {
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public SubjectConfirmation getSubjectConfirmation() {
/* 117 */     return (SubjectConfirmation)super.getSubjectConfirmation();
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
/*     */   public static SubjectTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/* 132 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 134 */       Unmarshaller u = jc.createUnmarshaller();
/* 135 */       return (SubjectTypeImpl)u.unmarshal(element);
/* 136 */     } catch (Exception ex) {
/* 137 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\Subject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */