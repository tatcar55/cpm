/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.AuthenticationStatement;
/*     */ import com.sun.xml.wss.saml.AuthorityBinding;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AuthenticationStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AuthorityBindingType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.SubjectType;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import com.sun.xml.wss.util.DateUtils;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
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
/*     */ public class AuthenticationStatement
/*     */   extends AuthenticationStatementType
/*     */   implements AuthenticationStatement
/*     */ {
/*  81 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*  84 */   private List<AuthorityBinding> authorityBindingList = null;
/*  85 */   private Date instantDate = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthenticationStatementType fromElement(Element element) throws SAMLException {
/*     */     try {
/* 103 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 105 */       Unmarshaller u = jc.createUnmarshaller();
/* 106 */       return (AuthenticationStatementType)u.unmarshal(element);
/* 107 */     } catch (Exception ex) {
/* 108 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAuthorityBinding(List authorityBinding) {
/* 114 */     this.authorityBinding = authorityBinding;
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
/*     */   public AuthenticationStatement(String authMethod, GregorianCalendar authInstant, Subject subject, SubjectLocality subjectLocality, List authorityBinding) {
/* 134 */     if (authMethod != null) {
/* 135 */       setAuthenticationMethod(authMethod);
/*     */     }
/* 137 */     if (authInstant != null) {
/*     */       try {
/* 139 */         DatatypeFactory factory = DatatypeFactory.newInstance();
/* 140 */         setAuthenticationInstant(factory.newXMLGregorianCalendar(authInstant));
/* 141 */       } catch (DatatypeConfigurationException ex) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 146 */     if (subject != null) {
/* 147 */       setSubject(subject);
/*     */     }
/* 149 */     if (subjectLocality != null) {
/* 150 */       setSubjectLocality(subjectLocality);
/*     */     }
/* 152 */     if (authorityBinding != null)
/* 153 */       setAuthorityBinding(authorityBinding); 
/*     */   }
/*     */   
/*     */   public AuthenticationStatement(AuthenticationStatementType authStmtType) {
/* 157 */     setAuthenticationMethod(authStmtType.getAuthenticationMethod());
/* 158 */     setAuthenticationInstant(authStmtType.getAuthenticationInstant());
/* 159 */     if (authStmtType.getSubject() != null) {
/* 160 */       Subject subj = new Subject(authStmtType.getSubject());
/* 161 */       setSubject(subj);
/*     */     } 
/* 163 */     setSubjectLocality(authStmtType.getSubjectLocality());
/* 164 */     setAuthorityBinding(authStmtType.getAuthorityBinding());
/*     */   }
/*     */   
/*     */   public Date getAuthenticationInstantDate() {
/* 168 */     if (this.instantDate != null) {
/* 169 */       return this.instantDate;
/*     */     }
/*     */     try {
/* 172 */       if (getAuthenticationInstant() != null) {
/* 173 */         this.instantDate = DateUtils.stringToDate(getAuthenticationInstant().toString());
/*     */       }
/* 175 */     } catch (ParseException ex) {
/* 176 */       log.log(Level.SEVERE, (String)null, ex);
/*     */     } 
/* 178 */     return this.instantDate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAuthenticationMethod() {
/* 184 */     return super.getAuthenticationMethod();
/*     */   }
/*     */   
/*     */   public List<AuthorityBinding> getAuthorityBindingList() {
/* 188 */     if (this.authorityBindingList != null) {
/* 189 */       this.authorityBindingList = new ArrayList<AuthorityBinding>();
/*     */     } else {
/* 191 */       return this.authorityBindingList;
/*     */     } 
/* 193 */     Iterator<AuthorityBindingType> it = getAuthorityBinding().iterator();
/* 194 */     while (it.hasNext()) {
/* 195 */       AuthorityBinding obj = new AuthorityBinding(it.next());
/*     */       
/* 197 */       this.authorityBindingList.add(obj);
/*     */     } 
/* 199 */     return this.authorityBindingList;
/*     */   }
/*     */   
/*     */   public String getSubjectLocalityIPAddress() {
/* 203 */     if (getSubjectLocality() != null) {
/* 204 */       return getSubjectLocality().getIPAddress();
/*     */     }
/* 206 */     return null;
/*     */   }
/*     */   
/*     */   public String getSubjectLocalityDNSAddress() {
/* 210 */     if (getSubjectLocality() != null) {
/* 211 */       return getSubjectLocality().getDNSAddress();
/*     */     }
/* 213 */     return null;
/*     */   }
/*     */   
/*     */   public Subject getSubject() {
/* 217 */     return (Subject)super.getSubject();
/*     */   }
/*     */   
/*     */   protected AuthenticationStatement() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\AuthenticationStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */