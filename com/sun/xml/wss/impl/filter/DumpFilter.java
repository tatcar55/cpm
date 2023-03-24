/*     */ package com.sun.xml.wss.impl.filter;
/*     */ 
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.logging.impl.filter.LogStringsMessages;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DumpFilter
/*     */ {
/*  63 */   private static Level DEFAULT_LOG_LEVEL = Level.INFO;
/*     */   
/*  65 */   private static final String lineSeparator = System.getProperty("line.separator");
/*     */   
/*  67 */   private static Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.filter", "com.sun.xml.wss.logging.impl.filter.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void process(ProcessingContext context) throws XWSSecurityException {
/*  78 */     ByteArrayOutputStream baos = null;
/*     */     
/*  80 */     baos = new ByteArrayOutputStream();
/*  81 */     OutputStream dest = baos;
/*     */     
/*  83 */     String label = "Sending Message";
/*     */     
/*  85 */     if (context.isInboundMessage()) {
/*  86 */       label = "Received Message";
/*     */     }
/*     */     
/*  89 */     String msg1 = "==== " + label + " Start ====" + lineSeparator;
/*     */ 
/*     */     
/*     */     try {
/*  93 */       TeeFilter teeFilter = new TeeFilter(dest, true);
/*  94 */       teeFilter.process(context.getSOAPMessage());
/*  95 */     } catch (Exception e) {
/*  96 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1411_UNABLETO_DUMP_SOAPMESSAGE(new Object[] { e.getMessage() }));
/*     */ 
/*     */       
/*  99 */       throw new XWSSecurityException("Unable to dump SOAPMessage");
/*     */     } 
/*     */     
/* 102 */     String msg2 = "==== " + label + " End  ====" + lineSeparator;
/*     */     
/* 104 */     byte[] bytes = baos.toByteArray();
/* 105 */     String logMsg = msg1 + new String(bytes) + msg2;
/* 106 */     log.log(DEFAULT_LOG_LEVEL, logMsg);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\filter\DumpFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */