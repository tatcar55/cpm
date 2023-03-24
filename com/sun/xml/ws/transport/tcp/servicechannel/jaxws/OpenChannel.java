/*     */ package com.sun.xml.ws.transport.tcp.servicechannel.jaxws;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
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
/*     */ @XmlRootElement(name = "openChannel", namespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "openChannel", namespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", propOrder = {"targetWSURI", "negotiatedMimeTypes", "negotiatedParams"})
/*     */ public class OpenChannel
/*     */ {
/*     */   @XmlElement(name = "targetWSURI", namespace = "", required = true)
/*     */   private String targetWSURI;
/*     */   @XmlElement(name = "negotiatedMimeTypes", namespace = "", required = true)
/*     */   private List<String> negotiatedMimeTypes;
/*     */   @XmlElement(name = "negotiatedParams", namespace = "")
/*     */   private List<String> negotiatedParams;
/*     */   
/*     */   public String getTargetWSURI() {
/*  72 */     return this.targetWSURI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetWSURI(String targetWSURI) {
/*  81 */     this.targetWSURI = targetWSURI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getNegotiatedMimeTypes() {
/*  90 */     return this.negotiatedMimeTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNegotiatedMimeTypes(List<String> negotiatedMimeTypes) {
/*  99 */     this.negotiatedMimeTypes = negotiatedMimeTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getNegotiatedParams() {
/* 108 */     return this.negotiatedParams;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNegotiatedParams(List<String> negotiatedParams) {
/* 117 */     this.negotiatedParams = negotiatedParams;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\servicechannel\jaxws\OpenChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */