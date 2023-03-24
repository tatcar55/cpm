/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Action;
/*     */ import com.sun.xml.wss.saml.AuthnDecisionStatement;
/*     */ import com.sun.xml.wss.saml.Evidence;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.ActionType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AuthzDecisionStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.DecisionType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.EvidenceType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
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
/*     */ public class AuthzDecisionStatement
/*     */   extends AuthzDecisionStatementType
/*     */   implements AuthnDecisionStatement
/*     */ {
/*  68 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*  71 */   private List<Action> actionList = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthzDecisionStatementType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  90 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/*  92 */       Unmarshaller u = jc.createUnmarshaller();
/*  93 */       return (AuthzDecisionStatementType)u.unmarshal(element);
/*  94 */     } catch (Exception ex) {
/*  95 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAction(List action) {
/* 101 */     this.action = action;
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
/*     */   public AuthzDecisionStatement(String resource, String decision, List action, Evidence evidence) {
/* 123 */     setResource(resource);
/* 124 */     setDecision(DecisionType.fromValue(decision));
/* 125 */     setAction(action);
/* 126 */     setEvidence(evidence);
/*     */   }
/*     */   
/*     */   public AuthzDecisionStatement(AuthzDecisionStatementType authDesStmt) {
/* 130 */     setResource(authDesStmt.getResource());
/* 131 */     setDecision(authDesStmt.getDecision());
/* 132 */     setAction(authDesStmt.getAction());
/* 133 */     setEvidence(authDesStmt.getEvidence());
/*     */   }
/*     */   
/*     */   public List<Action> getActionList() {
/* 137 */     if (this.actionList == null) {
/* 138 */       this.actionList = new ArrayList<Action>();
/*     */     } else {
/* 140 */       return this.actionList;
/*     */     } 
/* 142 */     Iterator<ActionType> it = getAction().iterator();
/* 143 */     while (it.hasNext()) {
/* 144 */       Action obj = new Action(it.next());
/*     */       
/* 146 */       this.actionList.add(obj);
/*     */     } 
/* 148 */     return this.actionList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Evidence getEvidence() {
/* 153 */     Evidence eve = new Evidence(super.getEvidence());
/* 154 */     return eve;
/*     */   }
/*     */   
/*     */   public String getDecisionValue() {
/* 158 */     return getDecision().value();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getResource() {
/* 163 */     return super.getResource();
/*     */   }
/*     */   
/*     */   protected AuthzDecisionStatement() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\AuthzDecisionStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */