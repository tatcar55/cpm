/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.NameID;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmationData;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.SubjectConfirmationDataType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.SubjectConfirmationType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
/*     */ import java.security.PublicKey;
/*     */ import java.util.ArrayList;
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
/*     */ public class SubjectConfirmation
/*     */   extends SubjectConfirmationType
/*     */   implements SubjectConfirmation
/*     */ {
/*  67 */   protected PublicKey keyInfoKeyValue = null;
/*     */   
/*  69 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation(NameID nameID, String method) {
/*  90 */     setNameID(nameID);
/*  91 */     setMethod(method);
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
/* 105 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/* 107 */       Unmarshaller u = jc.createUnmarshaller();
/* 108 */       return (SubjectConfirmationType)u.unmarshal(element);
/* 109 */     } catch (Exception ex) {
/* 110 */       throw new SAMLException(ex.getMessage());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation(NameID nameID, SubjectConfirmationData subjectConfirmationData, String confirmationMethod) throws SAMLException {
/* 157 */     setNameID(nameID);
/* 158 */     if (subjectConfirmationData != null)
/* 159 */       setSubjectConfirmationData(subjectConfirmationData); 
/* 160 */     setMethod(confirmationMethod);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation(NameID nameID, KeyInfoConfirmationData keyInfoConfirmationData, String confirmationMethod) throws SAMLException {
/* 167 */     setNameID(nameID);
/* 168 */     if (keyInfoConfirmationData != null)
/* 169 */       setSubjectConfirmationData((SubjectConfirmationDataType)keyInfoConfirmationData); 
/* 170 */     setMethod(confirmationMethod);
/*     */   }
/*     */ 
/*     */   
/*     */   public SubjectConfirmation(SubjectConfirmationType subConfType) {
/* 175 */     if (subConfType.getNameID() != null) {
/* 176 */       NameID nameId = new NameID(subConfType.getNameID());
/* 177 */       setNameID(nameId);
/*     */     } 
/* 179 */     if (subConfType.getSubjectConfirmationData() != null) {
/* 180 */       SubjectConfirmationData subConData = new SubjectConfirmationData(subConfType.getSubjectConfirmationData());
/* 181 */       setSubjectConfirmationData(subConData);
/*     */     } 
/* 183 */     setMethod(subConfType.getMethod());
/*     */   }
/*     */   
/*     */   public List<String> getConfirmationMethod() {
/* 187 */     List<String> confirmMethods = new ArrayList<String>();
/* 188 */     confirmMethods.add(getMethod());
/* 189 */     return confirmMethods;
/*     */   }
/*     */   
/*     */   public Object getSubjectConfirmationDataForSAML11() {
/* 193 */     throw new UnsupportedOperationException("Not supported for SAML 2.0");
/*     */   }
/*     */   public SubjectConfirmationData getSubjectConfirmationDataForSAML20() {
/* 196 */     return (SubjectConfirmationData)getSubjectConfirmationData();
/*     */   }
/*     */   
/*     */   public NameID getNameId() {
/* 200 */     return (NameID)getNameID();
/*     */   }
/*     */   
/*     */   public SubjectConfirmation() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\SubjectConfirmation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */