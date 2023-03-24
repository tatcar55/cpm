/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.ProcessorAction;
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.generator.nodes.JavaWsdlMappingNode;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.Properties;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.Document;
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
/*     */ public class JaxRpcMappingGenerator
/*     */   implements ProcessorAction
/*     */ {
/*     */   private boolean debug = false;
/*     */   private File mappingFile;
/*     */   
/*     */   public JaxRpcMappingGenerator(File mappingFile) {
/*  57 */     this.mappingFile = mappingFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void perform(Model model, Configuration config, Properties options) {
/*  65 */     Document document = buildMapping(model, config);
/*  66 */     write(document, this.mappingFile);
/*     */   }
/*     */   
/*     */   private Document buildMapping(Model model, Configuration config) {
/*  70 */     debug("building mapping");
/*     */     
/*  72 */     Document root = newDocument();
/*     */     try {
/*  74 */       (new JavaWsdlMappingNode()).write(root, "java-wsdl-mapping", model, config);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  79 */     catch (Exception ex) {
/*     */       
/*  81 */       ex.printStackTrace();
/*  82 */       throw new RuntimeException(ex.toString());
/*     */     } 
/*  84 */     return root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Document newDocument() {
/*     */     try {
/*  95 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*     */ 
/*     */       
/*  98 */       DocumentBuilder builder = factory.newDocumentBuilder();
/*     */       
/* 100 */       DOMImplementation domImplementation = builder.getDOMImplementation();
/*     */ 
/*     */       
/* 103 */       Document document = builder.newDocument();
/* 104 */       return document;
/* 105 */     } catch (Exception e) {
/*     */       
/* 107 */       e.printStackTrace();
/*     */       
/* 109 */       return null;
/*     */     } 
/*     */   }
/*     */   private void write(Document document, File resultFile) {
/*     */     try {
/* 114 */       FileOutputStream out = new FileOutputStream(resultFile);
/* 115 */       Result output = new StreamResult(out);
/*     */       
/* 117 */       Source source = new DOMSource(document);
/* 118 */       TransformerFactory factory = TransformerFactory.newInstance();
/* 119 */       Transformer transformer = factory.newTransformer();
/*     */       
/* 121 */       transformer.setOutputProperty("method", "xml");
/* 122 */       transformer.setOutputProperty("indent", "yes");
/* 123 */       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
/*     */ 
/*     */       
/* 126 */       transformer.setOutputProperty("encoding", "UTF-8");
/* 127 */       transformer.transform(source, output);
/*     */       
/* 129 */       out.close();
/*     */     }
/* 131 */     catch (Exception e) {
/*     */       
/* 133 */       e.printStackTrace();
/* 134 */       throw new RuntimeException(e.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void debug(String msg) {
/* 139 */     if (this.debug)
/* 140 */       System.out.println("[JaxRpcMappingGenerator] --> " + msg); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\JaxRpcMappingGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */