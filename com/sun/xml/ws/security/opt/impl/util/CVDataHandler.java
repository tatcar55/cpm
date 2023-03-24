/*    */ package com.sun.xml.ws.security.opt.impl.util;
/*    */ 
/*    */ import com.sun.xml.ws.security.opt.impl.enc.CryptoProcessor;
/*    */ import java.awt.datatransfer.DataFlavor;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import javax.activation.DataContentHandler;
/*    */ import javax.activation.DataSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CVDataHandler
/*    */   implements DataContentHandler
/*    */ {
/*    */   public Object getContent(DataSource ds) {
/* 59 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public Object getTransferData(DataFlavor df, DataSource ds) {
/* 63 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public DataFlavor[] getTransferDataFlavors() {
/* 67 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
/* 71 */     if (obj instanceof CryptoProcessor) {
/* 72 */       CryptoProcessor cp = (CryptoProcessor)obj;
/* 73 */       cp.encrypt(os);
/*    */     } else {
/* 75 */       throw new UnsupportedOperationException();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\CVDataHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */