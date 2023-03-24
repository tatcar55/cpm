/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.KeyInfoType;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmationData;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.SubjectConfirmationDataType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
/*     */ import com.sun.xml.wss.util.DateUtils;
/*     */ import java.security.PublicKey;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.datatype.DatatypeConfigurationException;
/*     */ import javax.xml.datatype.DatatypeFactory;
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
/*     */ public class SubjectConfirmationData
/*     */   extends SubjectConfirmationDataType
/*     */   implements SubjectConfirmationData
/*     */ {
/*  75 */   protected PublicKey keyInfoKeyValue = null;
/*  76 */   private Date notBeforeDate = null;
/*  77 */   private Date notOnOrAfterDate = null;
/*  78 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmationData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SubjectConfirmationDataType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  98 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/* 100 */       Unmarshaller u = jc.createUnmarshaller();
/* 101 */       return (SubjectConfirmationDataType)u.unmarshal(element);
/* 102 */     } catch (Exception ex) {
/* 103 */       throw new SAMLException(ex.getMessage());
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
/*     */   public SubjectConfirmationData(String address, String inResponseTo, GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, String recipient, Element keyInfo) throws SAMLException {
/* 128 */     JAXBContext jc = null;
/* 129 */     Unmarshaller u = null;
/*     */ 
/*     */     
/*     */     try {
/* 133 */       jc = SAML20JAXBUtil.getJAXBContext();
/* 134 */       u = jc.createUnmarshaller();
/* 135 */     } catch (Exception ex) {
/* 136 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     setAddress(address);
/* 151 */     setInResponseTo(inResponseTo);
/* 152 */     if (notBefore != null) {
/*     */       try {
/* 154 */         DatatypeFactory factory = DatatypeFactory.newInstance();
/* 155 */         setNotBefore(factory.newXMLGregorianCalendar(notBefore));
/* 156 */       } catch (DatatypeConfigurationException ex) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (notOnOrAfter != null) {
/*     */       try {
/* 163 */         DatatypeFactory factory = DatatypeFactory.newInstance();
/* 164 */         setNotOnOrAfter(factory.newXMLGregorianCalendar(notOnOrAfter));
/* 165 */       } catch (DatatypeConfigurationException ex) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 170 */     setRecipient(recipient);
/*     */     
/*     */     try {
/* 173 */       if (keyInfo != null)
/*     */       {
/* 175 */         getContent().add(((JAXBElement<KeyInfoType>)u.unmarshal(keyInfo)).getValue());
/*     */       }
/* 177 */     } catch (Exception ex) {
/*     */       
/* 179 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SubjectConfirmationData(SubjectConfirmationDataType subConfDataType) {
/* 184 */     setAddress(subConfDataType.getAddress());
/* 185 */     setInResponseTo(subConfDataType.getInResponseTo());
/* 186 */     setNotBefore(subConfDataType.getNotBefore());
/* 187 */     setNotOnOrAfter(subConfDataType.getNotOnOrAfter());
/* 188 */     setRecipient(subConfDataType.getRecipient());
/*     */   }
/*     */   
/*     */   public Date getNotBeforeDate() {
/* 192 */     if (this.notBeforeDate == null && 
/* 193 */       getNotBefore() != null) {
/*     */       try {
/* 195 */         this.notBeforeDate = DateUtils.stringToDate(getNotBefore().toString());
/* 196 */       } catch (ParseException ex) {
/* 197 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0430_SAML_GET_NOT_BEFORE_DATE_OR_GET_NOT_ON_OR_AFTER_DATE_PARSE_FAILED(), ex);
/*     */       } 
/*     */     }
/*     */     
/* 201 */     return this.notBeforeDate;
/*     */   }
/*     */   
/*     */   public Date getNotOnOrAfterDate() {
/* 205 */     if (this.notOnOrAfterDate == null && 
/* 206 */       getNotOnOrAfter() != null) {
/*     */       try {
/* 208 */         this.notOnOrAfterDate = DateUtils.stringToDate(getNotOnOrAfter().toString());
/* 209 */       } catch (ParseException ex) {
/* 210 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0430_SAML_GET_NOT_BEFORE_DATE_OR_GET_NOT_ON_OR_AFTER_DATE_PARSE_FAILED(), ex);
/*     */       } 
/*     */     }
/*     */     
/* 214 */     return this.notOnOrAfterDate;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\SubjectConfirmationData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */