/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.NameID;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmationData;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AnyType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyInfoType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectConfirmationType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectConfirmationImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectConfirmationTypeImpl;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.security.PublicKey;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
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
/*     */ public class SubjectConfirmation
/*     */   extends SubjectConfirmationImpl
/*     */   implements SubjectConfirmation
/*     */ {
/*  76 */   protected PublicKey keyInfoKeyValue = null;
/*     */   
/*  78 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConfirmationMethod(List confirmationMethod) {
/*  88 */     this._ConfirmationMethod = new ListImpl(confirmationMethod);
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
/* 103 */     List<String> cm = new LinkedList();
/* 104 */     cm.add(confirmationMethod);
/* 105 */     setConfirmationMethod(cm);
/*     */   }
/*     */   
/*     */   public SubjectConfirmation(SubjectConfirmationType subConfType) {
/* 109 */     setConfirmationMethod(subConfType.getConfirmationMethod());
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
/*     */   public static SubjectConfirmationTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/* 123 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 125 */       Unmarshaller u = jc.createUnmarshaller();
/* 126 */       return (SubjectConfirmationTypeImpl)u.unmarshal(element);
/* 127 */     } catch (Exception ex) {
/* 128 */       throw new SAMLException(ex.getMessage());
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
/* 153 */     JAXBContext jc = null;
/* 154 */     Unmarshaller u = null;
/*     */ 
/*     */     
/*     */     try {
/* 158 */       jc = SAMLJAXBUtil.getJAXBContext();
/* 159 */       u = jc.createUnmarshaller();
/* 160 */     } catch (Exception ex) {
/* 161 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */     
/*     */     try {
/* 165 */       if (keyInfo != null) {
/* 166 */         setKeyInfo((KeyInfoType)u.unmarshal(keyInfo));
/*     */       }
/*     */       
/* 169 */       if (subjectConfirmationData != null) {
/* 170 */         setSubjectConfirmationData((AnyType)u.unmarshal(subjectConfirmationData));
/*     */       }
/* 172 */     } catch (Exception ex) {
/*     */       
/* 174 */       throw new SAMLException(ex);
/*     */     } 
/* 176 */     setConfirmationMethod(confirmationMethods);
/*     */   }
/*     */   
/*     */   public Object getSubjectConfirmationDataForSAML11() {
/* 180 */     return getSubjectConfirmationData();
/*     */   }
/*     */   
/*     */   public SubjectConfirmationData getSubjectConfirmationDataForSAML20() {
/* 184 */     throw new UnsupportedOperationException("Not supported for SAML 1.0");
/*     */   }
/*     */   
/*     */   public NameID getNameId() {
/* 188 */     throw new UnsupportedOperationException("Not supported for SAML 1.0");
/*     */   }
/*     */   public List<String> getConfirmationMethod() {
/* 191 */     List base = super.getConfirmationMethod();
/* 192 */     if (base == null) {
/* 193 */       return null;
/*     */     }
/* 195 */     List<String> ret = new ArrayList<String>();
/* 196 */     for (Object obj : base) {
/* 197 */       ret.add((String)obj);
/*     */     }
/* 199 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\SubjectConfirmation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */