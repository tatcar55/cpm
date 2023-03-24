/*    */ package com.sun.xml.ws.encoding.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
/*    */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*    */ import java.io.InputStream;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ public final class FastInfosetStreamReaderRecyclable
/*    */   extends StAXDocumentParser
/*    */   implements XMLStreamReaderFactory.RecycleAware
/*    */ {
/* 51 */   private static final FastInfosetStreamReaderFactory READER_FACTORY = FastInfosetStreamReaderFactory.getInstance();
/*    */ 
/*    */   
/*    */   public FastInfosetStreamReaderRecyclable() {}
/*    */ 
/*    */   
/*    */   public FastInfosetStreamReaderRecyclable(InputStream in) {
/* 58 */     super(in);
/*    */   }
/*    */   
/*    */   public void onRecycled() {
/* 62 */     READER_FACTORY.doRecycle((XMLStreamReader)this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\fastinfoset\FastInfosetStreamReaderRecyclable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */