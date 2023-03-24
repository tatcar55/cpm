/*     */ package com.sun.xml.ws.security.secext10;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyAttribute;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "UsernameTokenType", propOrder = {"username", "password", "nonce", "created", "salt", "iteration", "any"})
/*     */ public class UsernameTokenType
/*     */ {
/*     */   @XmlElement(name = "Username", required = true)
/*     */   protected AttributedString username;
/*     */   @XmlElement(name = "Password", required = false)
/* 106 */   protected AttributedString password = null;
/*     */   
/*     */   @XmlElement(name = "Nonce", required = false)
/* 109 */   protected AttributedString nonce = null;
/*     */   
/*     */   @XmlElement(name = "Created", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", required = false)
/* 112 */   protected AttributedString created = null;
/*     */   
/*     */   @XmlElement(name = "Salt", namespace = "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", required = false)
/* 115 */   protected AttributedString salt = null;
/*     */   
/*     */   @XmlElement(name = "Iteration", namespace = "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", required = false)
/*     */   protected AttributedString iteration;
/*     */   
/*     */   @XmlAnyElement(lax = true)
/*     */   protected List<Object> any;
/*     */   @XmlAttribute(name = "Id", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   @XmlID
/*     */   protected String id;
/*     */   @XmlAnyAttribute
/* 127 */   private Map<QName, String> otherAttributes = new HashMap<QName, String>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributedString getUsername() {
/* 139 */     return this.username;
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
/*     */   public void setUsername(AttributedString value) {
/* 151 */     this.username = value;
/*     */   }
/*     */   
/*     */   public AttributedString getPassword() {
/* 155 */     return this.password;
/*     */   }
/*     */   
/*     */   public void setPassword(AttributedString value) {
/* 159 */     this.password = value;
/*     */   }
/*     */   
/*     */   public AttributedString getNonce() {
/* 163 */     return this.nonce;
/*     */   }
/*     */   
/*     */   public void setNonce(AttributedString value) {
/* 167 */     this.nonce = value;
/*     */   }
/*     */   
/*     */   public AttributedString getCreated() {
/* 171 */     return this.created;
/*     */   }
/*     */   
/*     */   public void setCreated(AttributedString value) {
/* 175 */     this.created = value;
/*     */   }
/*     */   public AttributedString getIteration() {
/* 178 */     return this.iteration;
/*     */   }
/*     */   public void setIteration(AttributedString value) {
/* 181 */     this.iteration = value;
/*     */   }
/*     */   public AttributedString getSalt() {
/* 184 */     return this.salt;
/*     */   }
/*     */   public void setSalt(AttributedString salt) {
/* 187 */     this.salt = salt;
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
/*     */   
/*     */   public List<Object> getAny() {
/* 213 */     if (this.any == null) {
/* 214 */       this.any = new ArrayList();
/*     */     }
/* 216 */     return this.any;
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
/*     */   public String getId() {
/* 228 */     return this.id;
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
/*     */   public void setId(String value) {
/* 240 */     this.id = value;
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
/*     */   public Map<QName, String> getOtherAttributes() {
/* 258 */     return this.otherAttributes;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secext10\UsernameTokenType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */