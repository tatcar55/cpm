/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Action;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.ActionType;
/*     */ import java.util.logging.Logger;
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
/*     */ public class Action
/*     */   extends ActionType
/*     */   implements Action
/*     */ {
/*  62 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action(Element element) {
/*  75 */     setValue(element.getLocalName());
/*  76 */     setNamespace(element.getNamespaceURI());
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
/*     */   public Action(String namespace, String action) {
/*  88 */     setValue(action);
/*  89 */     setNamespace(namespace);
/*     */   }
/*     */   
/*     */   public Action(ActionType actionType) {
/*  93 */     setValue(actionType.getValue());
/*  94 */     setNamespace(actionType.getNamespace());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  99 */     return super.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespace() {
/* 104 */     return super.getNamespace();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\Action.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */