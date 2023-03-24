/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.KeyInfoType;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.saml.KeyInfoConfirmationData;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.KeyInfoConfirmationDataType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import com.sun.xml.wss.util.DateUtils;
/*     */ import java.security.PublicKey;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.logging.Level;
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
/*     */ public class KeyInfoConfirmationData
/*     */   extends KeyInfoConfirmationDataType
/*     */   implements KeyInfoConfirmationData
/*     */ {
/*  75 */   protected PublicKey keyInfoKeyValue = null;
/*     */ 
/*     */   
/*  78 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyInfoConfirmationData fromElement(Element element) throws SAMLException {
/*     */     try {
/*  93 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/*  95 */       Unmarshaller u = jc.createUnmarshaller();
/*  96 */       return (KeyInfoConfirmationData)u.unmarshal(element);
/*  97 */     } catch (Exception ex) {
/*  98 */       throw new SAMLException(ex.getMessage());
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
/*     */   public KeyInfoConfirmationData(Element keyInfo) throws SAMLException {
/* 122 */     JAXBContext jc = null;
/* 123 */     Unmarshaller u = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 128 */       jc = SAMLJAXBUtil.getJAXBContext();
/* 129 */       u = jc.createUnmarshaller();
/* 130 */     } catch (Exception ex) {
/* 131 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */     
/*     */     try {
/* 135 */       if (keyInfo != null) {
/* 136 */         setKeyInfo(((JAXBElement<KeyInfoType>)u.unmarshal(keyInfo)).getValue());
/*     */       }
/* 138 */     } catch (Exception ex) {
/*     */       
/* 140 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKeyInfo(KeyInfoType value) {
/* 146 */     getContent().add(value);
/*     */   }
/*     */   
/*     */   public Date getNotBeforeDate() {
/* 150 */     if (getNotBefore() != null) {
/* 151 */       Date getNotBeforeDate = null;
/*     */       try {
/* 153 */         getNotBeforeDate = DateUtils.stringToDate(getNotBefore().toString());
/* 154 */       } catch (ParseException ex) {
/* 155 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0430_SAML_GET_NOT_BEFORE_DATE_OR_GET_NOT_ON_OR_AFTER_DATE_PARSE_FAILED(), ex);
/*     */       } 
/* 157 */       return getNotBeforeDate;
/*     */     } 
/* 159 */     return null;
/*     */   }
/*     */   
/*     */   public Date getNotOnOrAfterDate() {
/* 163 */     if (getNotBefore() != null) {
/* 164 */       Date getNotBeforeDate = null;
/*     */       try {
/* 166 */         getNotBeforeDate = DateUtils.stringToDate(getNotOnOrAfter().toString());
/* 167 */       } catch (ParseException ex) {
/* 168 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0430_SAML_GET_NOT_BEFORE_DATE_OR_GET_NOT_ON_OR_AFTER_DATE_PARSE_FAILED(), ex);
/*     */       } 
/* 170 */       return getNotBeforeDate;
/*     */     } 
/* 172 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\KeyInfoConfirmationData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */