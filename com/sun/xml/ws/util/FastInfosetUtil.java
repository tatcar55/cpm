/*    */ package com.sun.xml.ws.util;
/*    */ 
/*    */ import com.sun.xml.ws.streaming.XMLReaderException;
/*    */ import com.sun.xml.ws.streaming.XMLStreamReaderException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FastInfosetUtil
/*    */ {
/*    */   public static XMLStreamReader createFIStreamReader(InputStream in) {
/* 56 */     if (FastInfosetReflection.fiStAXDocumentParser_new == null) {
/* 57 */       throw new XMLReaderException("fastinfoset.noImplementation", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 62 */       Object sdp = FastInfosetReflection.fiStAXDocumentParser_new.newInstance(new Object[0]);
/* 63 */       FastInfosetReflection.fiStAXDocumentParser_setStringInterning.invoke(sdp, new Object[] { Boolean.TRUE });
/* 64 */       FastInfosetReflection.fiStAXDocumentParser_setInputStream.invoke(sdp, new Object[] { in });
/* 65 */       return (XMLStreamReader)sdp;
/* 66 */     } catch (Exception e) {
/* 67 */       throw new XMLStreamReaderException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\FastInfosetUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */