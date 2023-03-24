/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.wss.saml.Action;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.ActionType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ActionImpl;
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
/*     */   extends ActionImpl
/*     */   implements Action
/*     */ {
/*  63 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
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
/*  76 */     setValue(element.getLocalName());
/*  77 */     setNamespace(element.getNamespaceURI());
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
/*  89 */     setValue(action);
/*  90 */     setNamespace(namespace);
/*     */   }
/*     */   
/*     */   public Action(ActionType actionType) {
/*  94 */     setValue(actionType.getValue());
/*  95 */     setNamespace(actionType.getNamespace());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 100 */     return super.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespace() {
/* 105 */     return super.getNamespace();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\Action.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */