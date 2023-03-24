/*     */ package com.sun.xml.security.core.ai;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlElementRefs;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlType(name = "IdentityType", propOrder = {"dnsOrSpnOrUpn"})
/*     */ public class IdentityType
/*     */ {
/*     */   @XmlElementRefs({@XmlElementRef(name = "Spn", namespace = "http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", type = JAXBElement.class), @XmlElementRef(name = "Dns", namespace = "http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", type = JAXBElement.class), @XmlElementRef(name = "KeyInfo", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class), @XmlElementRef(name = "Upn", namespace = "http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", type = JAXBElement.class), @XmlElementRef(name = "BinarySecurityToken", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", type = JAXBElement.class), @XmlElementRef(name = "SecurityTokenReference", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", type = JAXBElement.class)})
/*     */   protected List<JAXBElement<?>> dnsOrSpnOrUpn;
/*     */   
/*     */   public List<JAXBElement<?>> getDnsOrSpnOrUpn() {
/* 134 */     if (this.dnsOrSpnOrUpn == null) {
/* 135 */       this.dnsOrSpnOrUpn = new ArrayList<JAXBElement<?>>();
/*     */     }
/* 137 */     return this.dnsOrSpnOrUpn;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\security\core\ai\IdentityType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */