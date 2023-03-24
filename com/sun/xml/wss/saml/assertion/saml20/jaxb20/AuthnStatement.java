/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.saml.AuthnStatement;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AuthnStatementType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
/*     */ import com.sun.xml.wss.util.DateUtils;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthnStatement
/*     */   extends AuthnStatementType
/*     */   implements AuthnStatement
/*     */ {
/*  78 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*  81 */   private Date authnInstantDate = null;
/*  82 */   private Date sessionDate = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AuthnStatement() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthnStatementType fromElement(Element element) throws SAMLException {
/*     */     try {
/* 101 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/* 103 */       Unmarshaller u = jc.createUnmarshaller();
/* 104 */       return (AuthnStatementType)u.unmarshal(element);
/* 105 */     } catch (Exception ex) {
/* 106 */       throw new SAMLException(ex.getMessage());
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
/*     */   public AuthnStatement(GregorianCalendar authInstant, SubjectLocality subjectLocality, AuthnContext authnContext, String sessionIndex, GregorianCalendar sessionNotOnOrAfter) {
/* 132 */     if (authInstant != null) {
/*     */       try {
/* 134 */         DatatypeFactory factory = DatatypeFactory.newInstance();
/* 135 */         setAuthnInstant(factory.newXMLGregorianCalendar(authInstant));
/* 136 */       } catch (DatatypeConfigurationException ex) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (subjectLocality != null) {
/* 142 */       setSubjectLocality(subjectLocality);
/*     */     }
/* 144 */     if (authnContext != null) {
/* 145 */       setAuthnContext(authnContext);
/*     */     }
/* 147 */     if (sessionIndex != null) {
/* 148 */       setSessionIndex(sessionIndex);
/*     */     }
/* 150 */     if (sessionNotOnOrAfter != null) {
/*     */       try {
/* 152 */         DatatypeFactory factory = DatatypeFactory.newInstance();
/* 153 */         setSessionNotOnOrAfter(factory.newXMLGregorianCalendar(sessionNotOnOrAfter));
/* 154 */       } catch (DatatypeConfigurationException ex) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthnStatement(AuthnStatementType authStmtType) {
/* 161 */     setAuthnInstant(authStmtType.getAuthnInstant());
/* 162 */     setAuthnContext(authStmtType.getAuthnContext());
/* 163 */     setSubjectLocality(authStmtType.getSubjectLocality());
/* 164 */     setSessionIndex(authStmtType.getSessionIndex());
/* 165 */     setSessionNotOnOrAfter(authStmtType.getSessionNotOnOrAfter());
/*     */   }
/*     */   
/*     */   public Date getAuthnInstantDate() {
/* 169 */     if (this.authnInstantDate != null) {
/* 170 */       return this.authnInstantDate;
/*     */     }
/*     */     try {
/* 173 */       if (getAuthnInstant() != null) {
/* 174 */         this.authnInstantDate = DateUtils.stringToDate(getAuthnInstant().toString());
/*     */       }
/* 176 */     } catch (ParseException ex) {
/* 177 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0429_SAML_AUTH_INSTANT_OR_SESSION_PARSE_FAILED(), ex);
/*     */     } 
/* 179 */     return this.authnInstantDate;
/*     */   }
/*     */   
/*     */   public Date getSessionNotOnOrAfterDate() {
/* 183 */     if (this.sessionDate != null) {
/* 184 */       return this.sessionDate;
/*     */     }
/*     */     try {
/* 187 */       if (getSessionNotOnOrAfter() != null) {
/* 188 */         this.sessionDate = DateUtils.stringToDate(getSessionNotOnOrAfter().toString());
/*     */       }
/* 190 */     } catch (ParseException ex) {
/* 191 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0429_SAML_AUTH_INSTANT_OR_SESSION_PARSE_FAILED(), ex);
/*     */     } 
/* 193 */     return this.sessionDate;
/*     */   }
/*     */   
/*     */   public String getSubjectLocalityAddress() {
/* 197 */     if (getSubjectLocality() != null) {
/* 198 */       return getSubjectLocality().getAddress();
/*     */     }
/* 200 */     return null;
/*     */   }
/*     */   
/*     */   public String getSubjectLocalityDNSName() {
/* 204 */     if (getSubjectLocality() != null) {
/* 205 */       return getSubjectLocality().getDNSName();
/*     */     }
/* 207 */     return null;
/*     */   }
/*     */   
/*     */   public String getAuthnContextClassRef() {
/* 211 */     Iterator it = getAuthnContext().getContent().iterator();
/* 212 */     while (it.hasNext()) {
/* 213 */       Object obj = it.next();
/* 214 */       if (obj instanceof JAXBElement) {
/* 215 */         JAXBElement<T> element = (JAXBElement)obj;
/* 216 */         if (element.getName().getLocalPart().equalsIgnoreCase("AuthnContextClassRef")) {
/* 217 */           return element.getValue().toString();
/*     */         }
/*     */       } 
/*     */     } 
/* 221 */     return null;
/*     */   }
/*     */   
/*     */   public String getAuthenticatingAuthority() {
/* 225 */     Iterator it = getAuthnContext().getContent().iterator();
/* 226 */     while (it.hasNext()) {
/* 227 */       Object obj = it.next();
/* 228 */       if (obj instanceof JAXBElement) {
/* 229 */         JAXBElement<T> element = (JAXBElement)obj;
/* 230 */         if (element.getName().getLocalPart().equalsIgnoreCase("AuthenticatingAuthority")) {
/* 231 */           return element.getValue().toString();
/*     */         }
/*     */       } 
/*     */     } 
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSessionIndex() {
/* 240 */     if (super.getSessionIndex() != null) {
/* 241 */       return super.getSessionIndex();
/*     */     }
/* 243 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\AuthnStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */