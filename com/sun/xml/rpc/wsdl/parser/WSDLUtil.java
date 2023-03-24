/*     */ package com.sun.xml.rpc.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.spi.tools.WSDLUtil;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.Definitions;
/*     */ import com.sun.xml.rpc.wsdl.document.Import;
/*     */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.Schema;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.ParserContext;
/*     */ import com.sun.xml.rpc.wsdl.framework.WriterContext;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import org.w3c.dom.Element;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSDLUtil
/*     */   implements WSDLUtil
/*     */ {
/*     */   public void getRelativeImports(URL wsdlURL, Collection<Import> wsdlRelativeImports, Collection<Import> schemaRelativeImports) throws IOException {
/*  72 */     InputStream wsdlInputStream = new BufferedInputStream(wsdlURL.openStream());
/*     */     
/*  74 */     InputSource wsdlDocumentSource = new InputSource(wsdlInputStream);
/*  75 */     WSDLParserOverride wsdlParser = new WSDLParserOverride();
/*     */ 
/*     */     
/*  78 */     wsdlParser.setFollowImports(false);
/*  79 */     WSDLDocument wsdlDoc = wsdlParser.parse(wsdlDocumentSource);
/*     */     
/*  81 */     Iterator<Import> iter = wsdlDoc.getDefinitions().imports();
/*  82 */     while (iter.hasNext()) {
/*     */       
/*  84 */       Import next = iter.next();
/*  85 */       String location = next.getLocation();
/*     */       
/*  87 */       if (location.indexOf(":") == -1) {
/*  88 */         wsdlRelativeImports.add(next);
/*     */       }
/*     */     } 
/*     */     
/*  92 */     Collection schemaImports = wsdlParser.getSchemaImports();
/*  93 */     for (Iterator<Import> iterator1 = schemaImports.iterator(); iterator1.hasNext(); ) {
/*  94 */       Import next = iterator1.next();
/*  95 */       String location = next.getLocation();
/*     */       
/*  97 */       if (location.indexOf(":") == -1) {
/*  98 */         schemaRelativeImports.add(next);
/*     */       }
/*     */     } 
/*     */     
/* 102 */     wsdlInputStream.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WSDLParserOverride
/*     */     extends WSDLParser
/*     */   {
/*     */     private WSDLUtil.SchemaExtensionHandlerOverride schemaHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WSDLParserOverride() {
/* 117 */       this.schemaHandler = new WSDLUtil.SchemaExtensionHandlerOverride();
/*     */       
/* 119 */       register(this.schemaHandler);
/*     */     }
/*     */     
/*     */     public Collection getSchemaImports() {
/* 123 */       return this.schemaHandler.getImports();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Definitions parseDefinitions(ParserContext context, InputSource source, String expectedTargetNamespaceURI) {
/* 130 */       Definitions definitions = parseDefinitionsNoImport(context, source, expectedTargetNamespaceURI);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 135 */       return definitions;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SchemaExtensionHandlerOverride
/*     */     extends ExtensionHandler
/*     */   {
/* 145 */     private WSDLUtil.SchemaParserOverride parser = new WSDLUtil.SchemaParserOverride();
/*     */ 
/*     */     
/*     */     public Collection getImports() {
/* 149 */       return this.parser.getImports();
/*     */     }
/*     */     
/*     */     public String getNamespaceURI() {
/* 153 */       return "http://www.w3.org/2001/XMLSchema";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean doHandleExtension(ParserContext context, Extensible parent, Element e) {
/* 160 */       if (XmlUtil.matchesTagNS(e, SchemaConstants.QNAME_SCHEMA)) {
/* 161 */         parent.addExtension((Extension)this.parser.parseSchema(context, e, (String)null));
/* 162 */         return true;
/*     */       } 
/* 164 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void doHandleExtension(WriterContext context, Extension extension) throws IOException {
/* 172 */       throw new IllegalArgumentException("unsupported operation");
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SchemaParserOverride extends SchemaParser {
/* 177 */     private Collection imports = new HashSet();
/*     */     
/*     */     public Collection getImports() {
/* 180 */       return this.imports;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void processImports(ParserContext context, InputSource src, Schema schema) {
/* 187 */       Iterator<SchemaElement> iter = schema.getContent().children();
/* 188 */       while (iter.hasNext()) {
/*     */         
/* 190 */         SchemaElement child = iter.next();
/* 191 */         if (child.getQName().equals(SchemaConstants.QNAME_IMPORT)) {
/* 192 */           String location = child.getValueOfAttributeOrNull("schemaLocation");
/*     */ 
/*     */           
/* 195 */           String namespace = child.getValueOfAttributeOrNull("namespace");
/*     */ 
/*     */           
/* 198 */           if (location != null && namespace != null) {
/* 199 */             Import schemaImport = new Import();
/* 200 */             schemaImport.setLocation(location);
/* 201 */             schemaImport.setNamespace(namespace);
/* 202 */             this.imports.add(schemaImport);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private SchemaParserOverride() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\WSDLUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */