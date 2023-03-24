/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.NameID;
/*     */ import com.sun.xml.wss.saml.NameIdentifier;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.NameIdentifierType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.ObjectFactory;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.SubjectConfirmationType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.SubjectType;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Subject
/*     */   extends SubjectType
/*     */   implements Subject
/*     */ {
/*     */   private NameIdentifier nameIdentifier;
/*     */   private SubjectConfirmation subjectConfirmation;
/*  83 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 100 */     ObjectFactory factory = new ObjectFactory();
/*     */     
/* 102 */     if (nameIdentifier != null) {
/* 103 */       getContent().add(factory.createNameIdentifier(nameIdentifier));
/*     */     }
/* 105 */     if (subjectConfirmation != null)
/* 106 */       getContent().add(factory.createSubjectConfirmation(subjectConfirmation)); 
/*     */   }
/*     */   
/*     */   public Subject(SubjectType subjectType) {
/* 110 */     Iterator it = subjectType.getContent().iterator();
/*     */     
/* 112 */     while (it.hasNext()) {
/* 113 */       Object obj = it.next();
/* 114 */       if (obj instanceof JAXBElement) {
/* 115 */         Object object = ((JAXBElement)obj).getValue();
/* 116 */         if (object instanceof NameIdentifierType) {
/* 117 */           this.nameIdentifier = new NameIdentifier((NameIdentifierType)object); continue;
/* 118 */         }  if (object instanceof SubjectConfirmationType)
/* 119 */           this.subjectConfirmation = new SubjectConfirmation((SubjectConfirmationType)object); 
/*     */         continue;
/*     */       } 
/* 122 */       if (obj instanceof NameIdentifierType) {
/* 123 */         this.nameIdentifier = new NameIdentifier((NameIdentifierType)obj); continue;
/* 124 */       }  if (obj instanceof SubjectConfirmationType) {
/* 125 */         this.subjectConfirmation = new SubjectConfirmation((SubjectConfirmationType)obj);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NameIdentifier getNameIdentifier() {
/* 133 */     return this.nameIdentifier;
/*     */   }
/*     */   
/*     */   public NameID getNameId() {
/* 137 */     return null;
/*     */   }
/*     */   
/*     */   public SubjectConfirmation getSubjectConfirmation() {
/* 141 */     return this.subjectConfirmation;
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
/*     */   public static SubjectType fromElement(Element element) throws SAMLException {
/*     */     try {
/* 156 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 158 */       Unmarshaller u = jc.createUnmarshaller();
/* 159 */       return (SubjectType)u.unmarshal(element);
/* 160 */     } catch (Exception ex) {
/* 161 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\Subject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */