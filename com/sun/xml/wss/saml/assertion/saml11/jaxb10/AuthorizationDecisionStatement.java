/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.Action;
/*     */ import com.sun.xml.wss.saml.AuthorizationDecisionStatement;
/*     */ import com.sun.xml.wss.saml.Evidence;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.ActionType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorizationDecisionStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.EvidenceType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorizationDecisionStatementImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorizationDecisionStatementTypeImpl;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class AuthorizationDecisionStatement
/*     */   extends AuthorizationDecisionStatementImpl
/*     */   implements AuthorizationDecisionStatement
/*     */ {
/*  75 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*  78 */   private List<Action> actionList = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthorizationDecisionStatementTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/*  98 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 100 */       Unmarshaller u = jc.createUnmarshaller();
/* 101 */       return (AuthorizationDecisionStatementTypeImpl)u.unmarshal(element);
/* 102 */     } catch (Exception ex) {
/* 103 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setAction(List action) {
/* 108 */     this._Action = new ListImpl(action);
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
/*     */   public AuthorizationDecisionStatement(Subject subject, String resource, String decision, List action, Evidence evidence) {
/* 129 */     setSubject((SubjectType)subject);
/* 130 */     setResource(resource);
/* 131 */     setDecision(decision);
/* 132 */     setAction(action);
/* 133 */     setEvidence((EvidenceType)evidence);
/*     */   }
/*     */   
/*     */   public AuthorizationDecisionStatement(AuthorizationDecisionStatementType authDesStmt) {
/* 137 */     if (authDesStmt.getSubject() != null) {
/* 138 */       Subject subj = new Subject(authDesStmt.getSubject());
/* 139 */       setSubject((SubjectType)subj);
/*     */     } 
/* 141 */     setResource(authDesStmt.getResource());
/* 142 */     setDecision(authDesStmt.getDecision());
/* 143 */     setAction(authDesStmt.getAction());
/* 144 */     setEvidence(authDesStmt.getEvidence());
/*     */   }
/*     */   
/*     */   public List<Action> getActionList() {
/* 148 */     if (this.actionList == null) {
/* 149 */       this.actionList = new ArrayList<Action>();
/*     */     } else {
/* 151 */       return this.actionList;
/*     */     } 
/* 153 */     Iterator<ActionType> it = getAction().iterator();
/* 154 */     while (it.hasNext()) {
/* 155 */       Action obj = new Action(it.next());
/*     */       
/* 157 */       this.actionList.add(obj);
/*     */     } 
/* 159 */     return this.actionList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Evidence getEvidence() {
/* 164 */     Evidence eve = new Evidence(super.getEvidence());
/* 165 */     return eve;
/*     */   }
/*     */   
/*     */   public String getDecisionValue() {
/* 169 */     return getDecision();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getResource() {
/* 174 */     return super.getResource();
/*     */   }
/*     */ 
/*     */   
/*     */   public Subject getSubject() {
/* 179 */     return (Subject)super.getSubject();
/*     */   }
/*     */   
/*     */   protected AuthorizationDecisionStatement() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\AuthorizationDecisionStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */