/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.AuthenticationStatement;
/*     */ import com.sun.xml.wss.saml.AuthorityBinding;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthenticationStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorityBindingType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectLocalityType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthenticationStatementImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthenticationStatementTypeImpl;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import com.sun.xml.wss.util.DateUtils;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
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
/*     */ public class AuthenticationStatement
/*     */   extends AuthenticationStatementImpl
/*     */   implements AuthenticationStatement
/*     */ {
/*  82 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*  85 */   private List<AuthorityBinding> authorityBindingList = null;
/*  86 */   private Date instantDate = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthenticationStatementTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/* 105 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 107 */       Unmarshaller u = jc.createUnmarshaller();
/* 108 */       return (AuthenticationStatementTypeImpl)u.unmarshal(element);
/* 109 */     } catch (Exception ex) {
/* 110 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAuthorityBinding(List authorityBinding) {
/* 116 */     this._AuthorityBinding = new ListImpl(authorityBinding);
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
/*     */   public AuthenticationStatement(String authMethod, Calendar authInstant, Subject subject, SubjectLocality subjectLocality, List authorityBinding) {
/* 137 */     if (authMethod != null) {
/* 138 */       setAuthenticationMethod(authMethod);
/*     */     }
/* 140 */     if (authInstant != null) {
/* 141 */       setAuthenticationInstant(authInstant);
/*     */     }
/* 143 */     if (subject != null) {
/* 144 */       setSubject((SubjectType)subject);
/*     */     }
/* 146 */     if (subjectLocality != null) {
/* 147 */       setSubjectLocality((SubjectLocalityType)subjectLocality);
/*     */     }
/* 149 */     if (authorityBinding != null)
/* 150 */       setAuthorityBinding(authorityBinding); 
/*     */   }
/*     */   
/*     */   public AuthenticationStatement(AuthenticationStatementType authStmtType) {
/* 154 */     setAuthenticationMethod(authStmtType.getAuthenticationMethod());
/* 155 */     setAuthenticationInstant(authStmtType.getAuthenticationInstant());
/* 156 */     if (authStmtType.getSubject() != null) {
/* 157 */       Subject subj = new Subject(authStmtType.getSubject());
/* 158 */       setSubject((SubjectType)subj);
/*     */     } 
/* 160 */     setSubjectLocality(authStmtType.getSubjectLocality());
/* 161 */     setAuthorityBinding(authStmtType.getAuthorityBinding());
/*     */   }
/*     */   
/*     */   public Date getAuthenticationInstantDate() {
/* 165 */     if (this.instantDate != null) {
/* 166 */       return this.instantDate;
/*     */     }
/*     */     try {
/* 169 */       if (getAuthenticationInstant() != null) {
/* 170 */         this.instantDate = DateUtils.stringToDate(getAuthenticationInstant().toString());
/*     */       }
/* 172 */     } catch (ParseException ex) {
/* 173 */       Logger.getLogger(AuthenticationStatement.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/* 175 */     return this.instantDate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAuthenticationMethod() {
/* 181 */     return super.getAuthenticationMethod();
/*     */   }
/*     */   
/*     */   public List<AuthorityBinding> getAuthorityBindingList() {
/* 185 */     if (this.authorityBindingList != null) {
/* 186 */       this.authorityBindingList = new ArrayList<AuthorityBinding>();
/*     */     } else {
/* 188 */       return this.authorityBindingList;
/*     */     } 
/* 190 */     Iterator<AuthorityBindingType> it = getAuthorityBinding().iterator();
/* 191 */     while (it.hasNext()) {
/* 192 */       AuthorityBinding obj = new AuthorityBinding(it.next());
/*     */       
/* 194 */       this.authorityBindingList.add(obj);
/*     */     } 
/* 196 */     return this.authorityBindingList;
/*     */   }
/*     */   
/*     */   public String getSubjectLocalityIPAddress() {
/* 200 */     if (getSubjectLocality() != null) {
/* 201 */       return getSubjectLocality().getIPAddress();
/*     */     }
/* 203 */     return null;
/*     */   }
/*     */   
/*     */   public String getSubjectLocalityDNSAddress() {
/* 207 */     if (getSubjectLocality() != null) {
/* 208 */       return getSubjectLocality().getDNSAddress();
/*     */     }
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Subject getSubject() {
/* 215 */     return (Subject)super.getSubject();
/*     */   }
/*     */   
/*     */   protected AuthenticationStatement() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\AuthenticationStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */