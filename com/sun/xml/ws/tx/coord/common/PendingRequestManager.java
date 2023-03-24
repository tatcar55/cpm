/*     */ package com.sun.xml.ws.tx.coord.common;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PendingRequestManager
/*     */ {
/*  52 */   private static final Logger LOGGER = Logger.getLogger(PendingRequestManager.class);
/*  53 */   static ConcurrentHashMap<String, ResponseBox> pendingRequests = new ConcurrentHashMap<String, ResponseBox>();
/*     */   
/*     */   public static ResponseBox reqisterRequest(String msgId) {
/*  56 */     ResponseBox box = new ResponseBox();
/*  57 */     pendingRequests.put(msgId, box);
/*  58 */     return box;
/*     */   }
/*     */   
/*     */   public static void removeRequest(String msgId) {
/*  62 */     pendingRequests.remove(msgId);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registryReponse(String msgId, BaseRegisterResponseType repsonse) {
/*  67 */     if (LOGGER.isLoggable(Level.FINE)) {
/*  68 */       LOGGER.fine(LocalizationMessages.WSAT_4620_GET_RESPONSE_REQUEST("\t" + msgId));
/*     */     }
/*  70 */     ResponseBox box = pendingRequests.remove(msgId);
/*  71 */     if (box != null) {
/*  72 */       box.put(repsonse);
/*  73 */     } else if (LOGGER.isLoggable(Level.FINE)) {
/*  74 */       LOGGER.fine(LocalizationMessages.WSAT_4621_IGNORE_RESPONSE("\t" + msgId));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ResponseBox
/*     */   {
/*     */     private boolean isSet = false;
/*     */     
/*     */     private BaseRegisterResponseType type;
/*     */ 
/*     */     
/*     */     public synchronized void put(BaseRegisterResponseType type) {
/*  87 */       this.type = type;
/*  88 */       this.isSet = true;
/*  89 */       notify();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized BaseRegisterResponseType getResponse(long timeout) {
/* 101 */       long start = System.currentTimeMillis();
/* 102 */       while (!this.isSet) {
/*     */         try {
/* 104 */           wait(timeout);
/* 105 */           long end = System.currentTimeMillis();
/* 106 */           timeout -= end - start;
/* 107 */           if (timeout <= 0L)
/*     */             break; 
/* 109 */           start = end;
/* 110 */         } catch (InterruptedException e) {
/* 111 */           throw new WebServiceException(e);
/*     */         } 
/*     */       } 
/*     */       
/* 115 */       return this.type;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\PendingRequestManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */