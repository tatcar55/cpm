/*    */ package com.sun.xml.ws.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*    */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*    */ import com.sun.xml.ws.api.server.SDDocumentSource;
/*    */ import com.sun.xml.ws.api.wsdl.parser.XMLEntityResolver;
/*    */ import com.sun.xml.ws.util.JAXWSUtils;
/*    */ import com.sun.xml.ws.util.xml.XmlUtil;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.transform.Result;
/*    */ import javax.xml.transform.Source;
/*    */ import javax.xml.transform.Transformer;
/*    */ import javax.xml.transform.TransformerException;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public final class MexEntityResolver
/*    */   implements XMLEntityResolver
/*    */ {
/* 66 */   private final Map<String, SDDocumentSource> wsdls = new HashMap<String, SDDocumentSource>();
/*    */   
/*    */   public MexEntityResolver(List<? extends Source> wsdls) throws IOException {
/* 69 */     Transformer transformer = XmlUtil.newTransformer();
/* 70 */     for (Source source : wsdls) {
/* 71 */       XMLStreamBufferResult xsbr = new XMLStreamBufferResult();
/*    */       try {
/* 73 */         transformer.transform(source, (Result)xsbr);
/* 74 */       } catch (TransformerException e) {
/* 75 */         throw new WebServiceException(e);
/*    */       } 
/* 77 */       String systemId = source.getSystemId();
/*    */ 
/*    */       
/* 80 */       if (systemId != null) {
/* 81 */         SDDocumentSource doc = SDDocumentSource.create(JAXWSUtils.getFileOrURL(systemId), (XMLStreamBuffer)xsbr.getXMLStreamBuffer());
/* 82 */         this.wsdls.put(systemId, doc);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public XMLEntityResolver.Parser resolveEntity(String publicId, String systemId) throws SAXException, IOException, XMLStreamException {
/* 88 */     if (systemId != null) {
/* 89 */       SDDocumentSource src = this.wsdls.get(systemId);
/* 90 */       if (src != null)
/* 91 */         return new XMLEntityResolver.Parser(src); 
/*    */     } 
/* 93 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\MexEntityResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */