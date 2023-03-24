/*    */ package com.sun.xml.ws.transport.tcp.encoding;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
/*    */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*    */ import java.io.InputStream;
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
/*    */ public class WSTCPFastInfosetStreamReaderRecyclable
/*    */   extends StAXDocumentParser
/*    */   implements XMLStreamReaderFactory.RecycleAware
/*    */ {
/*    */   private RecycleAwareListener listener;
/*    */   
/*    */   public WSTCPFastInfosetStreamReaderRecyclable() {}
/*    */   
/*    */   public WSTCPFastInfosetStreamReaderRecyclable(InputStream in, RecycleAwareListener listener) {
/* 57 */     super(in);
/* 58 */     this.listener = listener;
/*    */   }
/*    */   
/*    */   public void onRecycled() {
/* 62 */     this.listener.onRecycled();
/*    */   }
/*    */   
/*    */   public RecycleAwareListener getListener() {
/* 66 */     return this.listener;
/*    */   }
/*    */   
/*    */   public void setListener(RecycleAwareListener listener) {
/* 70 */     this.listener = listener;
/*    */   }
/*    */   
/*    */   public static interface RecycleAwareListener {
/*    */     void onRecycled();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\encoding\WSTCPFastInfosetStreamReaderRecyclable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */