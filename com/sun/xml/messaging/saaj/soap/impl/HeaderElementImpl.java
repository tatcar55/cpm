/*     */ package com.sun.xml.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeaderElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HeaderElementImpl
/*     */   extends ElementImpl
/*     */   implements SOAPHeaderElement
/*     */ {
/*  53 */   protected static Name RELAY_ATTRIBUTE_LOCAL_NAME = NameImpl.createFromTagName("relay");
/*     */   
/*  55 */   protected static Name MUST_UNDERSTAND_ATTRIBUTE_LOCAL_NAME = NameImpl.createFromTagName("mustUnderstand");
/*     */   Name actorAttNameWithoutNS;
/*     */   Name roleAttNameWithoutNS;
/*     */   
/*  59 */   public HeaderElementImpl(SOAPDocumentImpl ownerDoc, Name qname) { super(ownerDoc, qname);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     this.actorAttNameWithoutNS = NameImpl.createFromTagName("actor");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     this.roleAttNameWithoutNS = NameImpl.createFromTagName("role"); } public HeaderElementImpl(SOAPDocumentImpl ownerDoc, QName qname) { super(ownerDoc, qname); this.actorAttNameWithoutNS = NameImpl.createFromTagName("actor"); this.roleAttNameWithoutNS = NameImpl.createFromTagName("role"); }
/*     */   
/*     */   protected abstract NameImpl getActorAttributeName(); protected abstract NameImpl getRoleAttributeName(); protected abstract NameImpl getMustunderstandAttributeName(); protected abstract boolean getMustunderstandAttributeValue(String paramString); protected abstract String getMustunderstandLiteralValue(boolean paramBoolean);
/*     */   protected abstract NameImpl getRelayAttributeName();
/* 112 */   public String getRole() { String role = getAttributeValue((Name)getRoleAttributeName());
/* 113 */     return role; }
/*     */   protected abstract boolean getRelayAttributeValue(String paramString);
/*     */   protected abstract String getRelayLiteralValue(boolean paramBoolean);
/*     */   protected abstract String getActorOrRole();
/*     */   public void setParentElement(SOAPElement element) throws SOAPException { if (!(element instanceof javax.xml.soap.SOAPHeader)) { log.severe("SAAJ0130.impl.header.elem.parent.mustbe.header"); throw new SOAPException("Parent of a SOAPHeaderElement has to be a SOAPHeader"); }  super.setParentElement(element); } public void setActor(String actorUri) { try { removeAttribute((Name)getActorAttributeName()); addAttribute((Name)getActorAttributeName(), actorUri); }
/* 118 */     catch (SOAPException ex) {} } public void setRole(String roleUri) throws SOAPException { removeAttribute((Name)getRoleAttributeName()); addAttribute((Name)getRoleAttributeName(), roleUri); } public String getActor() { String actor = getAttributeValue((Name)getActorAttributeName()); return actor; } public void setMustUnderstand(boolean mustUnderstand) { try { removeAttribute((Name)getMustunderstandAttributeName());
/* 119 */       addAttribute((Name)getMustunderstandAttributeName(), getMustunderstandLiteralValue(mustUnderstand));
/*     */        }
/*     */     
/* 122 */     catch (SOAPException ex) {} }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMustUnderstand() {
/* 127 */     String mu = getAttributeValue((Name)getMustunderstandAttributeName());
/*     */     
/* 129 */     if (mu != null) {
/* 130 */       return getMustunderstandAttributeValue(mu);
/*     */     }
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRelay(boolean relay) throws SOAPException {
/* 137 */     removeAttribute((Name)getRelayAttributeName());
/* 138 */     addAttribute((Name)getRelayAttributeName(), getRelayLiteralValue(relay));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getRelay() {
/* 144 */     String mu = getAttributeValue((Name)getRelayAttributeName());
/* 145 */     if (mu != null) {
/* 146 */       return getRelayAttributeValue(mu);
/*     */     }
/* 148 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\HeaderElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */