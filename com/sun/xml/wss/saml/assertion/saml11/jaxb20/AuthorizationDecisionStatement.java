/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Action;
/*     */ import com.sun.xml.wss.saml.AuthorizationDecisionStatement;
/*     */ import com.sun.xml.wss.saml.Evidence;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.ActionType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AuthorizationDecisionStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.DecisionType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.EvidenceType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.SubjectType;
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
/*     */ public class AuthorizationDecisionStatement
/*     */   extends AuthorizationDecisionStatementType
/*     */   implements AuthorizationDecisionStatement
/*     */ {
/*  72 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*  75 */   private List<Action> actionList = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthorizationDecisionStatementType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  94 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  96 */       Unmarshaller u = jc.createUnmarshaller();
/*  97 */       return (AuthorizationDecisionStatementType)u.unmarshal(element);
/*  98 */     } catch (Exception ex) {
/*  99 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setAction(List action) {
/* 104 */     this.action = action;
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
/*     */   public AuthorizationDecisionStatement(Subject subject, String resource, String decision, List action, Evidence evidence) {
/* 126 */     setSubject(subject);
/* 127 */     setResource(resource);
/* 128 */     setDecision(DecisionType.fromValue(decision));
/* 129 */     setAction(action);
/* 130 */     setEvidence(evidence);
/*     */   }
/*     */   
/*     */   public AuthorizationDecisionStatement(AuthorizationDecisionStatementType authDesStmt) {
/* 134 */     if (authDesStmt.getSubject() != null) {
/* 135 */       Subject subj = new Subject(authDesStmt.getSubject());
/* 136 */       setSubject(subj);
/*     */     } 
/* 138 */     setResource(authDesStmt.getResource());
/* 139 */     setDecision(authDesStmt.getDecision());
/* 140 */     setAction(authDesStmt.getAction());
/* 141 */     setEvidence(authDesStmt.getEvidence());
/*     */   }
/*     */   
/*     */   public List<Action> getActionList() {
/* 145 */     if (this.actionList == null) {
/* 146 */       this.actionList = new ArrayList<Action>();
/*     */     } else {
/* 148 */       return this.actionList;
/*     */     } 
/* 150 */     Iterator<ActionType> it = getAction().iterator();
/* 151 */     while (it.hasNext()) {
/* 152 */       Action obj = new Action(it.next());
/*     */       
/* 154 */       this.actionList.add(obj);
/*     */     } 
/* 156 */     return this.actionList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Evidence getEvidence() {
/* 161 */     Evidence eve = new Evidence(super.getEvidence());
/* 162 */     return eve;
/*     */   }
/*     */   
/*     */   public String getDecisionValue() {
/* 166 */     return getDecision().value();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getResource() {
/* 171 */     return super.getResource();
/*     */   }
/*     */ 
/*     */   
/*     */   public Subject getSubject() {
/* 176 */     return (Subject)super.getSubject();
/*     */   }
/*     */   
/*     */   protected AuthorizationDecisionStatement() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\AuthorizationDecisionStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */