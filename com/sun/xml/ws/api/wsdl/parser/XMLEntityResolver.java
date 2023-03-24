/*    */ package com.sun.xml.ws.api.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.ws.api.server.SDDocumentSource;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import org.xml.sax.SAXException;
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
/*    */ public interface XMLEntityResolver
/*    */ {
/*    */   Parser resolveEntity(String paramString1, String paramString2) throws SAXException, IOException, XMLStreamException;
/*    */   
/*    */   public static final class Parser
/*    */   {
/*    */     public final URL systemId;
/*    */     public final XMLStreamReader parser;
/*    */     
/*    */     public Parser(URL systemId, XMLStreamReader parser) {
/* 78 */       assert parser != null;
/* 79 */       this.systemId = systemId;
/* 80 */       this.parser = parser;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Parser(SDDocumentSource doc) throws IOException, XMLStreamException {
/* 87 */       this.systemId = doc.getSystemId();
/* 88 */       this.parser = doc.read();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\wsdl\parser\XMLEntityResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */