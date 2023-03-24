/*     */ package com.sun.xml.ws.config.metro.parser.jsr109;
/*     */ 
/*     */ import com.sun.xml.bind.Locatable;
/*     */ import com.sun.xml.bind.annotation.XmlLocation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlList;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import org.xml.sax.Locator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlType(name = "handler-chainType", propOrder = {"serviceNamePattern", "portNamePattern", "protocolBindings", "handler"})
/*     */ public class HandlerChainType
/*     */   implements Locatable
/*     */ {
/*     */   @XmlElement(name = "service-name-pattern")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   protected java.lang.String serviceNamePattern;
/*     */   @XmlElement(name = "port-name-pattern")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   protected java.lang.String portNamePattern;
/*     */   @XmlList
/*     */   @XmlElement(name = "protocol-bindings")
/*     */   protected List<java.lang.String> protocolBindings;
/*     */   @XmlElement(required = true)
/*     */   protected List<HandlerType> handler;
/*     */   @XmlAttribute(name = "id")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   @XmlID
/*     */   @XmlSchemaType(name = "ID")
/*     */   protected java.lang.String id;
/*     */   @XmlLocation
/*     */   @XmlTransient
/*     */   protected Locator locator;
/*     */   
/*     */   public java.lang.String getServiceNamePattern() {
/* 149 */     return this.serviceNamePattern;
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
/*     */   public void setServiceNamePattern(java.lang.String value) {
/* 161 */     this.serviceNamePattern = value;
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
/*     */   public java.lang.String getPortNamePattern() {
/* 173 */     return this.portNamePattern;
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
/*     */   public void setPortNamePattern(java.lang.String value) {
/* 185 */     this.portNamePattern = value;
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
/*     */   public List<java.lang.String> getProtocolBindings() {
/* 211 */     if (this.protocolBindings == null) {
/* 212 */       this.protocolBindings = new ArrayList<java.lang.String>();
/*     */     }
/* 214 */     return this.protocolBindings;
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
/*     */   public List<HandlerType> getHandler() {
/* 240 */     if (this.handler == null) {
/* 241 */       this.handler = new ArrayList<HandlerType>();
/*     */     }
/* 243 */     return this.handler;
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
/*     */   public java.lang.String getId() {
/* 255 */     return this.id;
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
/*     */   public void setId(java.lang.String value) {
/* 267 */     this.id = value;
/*     */   }
/*     */   
/*     */   public Locator sourceLocation() {
/* 271 */     return this.locator;
/*     */   }
/*     */   
/*     */   public void setSourceLocation(Locator newLocator) {
/* 275 */     this.locator = newLocator;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\metro\parser\jsr109\HandlerChainType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */