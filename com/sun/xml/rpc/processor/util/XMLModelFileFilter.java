/*    */ package com.sun.xml.rpc.processor.util;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.exporter.Constants;
/*    */ import com.sun.xml.rpc.spi.tools.XMLModelFileFilter;
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
/*    */ import com.sun.xml.rpc.streaming.XMLReaderException;
/*    */ import com.sun.xml.rpc.streaming.XMLReaderFactory;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import java.util.zip.GZIPInputStream;
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
/*    */ public class XMLModelFileFilter
/*    */   implements XMLModelFileFilter
/*    */ {
/* 49 */   private XMLReaderFactory factory = XMLReaderFactory.newInstance();
/*    */ 
/*    */   
/*    */   public boolean isModelFile(File f) {
/* 53 */     if (f == null || !f.isFile() || !f.exists()) {
/* 54 */       return false;
/*    */     }
/*    */     
/* 57 */     boolean result = false;
/*    */     
/*    */     try {
/* 60 */       InputStream is = new FileInputStream(f);
/* 61 */       return isModelFile(is);
/* 62 */     } catch (FileNotFoundException e) {
/*    */ 
/*    */       
/* 65 */       return result;
/*    */     } 
/*    */   }
/*    */   public boolean isModelFile(URL uRL) {
/* 69 */     boolean result = false;
/*    */     try {
/* 71 */       InputStream is = uRL.openStream();
/* 72 */       result = isModelFile(is);
/* 73 */     } catch (Exception e) {}
/*    */     
/* 75 */     return result;
/*    */   }
/*    */   
/*    */   public boolean isModelFile(InputStream inputStream) {
/* 79 */     boolean result = false;
/*    */     
/* 81 */     try { XMLReader reader = this.factory.createXMLReader(new GZIPInputStream(inputStream));
/*    */       
/* 83 */       reader.next();
/* 84 */       if (reader.getState() == 1 && reader.getName().equals(Constants.QNAME_MODEL))
/*    */       {
/*    */         
/* 87 */         result = true;
/*    */       }
/* 89 */       reader.close(); }
/* 90 */     catch (XMLReaderException e) {  }
/* 91 */     catch (FileNotFoundException e) {  }
/* 92 */     catch (IOException e) {}
/*    */     
/* 94 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\XMLModelFileFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */