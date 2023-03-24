/*     */ package com.sun.xml.ws.transport.tcp.servicechannel;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.ws.WebFault;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @WebFault(name = "ServiceChannelException", targetNamespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", faultBean = "com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelException$ServiceChannelExceptionBean")
/*     */ public class ServiceChannelException
/*     */   extends Exception
/*     */ {
/*     */   private ServiceChannelExceptionBean faultInfo;
/*     */   
/*     */   public ServiceChannelException() {
/*  58 */     this.faultInfo = new ServiceChannelExceptionBean();
/*     */   }
/*     */   
/*     */   public ServiceChannelException(ServiceChannelErrorCode errorCode, @Nullable String message) {
/*  62 */     super(message);
/*  63 */     this.faultInfo = new ServiceChannelExceptionBean(errorCode, message);
/*     */   }
/*     */   
/*     */   public ServiceChannelException(String message, ServiceChannelExceptionBean faultInfo) {
/*  67 */     super(message);
/*  68 */     this.faultInfo = faultInfo;
/*     */   }
/*     */   
/*     */   public ServiceChannelException(String message, ServiceChannelExceptionBean faultInfo, Throwable cause) {
/*  72 */     super(message, cause);
/*  73 */     this.faultInfo = faultInfo;
/*     */   }
/*     */   
/*     */   public ServiceChannelExceptionBean getFaultInfo() {
/*  77 */     return this.faultInfo;
/*     */   }
/*     */   
/*     */   public void setFaultInfo(ServiceChannelExceptionBean faultInfo) {
/*  81 */     this.faultInfo = faultInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   @XmlAccessorType(XmlAccessType.FIELD)
/*     */   @XmlType(name = "serviceChannelExceptionBean", propOrder = {"errorCode", "message"})
/*     */   public static class ServiceChannelExceptionBean
/*     */   {
/*     */     @XmlElement(required = true)
/*     */     private ServiceChannelErrorCode errorCode;
/*     */     
/*     */     private String message;
/*     */ 
/*     */     
/*     */     public ServiceChannelExceptionBean() {}
/*     */ 
/*     */     
/*     */     public ServiceChannelExceptionBean(ServiceChannelErrorCode errorCode, String message) {
/*  99 */       this.errorCode = errorCode;
/* 100 */       this.message = message;
/*     */     }
/*     */     
/*     */     public ServiceChannelErrorCode getErrorCode() {
/* 104 */       return this.errorCode;
/*     */     }
/*     */     
/*     */     public void setErrorCode(ServiceChannelErrorCode errorCode) {
/* 108 */       this.errorCode = errorCode;
/*     */     }
/*     */     
/*     */     public String getMessage() {
/* 112 */       return this.message;
/*     */     }
/*     */     
/*     */     public void setMessage(String message) {
/* 116 */       this.message = message;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\servicechannel\ServiceChannelException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */