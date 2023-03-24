/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.KeyInfoType;
/*     */ import com.sun.xml.wss.saml.NameID;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmationData;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.SubjectConfirmationType;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.security.PublicKey;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class SubjectConfirmation
/*     */   extends SubjectConfirmationType
/*     */   implements SubjectConfirmation
/*     */ {
/*  71 */   protected PublicKey keyInfoKeyValue = null;
/*     */   
/*  73 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConfirmationMethod(List confirmationMethod) {
/*  82 */     this.confirmationMethod = confirmationMethod;
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
/*     */   public SubjectConfirmation(String confirmationMethod) {
/*  97 */     List<String> cm = new LinkedList();
/*  98 */     cm.add(confirmationMethod);
/*  99 */     setConfirmationMethod(cm);
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
/*     */   public static SubjectConfirmationType fromElement(Element element) throws SAMLException {
/*     */     try {
/* 113 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 115 */       Unmarshaller u = jc.createUnmarshaller();
/* 116 */       return (SubjectConfirmationType)u.unmarshal(element);
/* 117 */     } catch (Exception ex) {
/* 118 */       throw new SAMLException(ex.getMessage());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation(List confirmationMethods, Element subjectConfirmationData, Element keyInfo) throws SAMLException {
/* 143 */     JAXBContext jc = null;
/* 144 */     Unmarshaller u = null;
/*     */ 
/*     */     
/*     */     try {
/* 148 */       jc = SAMLJAXBUtil.getJAXBContext();
/* 149 */       u = jc.createUnmarshaller();
/* 150 */     } catch (Exception ex) {
/* 151 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */     
/*     */     try {
/* 155 */       if (keyInfo != null) {
/* 156 */         setKeyInfo(((JAXBElement<KeyInfoType>)u.unmarshal(keyInfo)).getValue());
/*     */       }
/* 158 */       if (subjectConfirmationData != null) {
/* 159 */         setSubjectConfirmationData(((JAXBElement<SubjectConfirmationType>)u.unmarshal(subjectConfirmationData)).getValue());
/*     */       }
/* 161 */     } catch (Exception ex) {
/*     */       
/* 163 */       throw new SAMLException(ex);
/*     */     } 
/* 165 */     setConfirmationMethod(confirmationMethods);
/*     */   }
/*     */   
/*     */   public SubjectConfirmation(SubjectConfirmationType subConfType) {
/* 169 */     setSubjectConfirmationData(subConfType.getSubjectConfirmationData());
/* 170 */     setConfirmationMethod(subConfType.getConfirmationMethod());
/*     */   }
/*     */   
/*     */   public Object getSubjectConfirmationDataForSAML11() {
/* 174 */     return getSubjectConfirmationData();
/*     */   }
/*     */   
/*     */   public SubjectConfirmationData getSubjectConfirmationDataForSAML20() {
/* 178 */     throw new UnsupportedOperationException("Not supported for SAML 1.1");
/*     */   }
/*     */   
/*     */   public NameID getNameId() {
/* 182 */     throw new UnsupportedOperationException("Not supported for SAML 1.1");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\SubjectConfirmation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */