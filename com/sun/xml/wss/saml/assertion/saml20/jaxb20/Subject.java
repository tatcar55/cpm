/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.NameID;
/*     */ import com.sun.xml.wss.saml.NameIdentifier;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.NameIDType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.ObjectFactory;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.SubjectConfirmationType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.SubjectType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
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
/*     */ public class Subject
/*     */   extends SubjectType
/*     */   implements Subject
/*     */ {
/*  79 */   private NameID nameId = null;
/*  80 */   private SubjectConfirmation subjectConfirmation = null;
/*     */   
/*  82 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Subject(NameID nameId, SubjectConfirmation subjectConfirmation) {
/*  99 */     ObjectFactory factory = new ObjectFactory();
/*     */     
/* 101 */     if (nameId != null) {
/* 102 */       getContent().add(factory.createNameID(nameId));
/*     */     }
/* 104 */     if (subjectConfirmation != null) {
/* 105 */       getContent().add(factory.createSubjectConfirmation(subjectConfirmation));
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
/*     */   public static SubjectType fromElement(Element element) throws SAMLException {
/*     */     try {
/* 119 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/* 121 */       Unmarshaller u = jc.createUnmarshaller();
/* 122 */       return (SubjectType)u.unmarshal(element);
/* 123 */     } catch (Exception ex) {
/* 124 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public Subject(SubjectType subjectType) {
/* 129 */     this.content = subjectType.getContent();
/* 130 */     Iterator it = subjectType.getContent().iterator();
/*     */     
/* 132 */     while (it.hasNext()) {
/* 133 */       Object obj = it.next();
/* 134 */       if (obj instanceof JAXBElement) {
/* 135 */         Object object = ((JAXBElement)obj).getValue();
/* 136 */         if (object instanceof NameIDType) {
/* 137 */           this.nameId = new NameID((NameIDType)object); continue;
/* 138 */         }  if (object instanceof SubjectConfirmationType)
/* 139 */           this.subjectConfirmation = new SubjectConfirmation((SubjectConfirmationType)object); 
/*     */         continue;
/*     */       } 
/* 142 */       if (obj instanceof NameIDType) {
/* 143 */         this.nameId = new NameID((NameIDType)obj); continue;
/* 144 */       }  if (obj instanceof SubjectConfirmationType) {
/* 145 */         this.subjectConfirmation = new SubjectConfirmation((SubjectConfirmationType)obj);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NameIdentifier getNameIdentifier() {
/* 152 */     return null;
/*     */   }
/*     */   
/*     */   public NameID getNameId() {
/* 156 */     return this.nameId;
/*     */   }
/*     */   
/*     */   public SubjectConfirmation getSubjectConfirmation() {
/* 160 */     return this.subjectConfirmation;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\Subject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */