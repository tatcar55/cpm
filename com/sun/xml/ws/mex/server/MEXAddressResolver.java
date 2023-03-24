/*     */ package com.sun.xml.ws.mex.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.ws.mex.MessagesMessages;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.logging.Logger;
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
/*     */ class MEXAddressResolver
/*     */   extends PortAddressResolver
/*     */ {
/*  61 */   private static final Logger LOGGER = Logger.getLogger(MEXAddressResolver.class.getName());
/*     */ 
/*     */   
/*     */   private final QName service;
/*     */ 
/*     */   
/*     */   private final String port;
/*     */ 
/*     */   
/*     */   private final String address;
/*     */ 
/*     */ 
/*     */   
/*     */   MEXAddressResolver(@NotNull QName serviceName, @NotNull QName portName, @NotNull String address) {
/*  75 */     this.service = serviceName;
/*  76 */     this.port = portName.getLocalPart();
/*  77 */     this.address = address;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAddressFor(@NotNull QName serviceName, @NotNull String portName) {
/*  82 */     return this.address;
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
/*     */   public String getAddressFor(@NotNull QName serviceName, @NotNull String portName, String currentAddress) {
/* 105 */     LOGGER.entering(MEXAddressResolver.class.getName(), "getAddressFor", new Object[] { serviceName, portName, currentAddress });
/*     */     
/* 107 */     String result = null;
/* 108 */     if (this.service.equals(serviceName) && this.port.equals(portName)) {
/* 109 */       result = getAddressFor(serviceName, portName);
/* 110 */       if (currentAddress != null) {
/*     */         try {
/* 112 */           URL addressUrl = new URL(this.address);
/*     */           try {
/* 114 */             URL currentAddressUrl = new URL(currentAddress);
/* 115 */             if (currentAddressUrl.getProtocol().toLowerCase().equals("http") && addressUrl.getProtocol().toLowerCase().equals("https")) {
/*     */               
/* 117 */               result = currentAddress;
/* 118 */               LOGGER.fine(MessagesMessages.MEX_0019_LEAVE_ADDRESS(currentAddress, portName));
/*     */             } else {
/*     */               
/* 121 */               LOGGER.fine(MessagesMessages.MEX_0018_REPLACE_ADDRESS(currentAddress, portName, result));
/*     */             } 
/* 123 */           } catch (MalformedURLException ex) {
/* 124 */             LOGGER.fine(MessagesMessages.MEX_0020_CURRENT_ADDRESS_NO_URL(currentAddress, portName));
/*     */           } 
/* 126 */         } catch (MalformedURLException ex) {
/* 127 */           LOGGER.fine(MessagesMessages.MEX_0021_NEW_ADDRESS_NO_URL(this.address, portName));
/*     */         } 
/*     */       }
/*     */     } 
/* 131 */     LOGGER.exiting(MEXAddressResolver.class.getName(), "getAddressFor", result);
/* 132 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\mex\server\MEXAddressResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */