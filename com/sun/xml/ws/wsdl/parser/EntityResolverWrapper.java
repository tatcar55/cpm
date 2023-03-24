/*    */ package com.sun.xml.ws.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*    */ import com.sun.xml.ws.api.wsdl.parser.XMLEntityResolver;
/*    */ import com.sun.xml.ws.streaming.TidyXMLStreamReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import org.xml.sax.EntityResolver;
/*    */ import org.xml.sax.InputSource;
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
/*    */ final class EntityResolverWrapper
/*    */   implements XMLEntityResolver
/*    */ {
/*    */   private final EntityResolver core;
/*    */   private boolean useStreamFromEntityResolver = false;
/*    */   
/*    */   public EntityResolverWrapper(EntityResolver core) {
/* 64 */     this.core = core;
/*    */   }
/*    */   
/*    */   public EntityResolverWrapper(EntityResolver core, boolean useStreamFromEntityResolver) {
/* 68 */     this.core = core;
/* 69 */     this.useStreamFromEntityResolver = useStreamFromEntityResolver;
/*    */   }
/*    */   public XMLEntityResolver.Parser resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/*    */     InputStream stream;
/* 73 */     InputSource source = this.core.resolveEntity(publicId, systemId);
/* 74 */     if (source == null) {
/* 75 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 80 */     if (source.getSystemId() != null) {
/* 81 */       systemId = source.getSystemId();
/*    */     }
/* 83 */     URL url = new URL(systemId);
/*    */     
/* 85 */     if (this.useStreamFromEntityResolver) {
/* 86 */       stream = source.getByteStream();
/*    */     } else {
/* 88 */       stream = url.openStream();
/*    */     } 
/* 90 */     return new XMLEntityResolver.Parser(url, (XMLStreamReader)new TidyXMLStreamReader(XMLStreamReaderFactory.create(url.toExternalForm(), stream, true), stream));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\EntityResolverWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */