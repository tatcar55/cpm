/*    */ package com.sun.xml.ws.streaming;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.istack.Nullable;
/*    */ import com.sun.xml.ws.util.xml.XMLStreamReaderFilter;
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public class TidyXMLStreamReader
/*    */   extends XMLStreamReaderFilter
/*    */ {
/*    */   private final Closeable closeableSource;
/*    */   
/*    */   public TidyXMLStreamReader(@NotNull XMLStreamReader reader, @Nullable Closeable closeableSource) {
/* 63 */     super(reader);
/* 64 */     this.closeableSource = closeableSource;
/*    */   }
/*    */   
/*    */   public void close() throws XMLStreamException {
/* 68 */     super.close();
/*    */     try {
/* 70 */       if (this.closeableSource != null)
/* 71 */         this.closeableSource.close(); 
/* 72 */     } catch (IOException e) {
/* 73 */       throw new WebServiceException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\streaming\TidyXMLStreamReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */