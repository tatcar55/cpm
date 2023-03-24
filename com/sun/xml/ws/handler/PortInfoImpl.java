/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.handler.PortInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortInfoImpl
/*     */   implements PortInfo
/*     */ {
/*     */   private BindingID bindingId;
/*     */   private QName portName;
/*     */   private QName serviceName;
/*     */   
/*     */   public PortInfoImpl(BindingID bindingId, QName portName, QName serviceName) {
/*  82 */     if (bindingId == null) {
/*  83 */       throw new RuntimeException("bindingId cannot be null");
/*     */     }
/*  85 */     if (portName == null) {
/*  86 */       throw new RuntimeException("portName cannot be null");
/*     */     }
/*  88 */     if (serviceName == null) {
/*  89 */       throw new RuntimeException("serviceName cannot be null");
/*     */     }
/*  91 */     this.bindingId = bindingId;
/*  92 */     this.portName = portName;
/*  93 */     this.serviceName = serviceName;
/*     */   }
/*     */   
/*     */   public String getBindingID() {
/*  97 */     return this.bindingId.toString();
/*     */   }
/*     */   
/*     */   public QName getPortName() {
/* 101 */     return this.portName;
/*     */   }
/*     */   
/*     */   public QName getServiceName() {
/* 105 */     return this.serviceName;
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
/*     */   public boolean equals(Object obj) {
/* 119 */     if (obj instanceof PortInfo) {
/* 120 */       PortInfo info = (PortInfo)obj;
/* 121 */       if (this.bindingId.toString().equals(info.getBindingID()) && this.portName.equals(info.getPortName()) && this.serviceName.equals(info.getServiceName()))
/*     */       {
/*     */         
/* 124 */         return true;
/*     */       }
/*     */     } 
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 135 */     return this.bindingId.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\PortInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */