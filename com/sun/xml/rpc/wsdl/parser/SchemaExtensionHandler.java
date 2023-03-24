/*    */ package com.sun.xml.rpc.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*    */ import com.sun.xml.rpc.wsdl.document.schema.Schema;
/*    */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*    */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*    */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*    */ import com.sun.xml.rpc.wsdl.framework.ParserContext;
/*    */ import com.sun.xml.rpc.wsdl.framework.WriterContext;
/*    */ import java.io.IOException;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchemaExtensionHandler
/*    */   extends ExtensionHandler
/*    */ {
/*    */   public String getNamespaceURI() {
/* 52 */     return "http://www.w3.org/2001/XMLSchema";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean doHandleExtension(ParserContext context, Extensible parent, Element e) {
/* 59 */     if (XmlUtil.matchesTagNS(e, SchemaConstants.QNAME_SCHEMA)) {
/* 60 */       SchemaParser parser = new SchemaParser();
/* 61 */       parent.addExtension((Extension)parser.parseSchema(context, e, (String)null));
/* 62 */       return true;
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void doHandleExtension(WriterContext context, Extension extension) throws IOException {
/* 70 */     if (extension instanceof Schema) {
/* 71 */       SchemaWriter writer = new SchemaWriter();
/* 72 */       writer.writeSchema(context, (Schema)extension);
/*    */     } else {
/*    */       
/* 75 */       throw new IllegalArgumentException();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\SchemaExtensionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */