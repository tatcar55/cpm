/*    */ package com.sun.xml.ws.encoding.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
/*    */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*    */ import java.io.InputStream;
/*    */ import java.io.Reader;
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
/*    */ 
/*    */ 
/*    */ public final class FastInfosetStreamReaderFactory
/*    */   extends XMLStreamReaderFactory
/*    */ {
/* 53 */   private static final FastInfosetStreamReaderFactory factory = new FastInfosetStreamReaderFactory();
/*    */   
/* 55 */   private ThreadLocal<StAXDocumentParser> pool = new ThreadLocal<StAXDocumentParser>();
/*    */   
/*    */   public static FastInfosetStreamReaderFactory getInstance() {
/* 58 */     return factory;
/*    */   }
/*    */   
/*    */   public XMLStreamReader doCreate(String systemId, InputStream in, boolean rejectDTDs) {
/* 62 */     StAXDocumentParser parser = fetch();
/* 63 */     if (parser == null) {
/* 64 */       return (XMLStreamReader)FastInfosetCodec.createNewStreamReaderRecyclable(in, false);
/*    */     }
/*    */     
/* 67 */     parser.setInputStream(in);
/* 68 */     return (XMLStreamReader)parser;
/*    */   }
/*    */   
/*    */   public XMLStreamReader doCreate(String systemId, Reader reader, boolean rejectDTDs) {
/* 72 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   private StAXDocumentParser fetch() {
/* 76 */     StAXDocumentParser parser = this.pool.get();
/* 77 */     this.pool.set(null);
/* 78 */     return parser;
/*    */   }
/*    */   
/*    */   public void doRecycle(XMLStreamReader r) {
/* 82 */     if (r instanceof StAXDocumentParser)
/* 83 */       this.pool.set((StAXDocumentParser)r); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\fastinfoset\FastInfosetStreamReaderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */